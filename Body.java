/**File Name: Body.java
  * Created by: Johnny Luong, cs8afqr, and Judy Chun, cs8afug
  * Date: 12/3/2017
  */ 

import java.awt.*;


/**
 * This is a class that represents a single planet, particle, or actor in a
 * system.
 */
public class Body {
  
  private static final double GRAVITATIONAL_CONSTANT = 6.67E-11;
  
  public static double TIME_STEP;
  public static double UNIVERSE_SIZE;
  
  private double xPosition;
  private double yPosition;
  private double xVelocity;
  private double yVelocity;
  private double mass;
  private Picture image;
  
  /**
   * Full constructor for this body.
   * 
   * @param xPosition The starting x coordinate in the universe.
   * @param yPosition The starting y coordinate in the universe.
   * @param xVelocity The starting momentum in the x direction.
   * @param yVelocity The starting momentum in the y direction.
   * @param mass The mass of the body, used to apply gravity.
   * @param image The picture to draw for this body.
   */
  public Body(double xPosition, double yPosition, double xVelocity,
              double yVelocity, double mass, Picture image) {
    this.xPosition = xPosition;
    this.yPosition = yPosition;
    this.xVelocity = xVelocity;
    this.yVelocity = yVelocity;
    this.mass = mass;
    this.image = image;
    
  }
  
  /**
   * Getter for this body's x position.
   * Input: nothing 
   * @return The body's x position, in the universe.
   */ 
  public double getXPosition()
  {
    return this.xPosition;
  }
  
  /**
   * Getter for this body's y position.
   * Input: nothing 
   * @return The body's y position, in the universe.
   */
  public double getYPosition()
  {
    return this.yPosition;
  }
  
  /**
   * Getter for the mass of this body.
   * Input: nothing 
   * @return The body's mass.
   */
  public double getMass()
  {
    return this.mass;
  }
  
  /**
   * Get the distance to another body.
   * Input: otherBody to compare 
   * @param otherBody The body to compare for distance to this one.
   * @return The distance to the other body.
   */
  public double getDistance(Body otherBody)
  {
    //calculating difference between each body's x and y coordinates 
    double deltaX = otherBody.getXPosition() -this.getXPosition();
    double deltaY = otherBody.getYPosition() -this.getYPosition();
    //using pythagorean theorem 
    double distance = Math.hypot(deltaX, deltaY);
    return distance;
  }

/**
 * Calculate the magnitude of the acceleration between this body and another.
 * Input: otherBody to calculate the magnitude 
 * @param otherBody The other body to accelerate towards.
 * @return The magnitude of acceleration to the other body.
 */
public double getAcceleration(Body otherBody)
{
  //applying formula with gravitational constant, mass of other Body, and distance between them
  double distanceSquared = this.getDistance(otherBody) * this.getDistance(otherBody);
  double acceleration = GRAVITATIONAL_CONSTANT*otherBody.getMass()/distanceSquared;
  
  return acceleration;
}

/**
 * Apply the acceleration due to gravity on this body from another.
 * Input: otherBody 
 * Return: nothing 
 * @param planet The body to accelerate towards.
 */
public void updateVelocity(Body otherBody)
{
  //statement checks for collision 
  if(this.getDistance(otherBody) != 0){
    //breaking Body's velocity into x and y components 
    double deltaX = otherBody.getXPosition() - this.getXPosition();
    double deltaY = otherBody.getYPosition() - this.getYPosition();
      
      //calculate the total magnitude of the acceleration towards the other body in x and y direction 
      double accelerationX = this.getAcceleration(otherBody) * (deltaX/this.getDistance(otherBody));
      double accelerationY = this.getAcceleration(otherBody) * (deltaY/this.getDistance(otherBody));
      
      //modifying the values of xVelocity and yVelocity in the calling object
      xVelocity = xVelocity + accelerationX * TIME_STEP;
      yVelocity = yVelocity + accelerationY * TIME_STEP;
      
    }
  }
  
  /**
   * Move the body based on its current velocity.
   * Input: nothing
   * Return: nothing 
   */ 
  public void move()
  {
    //update the locations of the bodies using current location and new velocity 
    xPosition = xPosition + (xVelocity * TIME_STEP);
    yPosition = yPosition + (yVelocity * TIME_STEP);
  }
  
  /**
   * Calculate the x pixel location where the center of this body will be drawn.
   * Input: nothing 
   * @return The x pixel location of the center of this body.
   */
  public int centerXToPixelPosition()
  {
    //round down the x coordinate of the corresponding pixel 
    int xCoordinate = (int)Math.floor((this.getXPosition()/UNIVERSE_SIZE)*512);
    return xCoordinate; 
  }
  
  /**
   * Calculate the y pixel location where the center of this body will be drawn.
   * Input: nothing
   * @return The y pixel location of the center of this body.
   */
  public int centerYToPixelPosition()
  {
    //round down the y coordinate of the corresponding pixel 
    int yCoordinate = (int)Math.floor((this.getYPosition()/UNIVERSE_SIZE)*512);
    return yCoordinate; 
  }
  
  
  /**
   * Draw the planet for the current frame on the image.
   * Input: Picture object called universe
   * Return: nothing 
   * @param universe The picture on which to draw the planet.
   */
  public void draw(Picture universe)
  {
    //calling alphaBlending method to draw the body onto the picture that is passed in method 
    image.alphaBlending(this.centerXToPixelPosition()-image.getWidth()/2, 
                        this.centerYToPixelPosition()-image.getHeight()/2, universe);
  }
  
  /**
   * Serialize this planet such that it can be read and converted to a planet
   * object from a text file.
   * 
   * @return A line that contains all the information about this planet.
   */ 
  public String toString()
  {
    String serialization = xPosition + " " + yPosition + " " +
      xVelocity +  " " + yVelocity + " " + mass;
    serialization += " " + image.getFileName();
    return serialization;
  }
}
