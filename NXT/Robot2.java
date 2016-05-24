import lejos.nxt.*;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.objectdetection;
import lejos.util.Delay;

/**
 * 
 */
public class Robot2
{

    public UltrasonicSensor us = new UltrasonicSensor(SensorPort.S1);
    public LightSensor light = new LightSensor(SensorPort.S2);
    public TouchSensor tLeft = new TouchSensor(SensorPort.S3);
    public TouchSensor tRight = new TouchSensor(SensorPort.S4);
    public DifferentialPilot pdrive = new DifferentialPilot( 56, 56, Motor.B, Motor.C );

    public Robot2()
    {
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

    public void leaveBox()
    {
        pdrive.setRotateSpeed( 540 );
        pdrive.forward();
        while( !detectBounds() )
        {
            
        }
        patrol();
    }

    public void patrol()
    {
        while( Button.readButtons() <= 0 )
        {
            if( detectedBounds() )
            {
                pdrive.rotateRight();
            }
            else if( !detectedBounds() )
            {
                forward();
            }
            if( tLeft.isPressed() || tRight.isPressed() || ( tLeft.isPressed() && tRight.isPressed() ) )
            {
                setSpeed( 270 );
            }
            else
            {
                setSpeed( 540 );
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
                pdrive.rotateRight();
                Feature f = detector.scan();
                if( f == null )
                {
                    pdrive.setRotateSpeed( 360 );
                    pdrive.rotateRight();
                    Delay.msDelay( 1000 );
                    robot2.leaveBox();
                }
            }
        }
    }
}