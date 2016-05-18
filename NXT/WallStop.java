import lejos.nxt.*;
public class WallStop
{
    public static void main(String[] args)
    {
        Motor.B.setSpeed(1000);
        Motor.C.setSpeed(1000);

        Motor.B.forward();
        Motor.C.forward();

        UltrasonicSensor us = new UltrasonicSensor(SensorPort.S1);

        boolean turnL = false;
        while(Button.readButtons() <= 0)
        {
            if (us.getDistance() <= 10 && us.getDistance() != 255)
            {
                Motor.B.stop();
                Motor.C.stop();
                break;
            }
            
            
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
    }
}

