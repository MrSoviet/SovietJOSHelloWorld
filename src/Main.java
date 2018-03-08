import lejos.hardware.Button;
import lejos.hardware.Key;
import lejos.hardware.KeyListener;
import lejos.hardware.Sound;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.motor.UnregulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.*;
import lejos.robotics.EncoderMotor;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;

// ================================= EV38 ================================= //
public class Main {

    public static final EV3LargeRegulatedMotor motorL = new EV3LargeRegulatedMotor(MotorPort.A);
    public static final EV3LargeRegulatedMotor motorR = new EV3LargeRegulatedMotor(MotorPort.D);
    public static final SensorModes SENSOR_TOUCH = new EV3TouchSensor(SensorPort.S1);
    public static final SensorModes SENSOR_ULTRASONIC = new EV3UltrasonicSensor(SensorPort.S2);
    public static final SensorModes SENSOR_COLOR = new EV3ColorSensor(SensorPort.S3);

    public static final ColorClass COLOR_CLASS = new ColorClass(SENSOR_COLOR);

    private EV3GyroSensor gyro = new EV3GyroSensor(SensorPort.S4);
    private float[] angle = { 0.0f };
    private SampleProvider gyroSamples = null;

    class EscapeButton implements Runnable {
        public void run() {
            while(Button.ESCAPE.isUp()) {
                try{
                    Thread.sleep(10);
                } catch (InterruptedException e) {break;}
            }
        }
    }

    class GyroClass implements Runnable {
        public void run() {
            while(!Thread.currentThread().isInterrupted()) {
                gyroSamples.fetchSample(angle, 0);
                LCD.drawString("Gyro Angle: "+angle[0], 0, 5);
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {break;}
            }
        }
    }


    class MotorClass implements Runnable {
        public void untilBlackTurn(){
            while (!COLOR_CLASS.getColor().equals(ColorClass.COLOR.BLACK) ){
                motorL.setSpeed(200);
                motorR.setSpeed(200);
                motorL.forward();
                motorR.backward();
            }

        }

        public void run() {
            while(!Thread.currentThread().isInterrupted()) {
                    if (COLOR_CLASS.getColor().equals(ColorClass.COLOR.BLACK)){
                        motorL.setSpeed(120);
                        motorR.setSpeed(200);
                        //forward for 1 seconds
                        motorL.forward();
                        motorR.forward();
                    }else if (COLOR_CLASS.getColor().equals(ColorClass.COLOR.RED)) {
                        untilBlackTurn();
                    }else if (COLOR_CLASS.getColor().equals(ColorClass.COLOR.BLUE)){
                        untilBlackTurn();
                    }else if (COLOR_CLASS.getColor().equals(ColorClass.COLOR.YELLOW)){
                        motorL.setSpeed(200);
                        motorR.setSpeed(200);
                        motorL.forward();
                        motorR.forward();
                        Delay.msDelay(300);
                        untilBlackTurn();
                    }else{
                        motorL.setSpeed(200);
                        motorR.setSpeed(120);
                        //forward for 1 seconds
                        motorL.forward();
                        motorR.forward();
                }

                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {break;}
            }
        }
    }

    public Main() throws InterruptedException {

        Thread t1 = new Thread( new MotorClass() );
        Thread t3 = new Thread( new EscapeButton() );
        Thread t4 = new Thread( new GyroClass());
        final Thread t2 = new Thread(COLOR_CLASS);
        t2.start();
        t3.start();
        t4.start();
        t1.start();

        t3.join();
        if(t2.isAlive()) t2.interrupt();
        if(t4.isAlive()) t4.interrupt();
        if(t1.isAlive()) t1.interrupt();
    }

    public static void main(String[] args) throws InterruptedException {
        Main m = new Main();
    }
}
