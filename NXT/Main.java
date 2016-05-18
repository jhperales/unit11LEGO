import lejos.nxt.*;
public class Main
{
    public static void main(String[] args)
    {
        Robot robot = new Robot();
        
        
        //Find opening
        boolean turnL = false;
        while (robot.us.getDistance() <= 10 && robot.us.getDistance() != 255)
        {
            if (Motor.A.getTachoCount() > -5 && Motor.A.getTachoCount() < 5 && turnL == false)
            {
                Motor.A.rotate(90);
                turnL = true;
            }
            else if(Motor.A.getTachoCount() > -5 && Motor.A.getTachoCount() < 5 && turnL == true)
            {
                Motor.A.rotate(-90);
            }
            
            if (Motor.A.getTachoCount() > 85 && Motor.A.getTachoCount() < 95)
            {
                Motor.A.rotate(-90);
            }
            
            if (Motor.A.getTachoCount() > -95 && Motor.A.getTachoCount() < -85)
            {
                Motor.A.rotate(90);
                turnL = false;
            }
            
        }
        
        
        //Get to line
        robot.setSpeed(1000);
        robot.forward();
        
        while (!robot.detectedBounds())
        {
            //Just prevents the rest of the code until the bounds are found
        }
        
        robot.stopMoving();
    }
}