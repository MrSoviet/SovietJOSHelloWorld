import lejos.hardware.Button;
import lejos.hardware.Key;
import lejos.hardware.KeyListener;
import lejos.hardware.Sound;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.UnregulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.*;
import lejos.robotics.EncoderMotor;

// ================================= EV38 ================================= //
public class Main {
    public static final EncoderMotor MOTOR_LEFT = new UnregulatedMotor(MotorPort.A);
    public static final EncoderMotor MOTOR_RIGHT = new UnregulatedMotor(MotorPort.D);
    public static final SensorModes SENSOR_TOUCH = new EV3TouchSensor(SensorPort.S1);
    public static final SensorModes SENSOR_ULTRASONIC = new EV3UltrasonicSensor(SensorPort.S2);
    public static final SensorModes SENSOR_COLOR = new EV3ColorSensor(SensorPort.S3);
    public static final SensorModes SENSOR_GYRO = new EV3GyroSensor(SensorPort.S4);

    public static final ColorClass COLOR_CLASS = new ColorClass(SENSOR_COLOR);
    public static final TouchClass TOUCH_CLASS = new TouchClass(SENSOR_TOUCH);



    public static boolean run = true;

    public static void main(String[] args) {
        LCD.drawString("helloWorld", 0, 0);

        COLOR_CLASS.run();
        TOUCH_CLASS.run();

        Button.ESCAPE.addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(Key key) {
                if (key.getId() == Button.ID_ESCAPE) {
                    run = false;
                    COLOR_CLASS.interrupt();
                    TOUCH_CLASS.interrupt();
                }
            }

            @Override
            public void keyReleased(Key key) {

            }
        });

        Sound.twoBeeps();
        Sound.beepSequenceUp();
    }


}
