import lejos.nxt.*;
import lejos.util.*;

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
    private boolean[] detection = new boolean[3];
    private boolean turnL;
    private Delay timer;

    public Robot()
    {
        knockCount = 0;
        turnL = false;
        Motor.A.setSpeed( 1000 );
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
        int angle = Motor.A.getTachoCount();
        Motor.A.rotate( -( angle - (-90) ) );
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

    public void turnLeft( int time )
    {
        Motor.B.backward();
        Motor.C.forward();
        timer.msDelay( time );
        stopMoving();
    }

    public void turnRight( int time )
    {
        Motor.B.forward();
        Motor.C.backward();
        timer.msDelay( time );
        stopMoving();
    }

    public boolean[] detect()
    {
        /*detection[0] - left
         * detection[1] - front
         * detection[2] - right
         */
        //turns the robot a full rotation (180 degrees) and scans three times
        if ( us.getDistance() <= 40 )
        {
            if( Motor.A.getTachoCount() > -5 && Motor.A.getTachoCount() < 5 )
            {
                detection[1] = true;
                Motor.A.rotate( 90 );
            }
            if (Motor.A.getTachoCount() > 85 && Motor.A.getTachoCount() < 95)
            {
                detection[2] = true;
                Motor.A.rotate( -90 );
            }
            if (Motor.A.getTachoCount() > -95 && Motor.A.getTachoCount() < -85)
            {
                detection[0] = true;
                Motor.A.rotate( 90 );
                turnL = false;
            }
        }
        if( Motor.A.getTachoCount() > -5 && Motor.A.getTachoCount() < 5 && turnL == false )
        {
            detection[1] = false;
            Motor.A.rotate( 90 );
            turnL = true;
        }
        else if( Motor.A.getTachoCount() > -5 && Motor.A.getTachoCount() < 5 && turnL == true )
        {
            detection[1] = false;
            Motor.A.rotate( -90 );
        }
        if (Motor.A.getTachoCount() > 85 && Motor.A.getTachoCount() < 95)
        {
            detection[2] = false;
            Motor.A.rotate( -90 );
        }
        if (Motor.A.getTachoCount() > -95 && Motor.A.getTachoCount() < -85)
        {
            detection[0] = false;
            Motor.A.rotate( 90 );
            turnL = false;
        }
        resetHead();
        return detection;
    }

    public void patrol()
    {
        while( knockCount != 6 )
        {
            turnRight( 20 );
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
        stopMoving();
    }
}