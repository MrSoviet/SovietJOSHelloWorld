import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.*;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;

// ================================= EV38 ================================= //
public class Main {

    public static final EV3LargeRegulatedMotor motorL = new EV3LargeRegulatedMotor(MotorPort.A);
    public static final EV3LargeRegulatedMotor motorR = new EV3LargeRegulatedMotor(MotorPort.D);
    public static final SensorModes SENSOR_TOUCH = new EV3TouchSensor(SensorPort.S1);
    public static final SensorModes SENSOR_ULTRASONIC = new EV3UltrasonicSensor(SensorPort.S2);
    public static final SensorModes SENSOR_COLOR = new EV3ColorSensor(SensorPort.S3);
    //public static final EV3GyroSensor SENSOR_GYRO = new EV3GyroSensor(SensorPort.S4);

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
        public void run()
        {
            while(!Thread.currentThread().isInterrupted()) {
                gyroSamples.fetchSample(angle, 0);

                /*
                if (angle[0] > -90) {
                    motorL.setSpeed(200);
                    motorR.setSpeed(0);
                    motorL.forward();
                    motorR.forward();
                } else {
                    motorL.setSpeed(0);
                    motorR.setSpeed(200);
                    motorL.forward();
                    motorR.forward();

                    while (angle[0] < 90) {
                        gyroSamples.fetchSample(angle, 0);
                    }
                }*/
            }
        }
    }


    class MotorClass implements Runnable {
        public boolean turnRightUntilBlack(){
            while (angle[0] > -90){
                motorL.setSpeed(100);
                motorR.setSpeed(100);
                motorL.forward();
                motorR.backward();

                if(COLOR_CLASS.getColor().equals(ColorClass.COLOR.BLACK) && angle[0] < -45) {
                    /*while (angle[0] > -90) {
                        if(angle[0] <= -89.5) {
                            motorL.stop(true);
                            motorR.stop(true);
                        }
                    }*/

                    motorL.stop(true);
                    motorR.stop(true);
                    return true;
                }
            }

            motorL.stop(true);
            motorR.stop(true);

            while (angle[0] <= -0.5) {
                motorL.setSpeed(100);
                motorR.setSpeed(100);
                motorL.backward();
                motorR.forward();
            }

            motorL.stop(true);
            motorR.stop(true);

            return false;
        }

        public boolean turnLeftUntilBlack() {
            while (angle[0] < 90) {
                motorL.setSpeed(100);
                motorR.setSpeed(100);
                motorL.backward();
                motorR.forward();

                if(COLOR_CLASS.getColor().equals(ColorClass.COLOR.BLACK) && angle[0] > 45) {
                    /*while (angle[0] < 90) {
                        if(angle[0] >= 89.5) {
                            motorL.stop(true);
                            motorR.stop(true);
                        }
                    }*/

                    motorL.stop(true);
                    motorR.stop(true);

                    return true;
                }
            }

            motorL.stop(true);
            motorR.stop(true);

            while (angle[0] >= 0.5) {
                motorL.setSpeed(100);
                motorR.setSpeed(100);
                motorL.forward();
                motorR.backward();
            }

            motorL.stop(true);
            motorR.stop(true);

            return false;
        }

        public boolean moveForwardUntilBlack() {
            long end1 = System.currentTimeMillis() + 1000;

            while (System.currentTimeMillis() < end1) {
                motorL.setSpeed(100);
                motorR.setSpeed(100);
                motorL.forward();
                motorR.forward();

                if(COLOR_CLASS.getColor().equals(ColorClass.COLOR.BLACK)) {
                    motorL.stop(true);
                    motorR.stop(true);
                    return true;
                }
            }

            motorL.stop(true);
            motorR.stop(true);

            long end2 = System.currentTimeMillis() + 1000;
            while (System.currentTimeMillis() < end2) {
                motorL.setSpeed(100);
                motorR.setSpeed(100);
                motorL.backward();
                motorR.backward();
            }

            motorL.stop(true);
            motorR.stop(true);

            return false;
        }

        public void turnAroundUntilBlack() {
            while(angle[0] < 180) {
                motorL.setSpeed(100);
                motorR.setSpeed(100);
                motorL.backward();
                motorR.forward();

                if(COLOR_CLASS.getColor().equals(ColorClass.COLOR.BLACK) && angle[0] > 135) {
                    while (angle[0] < 180) {
                        if(angle[0] >= 179.5) {
                            motorL.stop(true);
                            motorR.stop(true);
                        }
                    }

                    return;
                }
            }
        }

        public void run() {
            while(!Thread.currentThread().isInterrupted()) {
                gyroSamples.fetchSample(angle, 0);
                    if (COLOR_CLASS.getColor().equals(ColorClass.COLOR.BLACK)){
                        motorL.setSpeed(120);
                        motorR.setSpeed(200);
                        //forward for 1 seconds
                        motorL.forward();
                        motorR.forward();
                    }else if (COLOR_CLASS.getColor().equals(ColorClass.COLOR.RED)) {
                        gyro.reset();
                        turnAroundUntilBlack();
                    }else if (COLOR_CLASS.getColor().equals(ColorClass.COLOR.BLUE)){
                        //insert logic
                    }else if (COLOR_CLASS.getColor().equals(ColorClass.COLOR.YELLOW)){
                        gyro.reset();

                        motorL.setSpeed(200);
                        motorR.setSpeed(200);
                        motorL.forward();
                        motorR.forward();
                        Delay.msDelay(900);

                        if (!turnRightUntilBlack()) {
                            if(!moveForwardUntilBlack()) {
                                if(!turnLeftUntilBlack()) {
                                    //oh you fucked up mate
                                }
                            }
                        }
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

    class UltrasonicClass implements Runnable {
        private SampleProvider distance = SENSOR_ULTRASONIC.getMode("Distance");
        private float[] sampleDistance = new float[distance.sampleSize()];

        public void run() {
            while(!Thread.currentThread().isInterrupted()) {
                distance.fetchSample(sampleDistance, 0);

                if (sampleDistance[0] < 0.3) {
                    LCD.clear();
                    LCD.drawString("Hinderniss", 0, 0);
                } else {
                    LCD.clear();
                    LCD.drawString("kein Hinderniss", 0, 0);
                }

                Delay.msDelay(50);
            }
        }
    }

    public Main() throws InterruptedException {

        gyroSamples = gyro.getAngleMode();
        gyro.reset();

        Thread t1 = new Thread( new MotorClass() );
        Thread t3 = new Thread( new EscapeButton() );
        Thread t4 = new Thread( new GyroClass());
        final Thread t2 = new Thread(COLOR_CLASS);
        Thread t5 = new Thread( new UltrasonicClass() );

        t2.start();
        t3.start();
        t4.start();
        t1.start();
        //t5.start();

        t3.join();
        if(t2.isAlive()) t2.interrupt();
        if(t4.isAlive()) t4.interrupt();
        if(t1.isAlive()) t1.interrupt();
        //if(t5.isAlive()) t5.interrupt();
    }

    public static void main(String[] args) throws InterruptedException {
        Main m = new Main();
    }
}
