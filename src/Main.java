import lejos.hardware.Button;
import lejos.hardware.Sound;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.UnregulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.hardware.sensor.SensorModes;
import lejos.robotics.EncoderMotor;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;

// ================================= EV38 ================================= //
public class Main {
    public static final EncoderMotor motorLeft = new UnregulatedMotor(MotorPort.C);
    public static final EncoderMotor motorRight = new UnregulatedMotor(MotorPort.B);
    public static final SensorModes sensor = new EV3TouchSensor(SensorPort.S2);

    public static void main(String[] args) {
        LCD.drawString("helloWorld", 0, 0);

        Sound.twoBeeps();
        Sound.beepSequenceUp();

        SampleProvider touch = sensor.getMode("Touch");
        float sample[] = new float[touch.sampleSize()];

        boolean run = true;
        while(run) {
            Delay.msDelay(10);

            touch.fetchSample(sample, 0);
            if (sample[0] == 1) {
                LCD.drawString("Touched", 0, 1);
            } else {
                LCD.drawString("Released", 0, 1);
            }

            if (Button.ENTER.isDown()) {
                run = false;
            }
        }
    }
}
