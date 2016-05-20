import lejos.nxt.*;
import lejos.robotics.navigation.DifferentialPilot;

/**
 * 
 */
public class Robot
{

    public UltrasonicSensor us = new UltrasonicSensor(SensorPort.S1);
    public LightSensor light = new LightSensor(SensorPort.S2);
    public TouchSensor tLeft = new TouchSensor(SensorPort.S3);
    public TouchSensor tRight = new TouchSensor(SensorPort.S4);
    public DifferentialPilot pdrive = new DifferentialPilot( 18, 18, Motor.B, Motor.C );
    private boolean[] detection = new boolean[3];

    public Robot()
    {
        for( boolean a : detection )
        {
            a = true;
        }
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
        if( Motor.A.getTachoCount() > -2 && Motor.A.getTachoCount() < 2 )
        {
            Motor.A.rotateTo( -90 );
        }
        if (Motor.A.getTachoCount() > 88 && Motor.A.getTachoCount() < 92)
        {
            Motor.A.rotateTo( -90 );
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
        //turns the head a full rotation (180 degrees) and scans three times CHECK DISTANCE VALUE OUTPUT
        if (Motor.A.getTachoCount() > -92 && Motor.A.getTachoCount() < -88)
        {
            if ( us.getDistance() <= 28 )
            {
                detection[0] = true;
                Motor.A.rotateTo( 0 );
            }
            else
            {
                detection[0] = false;
                Motor.A.rotateTo( 0 );
            }
        }
        if( Motor.A.getTachoCount() > -2 && Motor.A.getTachoCount() < 2 )
        {
            if ( us.getDistance() <= 28 )
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
        if (Motor.A.getTachoCount() > 88 && Motor.A.getTachoCount() < 92)
        {
            if ( us.getDistance() <= 28 )
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
        while( Button.readButtons() <= 0 )
        {
            if( detectedBounds() )
            {
                turnRight( 720 );
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