package code;

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

public class Lol extends AnimationPanel {
    // Constants
    // ----------------------------------

    // Instance Variables
    // ----------------------------------
    int playerX = 250;
    int playerY = 250;
    ArrayList<Rectangle> rects = new ArrayList<Rectangle>();
    Player player = new Player(WIDTH/2, HEIGHT/2);


    // Constructor
    // ----------------------------------
    public Lol() {
        super("LOL", 500,  500);

        // add viewable rectangles
        rects.add(new Rectangle(playerX-10, playerY-10, 20, 20));
        
    }

    // renderFrame
    // ----------------------------------
    protected void renderFrame(Graphics g) {
        // for (Rectangle r: rects) {
        //     g.fillRect(r.x, r.y, r.width, r.height);
        // }

        g.fillRect(playerX-10, playerY-10, 20, 20);

        if(up)
        if(down)

        if(left)
            playerX --;
        if(right)
            playerX ++;
        
    }

    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_UP)
            up = true;
        if(e.getKeyCode()==KeyEvent.VK_DOWN)
            down = true;
        if(e.getKeyCode()==KeyEvent.VK_LEFT)
            left = true;
        if(e.getKeyCode()==KeyEvent.VK_RIGHT)
            right = true;
    }

    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_UP)
            up = false;
        if(e.getKeyCode()==KeyEvent.VK_DOWN)
            down = false;
        if(e.getKeyCode()==KeyEvent.VK_LEFT)
            left = false;
        if(e.getKeyCode()==KeyEvent.VK_RIGHT)
            right = false;
    }
}
