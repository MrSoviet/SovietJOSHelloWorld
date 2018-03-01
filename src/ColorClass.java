import lejos.hardware.lcd.LCD;
import lejos.hardware.sensor.SensorModes;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;

public class ColorClass extends Thread {
    private SensorModes sensorColor;
    private SampleProvider rgb;
    private float sampleRGB[];

    public ColorClass(SensorModes sensorColor) {
        this.sensorColor = sensorColor;
        rgb = this.sensorColor.getMode("RGB");
        sampleRGB = new float[rgb.sampleSize()];
    }

    @Override
    public void run() {
        while(!Thread.currentThread().isInterrupted()) {
            rgb.fetchSample(sampleRGB, 0);
            LCD.drawString(colorToString(getColor()), 0, 2);
            Delay.msDelay(50);
        }
    }

    public COLOR getColor() {
        float r = sampleRGB[0];
        float g = sampleRGB[1];
        float b = sampleRGB[2];

        if ((0.0 < r && r < 0.1) && (0.0 < g && g < 0.1) && (0.0 < b && b < 0.1)) {
            return COLOR.BLACK;
        } else if ((0.25 < r && r < 0.35) && (0.25 < g && g < 0.35) && (0.3 < b && b < 0.4)) {
            return COLOR.WHITE;
        } else if ((0.2 < r && r < 0.3) && (0.0 < g && g < 0.1) && (0.0 < b && b < 0.1)) {
            return COLOR.RED;
        } else if ((0.05 < r && r < 0.15) && (0.1 < g && g < 0.2) && (0.0 < b && b < 0.1)) {
            return COLOR.GREEN;
        } else if ((0.0 < r && r < 0.1) && (0.0 < g && g < 0.1) && (0.1 < b && b < 0.2)) {
            return COLOR.BLUE;
        } else if ((0.2 < r && r < 0.3) && (0.2 < g && g < 0.3) && (0.0 < b && b < 0.1)) {
            return COLOR.YELLOW;
        }

        return COLOR.UNKNOWN;
    }

    public String colorToString(COLOR COLOR) {
        switch(COLOR) {
            case BLACK:
                return "BLACK";
            case WHITE:
                return "WHITE";
            case RED:
                return "RED";
            case GREEN:
                return "GREEN";
            case BLUE:
                return "BLUE";
            case YELLOW:
                return "YELLOW";
            case UNKNOWN:
            default:
                return "UNKNOWN";
        }
    }

    public enum COLOR {
        UNKNOWN,
        BLACK,
        WHITE,
        RED,
        GREEN,
        BLUE,
        YELLOW
    }


    public SampleProvider getRgb() {
        return rgb;
    }
    public void setRgb(SampleProvider rgb) {
        this.rgb = rgb;
    }
    public float[] getSampleRGB() {
        return sampleRGB;
    }
    public void setSampleRGB(float[] sampleRGB) {
        this.sampleRGB = sampleRGB;
    }
}
