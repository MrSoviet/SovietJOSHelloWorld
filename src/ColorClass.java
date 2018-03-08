import lejos.hardware.lcd.LCD;
import lejos.hardware.sensor.SensorModes;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;

public class ColorClass implements Runnable {
    private SensorModes sensorColor;
    private SampleProvider rgb;
    private float sampleRGB[];

    public ColorClass(SensorModes sensorColor) {
        this.sensorColor = sensorColor;
        this.rgb = this.sensorColor.getMode("RGB");
        this.sampleRGB = new float[rgb.sampleSize()];
    }

    @Override
    public void run() {
        while(!Thread.currentThread().isInterrupted()) {
            rgb.fetchSample(sampleRGB, 0);
            LCD.drawString(colorToString(getColor()), 0, 2);
        }
    }

    public COLOR getColor() {
        float r = sampleRGB[0];
        float g = sampleRGB[1];
        float b = sampleRGB[2];

        LCD.drawString("color r: " + r, 0, 1);
        LCD.drawString("color g: " + g, 0, 2);
        LCD.drawString("color b: " + b, 0, 3);

        if(r>0.09&&r<0.13&&g>0.13&&g<0.18&&b>0.035&&b<0.06)  return COLOR.GREEN;
        else if(r>0.2&&r<0.3&&g>0.15&&g<0.25&&b<0.06) return COLOR.YELLOW;
        else if(r>0.2&&r<0.26&&g>0.035&&g<0.05&&b>0.025&&b<0.04) return COLOR.RED;
        else if(r>0.22&&r<0.32&&g>0.22&&g<0.32&&b>0.22&&b<0.32)  return COLOR.WHITE;
        else if((0.0 < r && r < 0.1) && (0.0 < g && g < 0.1) && (0.0 < b && b < 0.1)) return COLOR.BLACK;
        else if(r>0.03&&r<0.045&&g>0.045&&g<0.55&&b>0.08&&b<0.12) return COLOR.BLUE;

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
