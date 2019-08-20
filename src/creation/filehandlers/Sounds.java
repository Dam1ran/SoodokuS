package creation.filehandlers;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public class Sounds {

    private static Clip deleteSound;
    private static Clip wrongNumberSound;
    private static Clip earnedSound;
    private static Clip helperSound;
    private static Clip wheelRotateSound;
    private static Clip notify;
    private static Clip startSound;
    private static Clip clearBoardSound;
    private static Clip wonGameSound;
    private static Clip lockSound;

    public  static AudioInputStream audioIn;

    public static void init() throws Exception{

        //================================================
        URL url = Sounds.class.getClassLoader().getResource("delete.wav");

        audioIn = AudioSystem.getAudioInputStream(url);

        deleteSound = AudioSystem.getClip();
        deleteSound.open(audioIn);

        FloatControl gainControl = (FloatControl) deleteSound.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(-10.0f);

        //================================================
        url = Sounds.class.getClassLoader().getResource("wheel.wav");
        audioIn = AudioSystem.getAudioInputStream(url);

        wheelRotateSound = AudioSystem.getClip();
        wheelRotateSound.open(audioIn);

        gainControl = (FloatControl) wheelRotateSound.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(-25.0f);

        //================================================
        url = Sounds.class.getClassLoader().getResource("wrong.wav");
        audioIn = AudioSystem.getAudioInputStream(url);

        wrongNumberSound = AudioSystem.getClip();
        wrongNumberSound.open(audioIn);

        gainControl = (FloatControl) wrongNumberSound.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(-5.0f);

        //================================================
        url = Sounds.class.getClassLoader().getResource("earned.wav");
        audioIn = AudioSystem.getAudioInputStream(url);

        earnedSound = AudioSystem.getClip();
        earnedSound.open(audioIn);

        gainControl = (FloatControl) earnedSound.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(-10.0f);

        //================================================
        url = Sounds.class.getClassLoader().getResource("notify.wav");
        audioIn = AudioSystem.getAudioInputStream(url);

        notify = AudioSystem.getClip();
        notify.open(audioIn);

        gainControl = (FloatControl) notify.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(-10.0f);

        //================================================
        url = Sounds.class.getClassLoader().getResource("ding.wav");
        audioIn = AudioSystem.getAudioInputStream(url);

        helperSound = AudioSystem.getClip();
        helperSound.open(audioIn);

        gainControl = (FloatControl) helperSound.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(-15.0f);

        //================================================
        url = Sounds.class.getClassLoader().getResource("start.wav");
        audioIn = AudioSystem.getAudioInputStream(url);

        startSound = AudioSystem.getClip();
        startSound.open(audioIn);

        gainControl = (FloatControl) startSound.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(-15.0f);

        //================================================
        url = Sounds.class.getClassLoader().getResource("clear.wav");
        audioIn = AudioSystem.getAudioInputStream(url);

        clearBoardSound = AudioSystem.getClip();
        clearBoardSound.open(audioIn);

        gainControl = (FloatControl) clearBoardSound.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(-20.0f);

        //================================================
        url = Sounds.class.getClassLoader().getResource("wonGame.wav");
        audioIn = AudioSystem.getAudioInputStream(url);

        wonGameSound = AudioSystem.getClip();
        wonGameSound.open(audioIn);

        gainControl = (FloatControl) wonGameSound.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(-5.0f);


        //================================================
        url = Sounds.class.getClassLoader().getResource("lock.wav");
        audioIn = AudioSystem.getAudioInputStream(url);

        lockSound = AudioSystem.getClip();
        lockSound.open(audioIn);

        gainControl = (FloatControl) lockSound.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(-15.0f);


    }


    public static void clear(){
        clearBoardSound.stop();
        clearBoardSound.setMicrosecondPosition(0);
        clearBoardSound.start();
    }

    public static void commence(){
        startSound.stop();
        startSound.setMicrosecondPosition(0);
        startSound.start();
    }

    public static void delete(){
        deleteSound.stop();
        deleteSound.setMicrosecondPosition(0);
        deleteSound.start();
    }

    public static void wrongNumber(){
        wrongNumberSound.stop();
        wrongNumberSound.setMicrosecondPosition(0);
        wrongNumberSound.start();
    }

    public static void earned(){
        earnedSound.stop();
        earnedSound.setMicrosecondPosition(0);
        earnedSound.start();
    }

    public static void notifySound(){
        notify.stop();
        notify.setMicrosecondPosition(0);
        notify.start();
    }

    public static void wheelRotate(){
        wheelRotateSound.stop();
        wheelRotateSound.setMicrosecondPosition(0);
        wheelRotateSound.start();
    }

    public static void helper(){
        helperSound.stop();
        helperSound.setMicrosecondPosition(0);
        helperSound.start();
    }

    public static void wonGame(){
        wonGameSound.stop();
        wonGameSound.setMicrosecondPosition(0);
        wonGameSound.start();
    }

    public static void lock(){
        lockSound.stop();
        lockSound.setMicrosecondPosition(0);
        lockSound.start();
    }


}
