import lejos.nxt.Button;

public class HelloWorld 
{
    public static void main (String[] args) 
    {
        System.out.println("I'm alive :D");
        Button.waitForAnyPress();
    }
}