package code;

public class Player {
    private double x;
    private double y;
    private double xVel;
    private double yVel;
    private double g = 1;
    public boolean overGround = true;
    
    public Player(double startX, double startY) {
        x = startX;
        y = startY;
    }

    public void jump() {
        yVel -= 10.0;
    }

    public void jump(double power) {
        yVel -= power;
    }

    public void jump(double power, double direction) {
        yVel -= power * Math.sin(direction);
        xVel -= power * Math.cos(direction);
    }

    public void animate() {
        if (overGround) {
            yVel = 0;
        } else {
            yVel += g;
        }
        x -= xVel;
        y -= yVel;
    }

    public double getX() {return x;}
    public double getY() {return y;}
}
