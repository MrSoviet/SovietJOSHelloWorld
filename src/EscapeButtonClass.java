import lejos.hardware.Button;
import lejos.hardware.Key;
import lejos.hardware.KeyListener;

public class EscapeButtonClass extends Thread {
    @Override
    public void run() {
        Button.ESCAPE.addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(Key key) {
                if (key.getId() == Button.ID_ESCAPE) {
                    Main.run = false;
                }
            }

            @Override
            public void keyReleased(Key key) {

            }
        });

        while(Main.run && !Thread.currentThread().isInterrupted()) {}
    }
}
