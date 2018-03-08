import lejos.hardware.lcd.LCD;
import lejos.hardware.sensor.SensorModes;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;

public class TouchClass extends Thread {
    private SensorModes sensorTouch;
    private SampleProvider touch;
    private float sampleTouch[];

    public TouchClass(SensorModes sensorTouch) {
        this.sensorTouch = sensorTouch;
        touch = this.sensorTouch.getMode("Touch");
        sampleTouch = new float[touch.sampleSize()];
    }

    @Override
    public void run() {
        while(!Thread.currentThread().isInterrupted()) {
            touch.fetchSample(sampleTouch, 0);
            if (sampleTouch[0] == 1) {
                LCD.drawString("Touched", 0, 1);
            } else {
                LCD.drawString("Released", 0, 1);
            }

            Delay.msDelay(50);
        }
    }
}
