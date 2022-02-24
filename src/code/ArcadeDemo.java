package code;

/**
 * Class ArcadeDemo
 * This class contains demos of many of the things you might
 * want to use to make an animated arcade game.
 * 
 * Adapted from the AppletAE demo from years past. 
 */

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.sound.sampled.Clip;

import java.awt.geom.Rectangle2D;

public class ArcadeDemo extends AnimationPanel 
{

    //Constants
    //-------------------------------------------------------

    //Instance Variables
    //-------------------------------------------------------
    PongBall ball = new PongBall();
    BouncyBall bouncer = new BouncyBall();
    ArrayList<Projectile> laserArray = new ArrayList<Projectile>();
    boolean zPressed;

    //Constructor
    //-------------------------------------------------------
    public ArcadeDemo()
    {   //Enter the name and width and height.  
        super("ArcadeDemo", 640, 480);
        zPressed = false;        
    }
       
    //The renderFrame method is the one which is called each time a frame is drawn.
    //-------------------------------------------------------
    protected void renderFrame(Graphics g)
    {
        g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
        //Draw a circle that follows the mouse.
        g.setColor(Color.BLACK);
        g.fillOval(mouseX-10, mouseY-10, 20,20);
        
        //Draw a ball that bounces around the screen.
        g.drawImage(ballImage,ball.getX(),ball.getY(),this);
        ball.animate();
        
        //Draw a ball with gravity! 
        bouncer.animate(g.getClipBounds());
        bouncer.draw(g);

        //Draw any 'lasers' that have been fired (spacebar).
        for(Projectile p : laserArray)
        {
            //Drawing element z from the array
            g.setColor(Color.RED);
            g.fillRect(p.getX(), p.getY(), 5,15);
            p.animate();   
        }

        for (int i = 0; i < laserArray.size(); i++) {
            if (laserArray.get(i).isFrozen()) {
                laserArray.remove(i);
            }
        }
        
        //General Text (Draw this last to make sure it's on top.)
        g.setColor(Color.WHITE);
        g.drawString("ArcadeEngine 2021", 10, 12);
        g.drawString("#"+frameNumber,200,12);
        if(zPressed) g.drawString("Hooray!", 400, 400);

        char[] h = {'K', 'e', 'l', 'd', 'e', 'n'};
        if (super.frameNumber > 200)
            g.drawChars(h, 0, 6, 100, 100);

        char[] COLLISION = {'C', 'O', 'L', 'L', 'I', 'S', 'I', 'O', 'N'};
        if (ball.getRect().contains(new Point(super.mouseX, super.mouseY)))
            g.drawChars(COLLISION, 0, 9, 100, 150);
        
    }//--end of renderFrame method--
    
    //-------------------------------------------------------
    //Respond to Mouse Events
    //-------------------------------------------------------
    public void mouseClicked(MouseEvent e)  
    { 
        ball.setFrozen(!ball.isFrozen()); //Toggle the ball's frozen status.
    }
    
    //-------------------------------------------------------
    //Respond to Keyboard Events
    //-------------------------------------------------------
    public void keyTyped(KeyEvent e) 
    {
        char c = e.getKeyChar();
        //Use the IJKM keys to move the PongBall
        if(c=='i' || c=='I') 
            ball.moveUp();
        if(c=='j' || c=='J') 
            ball.moveLeft();
        if(c=='k' || c=='K') 
            ball.moveRight();
        if(c=='m' || c=='M') 
            ball.moveDown();
        if(c=='q' || c=='Q') {
            ball.setX(0);
            ball.setY(0);
        }
        if(c=='w' || c=='W')
            ball.increaseSpeed();
        if(c=='s' || c=='S')
            ball.decreaseSpeed();
            
        if(c==' ') //Fire a projectile when spacebar pressed.  
        {
            Projectile tempstar = new Projectile();
            tempstar.fireWeapon(mouseX,mouseY);           
            laserArray.add(tempstar);            
        }
    }
    
    public void keyPressed(KeyEvent e)
    {
        int v = e.getKeyCode();
        
        if(v == KeyEvent.VK_Z)
            zPressed = true;
    }

    public void keyReleased(KeyEvent e)
    {
        int v = e.getKeyCode();
        
        if(v == KeyEvent.VK_Z)
            zPressed = false;
    }
    
    
    //-------------------------------------------------------
    //Initialize Graphics
    //-------------------------------------------------------
//-----------------------------------------------------------------------
/*  Image section... 
 *  To add a new image to the program, do three things.
 *  1.  Make a declaration of the Image by name ...  Image imagename;
 *  2.  Actually make the image and store it in the same directory as the code.
 *  3.  Add a line into the initGraphics() function to load the file. 
//-----------------------------------------------------------------------*/
    Image ballImage;        
    Image starImage;
    Image background;
    
    public void initGraphics() 
    {      
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        
        ballImage = toolkit.getImage("src/images/tennis-ball.jpg").getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        starImage = toolkit.getImage("src/images/star.gif");
        background = toolkit.getImage("src/images/background.gif");
    } //--end of initGraphics()--
    
    //-------------------------------------------------------
    //Initialize Sounds
    //-------------------------------------------------------
//-----------------------------------------------------------------------
/*  Music section... 
 *  To add music clips to the program, do four things.
 *  1.  Make a declaration of the AudioClip by name ...  AudioClip clipname;
 *  2.  Actually make/get the .wav file and store it in the same directory as the code.
 *  3.  Add a line into the initMusic() function to load the clip. 
 *  4.  Use the play(), stop() and loop() functions as needed in your code.
//-----------------------------------------------------------------------*/
    Clip themeMusic;
    Clip bellSound;
    
    public void initMusic() 
    {
        themeMusic = loadClip("src/audio/under.wav");
        bellSound = loadClip("src/audio/ding.wav");
        if(themeMusic != null)
//            themeMusic.start(); //This would make it play once!
            themeMusic.loop(2); //This would make it loop 2 times.
    }

//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
}//--end of ArcadeDemo class--

