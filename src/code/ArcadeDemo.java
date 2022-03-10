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
import java.awt.Rectangle;
import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

import javax.sound.sampled.Clip;
import javax.swing.plaf.ColorUIResource;

import java.awt.geom.Rectangle2D;

public class ArcadeDemo extends AnimationPanel 
{

    //Constants
    //-------------------------------------------------------

    //Instance Variables
    //-------------------------------------------------------
    PongBall ball = new PongBall();
    BouncyBall blue = new BouncyBall();
    BouncyBall bouncer = new BouncyBall();
    ArrayList<Projectile> laserArray = new ArrayList<Projectile>();
    ArrayList<Projectile> creatureArray = new ArrayList<Projectile>();
    boolean zPressed;
    Random r = new Random();
    ArrayList<BouncyBall> bouncers = new ArrayList<BouncyBall>();
    ArrayList<PongBall> poggers = new ArrayList<PongBall>();
    int[] rectDim = {220, 120, 50, 50};
    int redAmm = 0;
    int lastX;
    int lastY;
    double grav = 1;
    
    ArrayList<Image> images = new ArrayList<Image>();
    //Constructor
    //-------------------------------------------------------
    public ArcadeDemo()
    {   //Enter the name and width and height.  
        super("ArcadeDemo", 640, 480);
        zPressed = false;  
        blue.setColor(Color.BLUE);      
        poggers.add(ball);
    }
       
    //The renderFrame method is the one which is called each time a frame is drawn.
    //-------------------------------------------------------
    protected void renderFrame(Graphics g)
    {
        images.add(i1);
        images.add(i2);
        images.add(i3);
        images.add(i4);
        g.drawImage(background, 0, 0, getWidth(), getHeight(), this);

        //Draw a square that is stationary on the screen.
        g.setColor(new Color(redAmm, 0, 255-redAmm, 122));
        g.fillRect(rectDim[0], rectDim[1], rectDim[2]+frameNumber, rectDim[3]+frameNumber);
        
        // draw new tracker bounds
        g.setColor(new Color(r.nextFloat(), r.nextFloat(), r.nextFloat()));
        g.drawRect((int)(getWidth()*(1.0/3.0)), (int)(getHeight()*(1.0/3.0)), (int)(getWidth()*(1.0/3.0)), (int)(getHeight()*(1.0/3.0)));
        
        //Draw a circle that follows the mouse.
        g.setColor(Color.BLACK);
        if (mouseX > getWidth()*(1.0/3.0) && mouseX < getWidth()*(2.0/3.0) &&
            mouseY > getHeight()*(1.0/3.0) && mouseY < getHeight()*(2.0/3.0)) {
                g.fillOval(mouseX-10, mouseY-10, 20,20);
                lastX = mouseX;
                lastY = mouseY;
            } else {
                g.fillOval(lastX-10, lastY-10, 20,20);
            }
        
        
        //Draw a ball that bounces around the screen.
        g.drawImage(ballImage,ball.getX(),ball.getY(),this);
        ball.animate();

        //Draw a blue ball with gravity! 
        blue.animate(g.getClipBounds());
        blue.draw(g);
        
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

        for (Projectile creature: creatureArray) {
            g.drawImage(images.get(creature.getKeep()), creature.getX(), creature.getY(), this);
            creature.animate();
        }

        // for(PongBall pb: poggers) { 
        //     g.drawImage(ballImage,pb.getX(),pb.getY(),this);
        //     pb.animate();
        // }

        for (int idx = 0; idx < bouncers.size(); idx++) {
            if (bouncers.size() > 0) {
                for (Projectile p: laserArray) {
                        if (bouncers.get(idx).getBounds().contains(p.getRect())){
                            bouncers.remove(idx);
                        }
                }
                bouncers.get(idx).setGravity(grav);
            }
        }

        for (BouncyBall b: bouncers) {
            b.animate(g.getClipBounds());
            b.draw(g);
        }

        for (int i = 0; i < laserArray.size(); i++) {
            if (laserArray.get(i).isFrozen()) {
                laserArray.remove(i);
            }
            if (new Rectangle(rectDim[0], rectDim[1], rectDim[2]+frameNumber, rectDim[3]+frameNumber).contains(laserArray.get(i).getRect())) {
                if (redAmm < 255)
                    redAmm ++;
            }
        }

        for (int i = 0; i < creatureArray.size(); i++) {
            if (creatureArray.get(i).isFrozen()) {
                creatureArray.remove(i);
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
        
        g.drawString("(" + String.valueOf(mouseX) + ", " + String.valueOf(mouseY) + ")", 25, 175);
        g.drawString(String.valueOf(laserArray.size()), 100, 200);

        if (super.frameNumber % 50 == 0) {
            bouncers.add(new BouncyBall(new ColorUIResource(r.nextFloat(), r.nextFloat(), r.nextFloat())));
        }

        if (frameNumber % 5 == 0) {
            Projectile tempstar = new Projectile();
            tempstar.fireWeapon(ball.getX(),ball.getY(),mouseX,mouseY);           
            laserArray.add(tempstar); 
        }

        if (ball.getX() > 640-ball.BALL_WIDTH || ball.getX() < 0) {
            bounce.setFramePosition(0);
            bounce.loop(0);
        }    
        
        if (ball.getY() > 480-ball.BALL_HEIGHT || ball.getY() < 0) {
            bounce.setFramePosition(0);
            bounce.loop(0);
        }
        
        Font myFont = new Font("TimesRoman", Font.PLAIN, 30);
        String scroll = "Number 24! Number 24! ";
        g.setFont(myFont);
        g.drawString(scroll.substring((frameNumber / 10) % 10, ((frameNumber / 10) % 10 + 10)), 100, 175);
        
    }//--end of renderFrame method--
    
    //-------------------------------------------------------
    //Respond to Mouse Events
    //-------------------------------------------------------
    public void mouseClicked(MouseEvent e)  
    {
        if (new Rectangle(rectDim[0], rectDim[1], rectDim[2]+frameNumber, rectDim[3]+frameNumber).contains(new Point(mouseX, mouseY)))
            grav = grav * -1.0;
        ball.setFrozen(!ball.isFrozen()); //Toggle the ball's frozen status.
        laserArray.clear();
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
        if(c=='x' || c=='X') {
            for (int i = 0; i < poggers.size(); i++) {
                poggers.get(i).nudgeTowards(mouseX, mouseY);

            }
        }
        if(c=='r' || c=='R') {
            Projectile tempCreture = new Projectile(r.nextDouble()*2*Math.PI, r.nextInt(4));
            tempCreture.fireWeapon(ball.getX(), ball.getY(), r.nextDouble()*2*Math.PI);
            creatureArray.add(tempCreture);
        }

            
        if(c==' ') //Fire a projectile when spacebar pressed.  
        {
            Projectile tempstar = new Projectile();
            tempstar.fireWeapon(mouseX,mouseY,r.nextDouble()*2*Math.PI);           
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
    Image furryImage;
    Image i1;
    Image i2;
    Image i3;
    Image i4;
    
    public void initGraphics() 
    {      
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        
        ballImage = toolkit.getImage("src/images/tennis-ball.jpg").getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        starImage = toolkit.getImage("src/images/star.gif");
        background = toolkit.getImage("src/images/background.gif");
        i1 = toolkit.getImage("src/images/furry-creature0.png").getScaledInstance(50, 50, Image.SCALE_FAST);
        i2 = toolkit.getImage("src/images/furry-creature1.gif").getScaledInstance(50, 50, Image.SCALE_FAST);
        i3 = toolkit.getImage("src/images/furry-creature2.gif").getScaledInstance(50, 50, Image.SCALE_FAST);
        i4 = toolkit.getImage("src/images/furry-creature3.gif").getScaledInstance(50, 50, Image.SCALE_FAST);
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
    Clip bounce;
    
    public void initMusic() 
    {
        themeMusic = loadClip("src/audio/Arcade-game-background-music.wav");
        bellSound = loadClip("src/audio/ding.wav");
        bounce = loadClip("src/audio/bounce.wav");
        int dx = new Random().nextInt(3);
        switch (dx) {
            case 0:
                themeMusic = loadClip("src/audio/bounce.wav");
                break;
            case 1:
                themeMusic = loadClip("src/audio/give.wav");
                break;
            case 2:
                themeMusic = loadClip("src/audio/halo.wav");
                break;

        }
        if(themeMusic != null)
//            themeMusic.start(); //This would make it play once!
            themeMusic.loop(2); //This would make it loop 2 times.
    }

//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
}//--end of ArcadeDemo class--

