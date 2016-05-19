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
    private boolean[] detection = new boolean[3];

    public Robot()
    {
        Motor.A.setSpeed( 750 );
    }

    public boolean detectedBounds()
    {
        if (light.getLightValue() >= 0 && light.getLightValue() < 40)
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
            Motor.A.rotateTo( -90 );
        }
        if (Motor.A.getTachoCount() > 85 && Motor.A.getTachoCount() < 95)
        {
            Motor.A.rotateTo( -180 );
        }
    }

    public void stopMoving()
    {
        Motor.C.stop();
        Motor.B.stop();
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
    
    //270 degree rotation = 90 degree turn (3:1)
    public void rotateLeft( int degree )
    {
        stopMoving();
        Motor.B.rotate( -( degree ) );
        Motor.C.rotate( degree );
    }

    public void rotateRight( int degree )
    {
        stopMoving();
        Motor.B.rotate( degree );
        Motor.C.rotate( -( degree ) );
    }

    public void turnLeft( int degree )
    {
        stopMoving();
        Motor.C.rotate( degree );
    }

    public void turnRight( int degree )
    {
        stopMoving();
        Motor.B.rotate( degree );
    }

    public boolean[] detect()
    {
        /*detection[0] - left
         * detection[1] - front
         * detection[2] - right
         */
        //turns the robot a full rotation (180 degrees) and scans three times CHECK DISTANCE VALUE OUTPUT
        if (Motor.A.getTachoCount() > -95 && Motor.A.getTachoCount() < -85)
        {
            if ( us.getDistance() <= 40 )
            {
                detection[0] = true;
                Motor.A.rotateTo( 90 );
            }
            else
            {
                detection[0] = false;
                Motor.A.rotateTo( 90 );
            }
        }
        if( Motor.A.getTachoCount() > -5 && Motor.A.getTachoCount() < 5 )
        {
            if ( us.getDistance() <= 40 )
            {
                detection[1] = true;
                Motor.A.rotateTo( 90 );
            }
            else
            {
                detection[1] = false;
                Motor.A.rotateTo( 90 );
            }
        }
        if (Motor.A.getTachoCount() > 85 && Motor.A.getTachoCount() < 95)
        {
            if ( us.getDistance() <= 40 )
            {
                detection[2] = true;
                resetHead();
            }
            else
            {
                detection[2] = false;
                resetHead();
            }
        }
        return detection;
    }

    public void patrol()
    {
        setSpeed( 500 );
        while( Button.readButtons() <= 0 )
        {
            if( detectedBounds() )
            {
                turnRight( 60 );
            }
            else if( !detectedBounds() )
            {
                forward();
            }
            if( tLeft.isPressed() || tRight.isPressed() || ( tLeft.isPressed() && tRight.isPressed() ) )
            {
                setSpeed( 250 );
            }
            else
            {
                setSpeed( 500 );
            }
        }
        stopMoving();
    }
}