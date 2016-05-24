import lejos.nxt.*;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.objectdetection.FeatureListener;
import lejos.robotics.objectdetection.Feature;
import lejos.robotics.objectdetection.FeatureDetector;
import lejos.robotics.objectdetection.RangeFeatureDetector;
import lejos.robotics.RangeReading;
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

    public static void main( String[] args )
    {
        Robot2 robot = new Robot2();
        while( ( Button.waitForAnyPress(1) & Button.ID_ENTER ) == 0)
        {

        }
        Button.waitForAnyPress();
    }

    public Robot2()
    {
        FeatureDetector disscan = new RangeFeatureDetector( us, 255, 100 );
        BoxDetect boxlisten = new BoxDetect();
        disscan.addListener( boxlisten );
        pdrive.setRotateSpeed( 540 );
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
        pdrive.setTravelSpeed( 120 );
        pdrive.forward();
        while( !detectedBounds() )
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
                pdrive.setTravelSpeed( 50 );
                pdrive.rotateRight();
            }
            else if( !detectedBounds() )
            {
                pdrive.setTravelSpeed( 150 );
                pdrive.forward();
            }
        }
        pdrive.stop();
    }

    class BoxDetect implements FeatureListener
    {
        public void featureDetected( Feature feature, FeatureDetector detector )
        {
            RangeReading range = feature.getRangeReading();
            if( range != null && range.getRange() <= 30 )
            {
                System.out.println( range.getRange() );
                pdrive.rotateRight();
            }
            else
            {
                System.out.println( "Detected" + range.getRange() );
                leaveBox();
            }

        }
    }
}