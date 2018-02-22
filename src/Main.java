import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;

public class Main {
    public static void main(String[] args) {
        LCD.drawString("helloWorld", 0, 0);
        Button.waitForAnyPress();
    }
}
