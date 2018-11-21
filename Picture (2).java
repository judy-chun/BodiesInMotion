/**File Name: Picture.java
  * Created by: Johnny Luong, cs8afqr, and Judy Chun, cs8afug
  * Date: 12/3/2017
  */ 


import java.awt.*;
import java.awt.font.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.text.*;
import java.util.*;
import java.util.List; // resolves problem with java.awt.List and java.util.List

/**
 * A class that represents a picture.  This class inherits from 
 * SimplePicture and allows the student to add functionality to
 * the Picture class.  
 * 
 * Copyright Georgia Institute of Technology 2004-2005
 * @author Barbara Ericson ericson@cc.gatech.edu
 */
public class Picture extends SimplePicture 
{
  ///////////////////// constructors //////////////////////////////////
  
  /**
   * Constructor that takes no arguments 
   */
  public Picture ()
  {
    /* not needed but use it to show students the implicit call to super()
     * child constructors always call a parent constructor 
     */
    super();  
  }
  
  /**
   * Constructor that takes a file name and creates the picture 
   * @param fileName the name of the file to create the picture from
   */
  public Picture(String fileName)
  {
    // let the parent class handle this fileName
    super(fileName);
  }
  
  /**
   * Constructor that takes the width and height
   * @param width the width of the desired picture
   * @param height the height of the desired picture
   */
  public Picture(int width, int height)
  {
    // let the parent class handle this width and height
    super(width,height);
  }
  
  /**
   * Constructor that takes a picture and creates a 
   * copy of that picture
   */
  public Picture(Picture copyPicture)
  {
    // let the parent class do the copy
    super(copyPicture);
  }
  
  /**
   * Constructor that takes a buffered image
   * @param image the buffered image to use
   */
  public Picture(BufferedImage image)
  {
    super(image);
  }
  
  ////////////////////// methods ///////////////////////////////////////
  
  
  /**
   * Method to return a string with information about this picture.
   * @return a string with information about the picture such as fileName,
   * height and width.
   */
  public String toString()
  {
    String output = "Picture, filename " + getFileName() + 
      " height " + getHeight() 
      + " width " + getWidth();
    return output;
    
  }
  
  
  /**
   * Blend the calling object picture into the parameter Picture
   * object with the upper left corner at (x, y)
   * Input: x - The backgrounds top left corner x coordinate
   *           y - The backgrounds top left corner y coordinate
   *           background - The background picture to blend into
   * Returns: nothing
   */
  
  public void alphaBlending(int x, int y, Picture background)
  {
    //Create all the variables needed for the alphaBlending algorithm
    double alphaValue;
    
    int sourceR, sourceG, sourceB;
    
    int targetR, targetG, targetB;
    
    int alphaR, alphaG, alphaB;
    
    Pixel sourcePixel;  //pixel of calling object
    Pixel backPixel;    //pixel of background picture
    
    //statement checks for out of bound error
    if( x + this.getWidth() < background.getWidth() && y +this.getHeight() < background.getHeight() && x > 0 && y > 0){
      //loop through calling object 
      for(int sourceX = 0, backX = x; sourceX < this.getWidth(); sourceX++, backX++)
      {
        for(int sourceY = 0, backY = y; sourceY < this.getHeight(); sourceY++, backY++)
        {
          //Retrieve RGB values from the source picture and background picture and use them in the alphaBlending algorithm
          sourcePixel = this.getPixel(sourceX, sourceY);
          backPixel = background.getPixel(backX, backY);
          alphaValue = sourcePixel.getAlpha(); 
          
          sourceR = sourcePixel.getRed();   
          sourceG = sourcePixel.getGreen();
          sourceB = sourcePixel.getBlue();
          
          targetR = backPixel.getRed();
          targetG = backPixel.getGreen();
          targetB = backPixel.getBlue();
          
          //calculating the new RGB values using alpha value 
          alphaR = (int)(alphaValue / 255 * sourceR + (1 - alphaValue / 255) * targetR);
          alphaG = (int)(alphaValue / 255 * sourceG + (1 - alphaValue / 255) * targetG);
          alphaB = (int)(alphaValue / 255 * sourceB + (1 - alphaValue / 255) * targetB);
          
          backPixel.setColor(new Color(alphaR, alphaG, alphaB)); //setting background with new color 
        }
      }
    } 
  }
  
  
} // this } is the end of class Picture, put all new methods before this

