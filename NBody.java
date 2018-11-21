/**File Name: NBody.java
  * Created by: Johnny Luong, cs8afqr, and Judy Chun, cs8afug
  * Date: 12/3/2017
  */

/**
 * *************PROGRAM DESCRIPTION**************
 * Nbody.java program allows you to play background music to make your motion picture
 * cool, and the music continues to play as your universe is being updated with each image
 * being repainted. The program allows us to continuously update and redraw the Bodies in 
 * our system in order to show the audience. The method called pause is used to ensure
 * that your animation will play smoothly. 
 * 
 * Body.java program creates a class for planet body objects that have the properties
 * of position, velocity, and acceleration. There are methods to break down velocity and 
 * acceleration into their x and y components. The user may also convert the position of
 * a planet body to fit onto a canvas.
 */ 

import java.awt.*;

/**
 * This is the primary class that will drive the universe simulation.
 */
public class NBody {
  
  public static final int CANVAS_WIDTH = 512; //final value of canvas width and height
  public static final int CANVAS_HEIGHT = 512;
  
  
  // This can be changed to modify the amount of delay between frames.
  private static final int SLEEP_TIME = 10;
  
  /**
   * Main Method 
   * @param args Arguments passed in from the command line.
   */
  public static void main(String[] args) {
    //load the background Picture and create a universe Picture
    Picture universe = new Picture(CANVAS_WIDTH, CANVAS_HEIGHT); 
    Picture background = new Picture("images/starfield.jpg");
    
    //Make user pick a simulation with FileChooser 
    String file = FileChooser.pickAFile();
    
    //Create a PlanetReader object using the returned filename to load 
    PlanetReader planetReader = new PlanetReader(file);
    double universeSize = planetReader.getUniverseSize(); //get the universe's size 
    double timeStep = planetReader.getTimestep(); //get the universe's timestep 
    Body.UNIVERSE_SIZE = universeSize;  //accessing static variable from class
    Body.TIME_STEP = timeStep;
    
    //Get array of Bodies with getPlanets()
    Body [] bodyArray = planetReader.getPlanets(); 
    //Get the total time to play the simulation with getTotalTime()
    double totalTime = planetReader.getTotalTime();
    
    Sound theme = new Sound("/home/linux/ieng6/cs8af/cs8afqr/psa8/2001.wav");
    theme.blockingPlay(); //load and play the 2001 theme 
    
    //draw the current state of the universe with drawBodies()
    drawBodies(bodyArray, universe, background); 
    universe.show(); //show universe 
    
    //looping from the current time (in steps of timestep) until the current time reaches total time 
    for(double currentTime = 0; currentTime < planetReader.getTotalTime(); currentTime+=timeStep){
      pause(); 
      moveBodies(bodyArray); //update velocity of every Body by using moveBodies() 
      drawBodies(bodyArray, universe, background); //clear and reset the background 
    }    
    
  }
  
  
  /**
   * Method:Helper method that will update all of the bodies of the universe
   * after one TIME_STEP and helps to update the velocities of all of the bodies
   * for current frame and then update their positions. 
   * Input: Body array of planets
   * Return: nothing
   * 
   * @param planets Array of all bodies in the system.
   */
  public static void moveBodies(Body[] planets) {
    
    //loop through the Body array of planets 
    for(int planet1 = 0; planet1 < planets.length; planet1++){
     for(int planet2 = 0; planet2 < planets.length; planet2++){
      if(planet1 != planet2){
      planets[planet1].updateVelocity(planets[planet2]);
      //update velocity of all the bodies for current frame 
      }
    }
    }
    
    //another loop through the Bodies and update their positions with move() 
    for(int i = 0; i < planets.length; i++){
      planets[i].move();
    }
    
  }
  
  /**
   * Method: Helper method that modifies the universe Picture to show the current state
   * of the universe and helps to reset the background picture onto the universe picture
   * by copying each pixel's color from the background picture to the universe picture, 
   * and draws every Body by using the draw method.
   * Input: Body array of bodies, Picture object called universe, and Picture object 
   * called background 
   * Return: nothing 
   * @param bodies Array of all bodies in the system.
   * @param universe A picture of the universe that wil be shown to the user.
   * @param background The background of the universe.
   */
  public static void drawBodies(Body[] bodies, Picture universe, Picture background) {
    //universe.getGraphics().clearRect(0, 0, CANVAS_WIDTH, CANVAS_HEIGHT);
    //loop through background picture 
    for(int x = 0; x < background.getWidth(); x++){
      for(int y = 0; y < background.getHeight(); y++){
        Pixel universePix = universe.getPixel(x,y); //create Pixel objects
        Pixel backgroundPix = background.getPixel(x,y);
        universePix.setColor(backgroundPix.getColor()); 
        //copy each pixel's color from the background picture to the universe picture 
      }
    }
    
    //loop through the Body array of bodies 
    for(int index = 0; index < bodies.length; index++){
      bodies[index].draw(universe); //draw every Body by using the draw method 
    }
    
    universe.repaint(); //updates the universe image after it is modified 
    
  }
  
  /**
   * You can use this to pause between frames.
   */
  private static void pause() {
    try {
      Thread.sleep(SLEEP_TIME);
    } catch (InterruptedException interrupt) {}
  }
}
