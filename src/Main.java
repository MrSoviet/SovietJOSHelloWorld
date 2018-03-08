import lejos.hardware.Sound;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.UnregulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.*;

// ================================= EV38 ================================= //
public class Main {
    public static final UnregulatedMotor MOTOR_LEFT = new UnregulatedMotor(MotorPort.A);
    public static final UnregulatedMotor MOTOR_RIGHT = new UnregulatedMotor(MotorPort.D);
    public static final EV3TouchSensor SENSOR_TOUCH = new EV3TouchSensor(SensorPort.S1);
    public static final EV3UltrasonicSensor SENSOR_ULTRASONIC = new EV3UltrasonicSensor(SensorPort.S2);
    public static final EV3ColorSensor SENSOR_COLOR = new EV3ColorSensor(SensorPort.S3);
    //public static final EV3GyroSensor SENSOR_GYRO = new EV3GyroSensor(SensorPort.S4);

    public static final ColorClass COLOR_CLASS = new ColorClass(SENSOR_COLOR);
    public static final TouchClass TOUCH_CLASS = new TouchClass(SENSOR_TOUCH);
    public static final EscapeButtonClass ESCAPE_BUTTON_CLASS = new EscapeButtonClass();

    public static boolean run = true;

    public static void main(String[] args) {
        LCD.drawString("helloWorld", 0, 0);

        Sound.twoBeeps();
        Sound.beepSequenceUp();

        ESCAPE_BUTTON_CLASS.run();
        COLOR_CLASS.run();
        TOUCH_CLASS.run();

        /*Button.ESCAPE.addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(Key key) {
                if (key.getId() == Button.ID_ESCAPE) {
                    run = false;
                    COLOR_CLASS.currentThread().interrupt();
                    TOUCH_CLASS.currentThread().interrupt();
                }
            }

            @Override
            public void keyReleased(Key key) {

            }
        });*/

        //COLOR_CLASS.currentThread().interrupt();
        //TOUCH_CLASS.currentThread().interrupt();
        LCD.clear();
    }
}
