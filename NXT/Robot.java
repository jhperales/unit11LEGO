import lejos.nxt.*;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.objectdetection;

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
        FeatureDetector disscan = new RangeFeatureDetector( us, 35, 500 );
        BoxDetect boxlisten = new BoxListener();
        disscan.addListener( listener );
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

    class BoxDetect implements FeatureListener
    {
        public void featureDetected( Feature feature, FeatureDetector detector )
        {
            int range = (int)feature.getRangeReading().getRange();
            if( range != null )
            {
                angle = Motor.A.getTachoCount();
                if( angle >= -5 && angle <= 5 )
                {
                    if( !pdrive.isMoving() )
                    {
                        pdrive.rotate( 180 );
                    }
                }
                else if( angle < -5 )
                {
                    if( !pdrive.isMoving() )
                    {
                        pdrive.rotateLeft();
                    }
                }
                else if( angle > 5 )
                {
                    if( !pdrive.isMoving() )
                    {
                        pdrive.rotateRight();
                    }
                }
            }
        }
    }
}