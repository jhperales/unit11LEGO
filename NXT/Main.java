import lejos.nxt.*;

public class Main
{
    public static void main(String[] args)
    {
        Robot robot = new Robot();
        robot.resetHead();
        robot.patrol();
        //Find opening
        boolean moveOut = false;
        while( moveOut != true )
        {
            boolean[] result = robot.detect();
            if( result[0] == true && result[1] == true && result[2] == true )
            {
                //turns robot 180 degrees
                robot.pdrive.rotate( -180 );
            }
            if( result[0] == false && result[1] == true && result[2] == true )
            {
                //turns robot 90 degrees left
                robot.pdrive.rotate( 90 );
            }
            if( result[0] == false && result[1] == false && result[2] == true )
            {
                //turns robot 45 degrees left
                robot.pdrive.rotate( 45 );
            }
            if( result[0] == true && result[1] == true && result[2] == false )
            {
                //turns robot 90 degrees right
                robot.pdrive.rotate( -90 );
            }
            if( result[0] == true && result[1] == false && result[2] == false )
            {
                //turns robot 45 degrees right
                robot.pdrive.rotate( -45 );
            }
            if( result[0] == false && result[1] == false && result[2] == false || 
            result[0] == true && result[1] == false && result[2] == true )
            {
                moveOut = true;
            }
        }

        //Get to line
        robot.setSpeed(1000);
        robot.forward();

        while (!robot.detectedBounds())
        {
            //Just prevents the rest of the code until the bounds are found
        }

        robot.setSpeed( 500 );
        robot.patrol();
    }
}