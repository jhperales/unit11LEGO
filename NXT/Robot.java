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
    
    /**
     * 
     */
    public Robot()
    {
        
    }
    
    /**
     * 
     */
    public void turnRobot(int degrees)
    {
        
    }
    
    /**
     *
     */
    public boolean detectedBounds()
    {
        System.out.println(light.getLightValue());
        if (light.getLightValue() >= 0 && light.getLightValue() < 30)
        {
            return true;
        }
        
        return false;
    }
    
    public void stopMoving()
    {
        Motor.C.stop();
        Motor.B.stop();
    }
    
    /**
     * 
     */
    public void stopAllMotors()
    {
        Motor.A.stop();
        Motor.B.stop();
        Motor.C.stop();
    }
    
    /**
     * 
     */
    public void turnHead(int degree)
    {
        Motor.A.rotate(degree);
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
    
    
}