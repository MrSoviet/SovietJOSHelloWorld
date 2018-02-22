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
    public static final EncoderMotor motorLeft = new UnregulatedMotor(MotorPort.A);
    public static final EncoderMotor motorRight = new UnregulatedMotor(MotorPort.D);
    public static final SensorModes sensorTouch = new EV3TouchSensor(SensorPort.S1);
    public static final SensorModes sensorUltrasonic = new EV3UltrasonicSensor(SensorPort.S2);
    public static final SensorModes sensorColor = new EV3ColorSensor(SensorPort.S3);
    public static final SensorModes sensorGyro = new EV3GyroSensor(SensorPort.S4);

    public static float sampleTouch[];
    public static float sampleRGB[];

    public static boolean run = true;

    public static void main(String[] args) {
        LCD.drawString("helloWorld", 0, 0);

        SampleProvider touch = sensorTouch.getMode("Touch");
        SampleProvider rgb = sensorColor.getMode("RGB");
        sampleTouch = new float[touch.sampleSize()];
        sampleRGB = new float[rgb.sampleSize()];

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

        while(run) {
            /*
            Delay.msDelay(10);

            touch.fetchSample(sampleTouch, 0);
            if (sampleTouch[0] == 1) {
                LCD.drawString("Touched", 0, 1);
            } else {
                LCD.drawString("Released", 0, 1);
            }*/

            rgb.fetchSample(sampleRGB, 0);
            LCD.drawString("R: " + sampleRGB[0], 0, 2);
            LCD.drawString("G: " + sampleRGB[1], 0, 3);
            LCD.drawString("B: " + sampleRGB[2], 0, 4);

            Delay.msDelay(500);
        }
    }
}
