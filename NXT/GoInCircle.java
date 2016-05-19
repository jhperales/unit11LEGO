import lejos.nxt.*;

public class GoInCircle
{
    public static void main(String[] args)
    {
        Motor.B.setSpeed(1000);
        Motor.C.setSpeed(1000);
        Motor.B.forward();
        Motor.C.forward();

        Button.waitForAnyPress();
    }
}