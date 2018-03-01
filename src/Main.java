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
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;

// ================================= EV38 ================================= //
public class Main {
    public static final EncoderMotor MOTOR_LEFT = new UnregulatedMotor(MotorPort.A);
    public static final EncoderMotor MOTOR_RIGHT = new UnregulatedMotor(MotorPort.D);
    public static final SensorModes SENSOR_TOUCH = new EV3TouchSensor(SensorPort.S1);
    public static final SensorModes SENSOR_ULTRASONIC = new EV3UltrasonicSensor(SensorPort.S2);
    public static final SensorModes SENSOR_COLOR = new EV3ColorSensor(SensorPort.S3);
    public static final SensorModes SENSOR_GYRO = new EV3GyroSensor(SensorPort.S4);

    public static final ColorClass COLOR_CLASS = new ColorClass(SENSOR_COLOR);

    public static float sampleTouch[];

    public static boolean run = true;

    public static void main(String[] args) {
        LCD.drawString("helloWorld", 0, 0);

        SampleProvider touch = SENSOR_TOUCH.getMode("Touch");
        sampleTouch = new float[touch.sampleSize()];

        Button.ESCAPE.addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(Key key) {
                if (key.getId() == Button.ID_ESCAPE) {
                    run = false;
                }
            }

            @Override
            public void keyReleased(Key key) {

            }
        });

        Sound.twoBeeps();
        Sound.beepSequenceUp();


        Thread thread = new Thread(COLOR_CLASS);
        thread.start();

        while(run) {
            /*
            Delay.msDelay(10);

            touch.fetchSample(sampleTouch, 0);
            if (sampleTouch[0] == 1) {
                LCD.drawString("Touched", 0, 1);
            } else {
                LCD.drawString("Released", 0, 1);
            }*/
        }
    }


}
