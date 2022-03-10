package code.galaga;

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

import code.AnimationPanel;

import java.awt.geom.Rectangle2D;

public class Galaga extends AnimationPanel{
    Boolean mouseControl = false;

    public Galaga() {
        super("Galaga", 640, 800);
    }

    protected void renderFrame(Graphics g) {
        // g.drawImage(img, x, y, observer)
    }

    public void keyTyped(KeyEvent e) {
        if (!mouseControl)
    }

    Image mainShip;
    public void initGraphics() {
        Toolkit tk = Toolkit.getDefaultToolkit();
        mainShip = tk.getImage("src/images/galaga.gif");
    }

    Clip themeMusic;
    public void initMusic() {
        themeMusic = loadClip("src/audio/");
    }
}
