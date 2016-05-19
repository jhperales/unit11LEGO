import lejos.nxt.*;

/**
 * 
 */
public class Robot
{

    public UltrasonicSensor us = new UltrasonicSensor(SensorPort.S1);
    public LightSensor light = new LightSensor(SensorPort.S2);
    public TouchSensor tLeft = new TouchSensor(SensorPort.S3);
    public TouchSensor tRight = new TouchSensor(SensorPort.S4);
    private int knockCount;
    private boolean[] detection;
    private boolean turnL;

    public Robot()
    {
        knockCount = 0;
        turnL = false;
        Motor.A.setSpeed( 1000 );
    }

    public boolean detectedBounds()
    {
        System.out.println(light.getLightValue());
        if (light.getLightValue() >= 0 && light.getLightValue() < 30)
        {
            return true;
        }

        return false;
    }

    public void resetHead()
    {
        //resets the head to face left
        if( Motor.A.getTachoCount() > -5 && Motor.A.getTachoCount() < 5 )
        {
            Motor.A.rotate(-90);
        }
        if (Motor.A.getTachoCount() > 85 && Motor.A.getTachoCount() < 95)
        {
            Motor.A.rotate(-180);
        }
    }

    public void stopMoving()
    {
        Motor.C.stop();
        Motor.B.stop();
    }

    public boolean isOut()
    {
        return out;
    }

    public void setSpeed(int speed)
    {
        Motor.B.setSpeed(speed);
        Motor.C.setSpeed(speed);
    }

    public void forward()
    {
        Motor.B.forward();
        Motor.C.forward();
    }

    public void turnLeft( int time )
    {
        Motor.B.backward();
        Motor.C.forward();
        msDelay( time );
        stopMoving();
    }

    public void turnRight( int time )
    {
        Motor.B.forward();
        Motor.C.backward();
        msDelay( time );
        stopMoving();
    }

    public boolean[] detect()
    {
        /*detection[0] - left
         * detection[1] - front
         * detection[2] - right
         */
        //turns the robot a full rotation (180 degrees) and scans three times
        for( int i = 0; i < 3; i++ )
        {
            if ( us.getDistance() <= 20 && us.getDistance() != 255 )
            {
                if( Motor.A.getTachoCount() > -5 && Motor.A.getTachoCount() < 5 && turnL == false )
                {
                    detection[1] = true;
                    Motor.A.rotate(90);
                    turnL = true;
                }
                else if( Motor.A.getTachoCount() > -5 && Motor.A.getTachoCount() < 5 && turnL == true )
                {
                    detection[1] = true;
                    Motor.A.rotate(-90);
                }
                if (Motor.A.getTachoCount() > 85 && Motor.A.getTachoCount() < 95)
                {
                    detection[2] = true;
                    Motor.A.rotate(-90);
                }
                if (Motor.A.getTachoCount() > -95 && Motor.A.getTachoCount() < -85)
                {
                    detection[0] = true;
                    Motor.A.rotate(90);
                    turnL = false;
                }
            }
            if( Motor.A.getTachoCount() > -5 && Motor.A.getTachoCount() < 5 && turnL == false )
            {
                detection[1] = false;
                Motor.A.rotate(90);
                turnL = true;
            }
            else if( Motor.A.getTachoCount() > -5 && Motor.A.getTachoCount() < 5 && turnL == true )
            {
                detection[1] = false;
                Motor.A.rotate(-90);
            }
            if (Motor.A.getTachoCount() > 85 && Motor.A.getTachoCount() < 95)
            {
                detection[2] = false;
                Motor.A.rotate(-90);
            }
            if (Motor.A.getTachoCount() > -95 && Motor.A.getTachoCount() < -85)
            {
                detection[0] = false;
                Motor.A.rotate(90);
                turnL = false;
            }
        }
        return detection;
    }

    public void patrol()
    {
        while( knockCount != 6 )
        {
            turnRight( 250 );
            forward();
            if( detectedBounds() )
            {
                stopMoving();
            }
            if( tLeft.isPressed() )
            {
                knockCount += 1;
            }
        }
        stopAllMotors();
    }
}