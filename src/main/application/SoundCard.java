package application;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Objects;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class SoundCard {

    // CLIP HOLDERS
    public Clip clip;
    private final String[][] sounds = new String[10][];
    private final int[][] loopStarts = new int[3][];

    // VOLUME SLIDER
    private FloatControl gainControl;
    public int volumeScale = 3;
    public float volume;
    private volatile boolean isLooping = false;

    public SoundCard() {
        sounds[0] = getSounds("musicwrld");
        sounds[1] = getSounds("musicbtl");
        sounds[2] = getSounds("menu");
        sounds[3] = getSounds("pdxcry");
        sounds[4] = getSounds("pdxfaint");
        sounds[5] = getSounds("moves");
        sounds[6] = getSounds("battle");
        sounds[7] = getSounds("world");
        sounds[8] = getSounds("entity");
        sounds[9] = getSounds("musicbtl_multi");

        /* Loop start times for each music file in milliseconds
           0 = World
           1 = Single Battle
           2 = PC/Multi Battle
        */
        loopStarts[0] = new int[]{945, 1375, 3855, 17931, 2617};
        loopStarts[1] = new int[]{0, 28746, 2571, 4559, 80477, 13557, 42315, 43417, 11627, 39982, 23835};
        loopStarts[2] = new int[]{9700, 16320, 13317, 25242, 24281, 32978, 18505, 24273, 13053, 18867, 11511,
                42305, 22999, 20863, 25097, 15962, 3459, 17867, 6268
        };
    }

    public String[] getSELibrary(int index) {
        return sounds[index];
    }

    private String[] getSounds(String library) {

        if (Objects.requireNonNull(
                Driver.class.getResource("Driver.class")).toString().startsWith("jar")) {

            String jarPath;
            List<String> sounds = new ArrayList<>();

            try {
                jarPath = new File(Driver.class
                        .getProtectionDomain()
                        .getCodeSource()
                        .getLocation()
                        .toURI()).getPath();

                JarFile jarFile = new JarFile(jarPath);
                Enumeration<JarEntry> entries = jarFile.entries();

                while (entries.hasMoreElements()) {

                    JarEntry entry = entries.nextElement();

                    if (entry.getName().startsWith("sound/" + library) && !entry.isDirectory()) {
                        sounds.add("/" + entry.getName());
                    }
                }

                jarFile.close();

                return sounds.toArray(String[]::new);
            }
            catch (URISyntaxException | IOException e) {
                e.printStackTrace();
            }
        }
        else {
            File folder = new File("src/res/sound/" + library);
            List<String> sounds = new ArrayList<>();

            for (File f : Objects.requireNonNull(folder.listFiles())) {
                String path = f.getName().toLowerCase();
                sounds.add("/sound/" + library + "/" + path);
            }

            return sounds.toArray(String[]::new);
        }

        return null;
    }

    public int getSoundDuration(int category, int record) {

        int duration = 0;

        if (category >= 0 && record >= 0) {

            URL file = getClass().getResource(sounds[category][record]);

            AudioInputStream audioInputStream;
            try {
                assert file != null;
                audioInputStream = AudioSystem.getAudioInputStream(file);
                AudioFormat format = audioInputStream.getFormat();
                long frames = audioInputStream.getFrameLength();
                double length = (frames + 0.0) / format.getFrameRate();
                duration = (int) Math.ceil(60.0 * length);
                if (duration == 0) {
                    duration = 60;
                }
            }
            catch (UnsupportedAudioFileException | IOException e) {
                e.printStackTrace();
            }
        }

        return duration;
    }

    public int getFile(int category, String file) {

        if (category > -1 && file != null) {
            for (int i = 0; i < sounds[category].length; i++) {
                if (sounds[category][i].toLowerCase().contains(file.toLowerCase())) {
                    return i;
                }
            }
        }

        return 0;
    }

    public void setFile(int category, int record) {

        try {
            URL file = getClass().getResource(sounds[category][record]);
            AudioInputStream ais = AudioSystem.getAudioInputStream(file);

            clip = AudioSystem.getClip();
            clip.open(ais);

            gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            checkVolume();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getLoopStart(int category, int record) {

        // Invalid category or record
        if (category < 0 || category > 9 || record < 0) {
            return 0;
        }

        // PC / Multi Battle Music
        if (category == 9) {
            // Invalid record
            if (record >= loopStarts[2].length) {
                return 0;
            }
            else {
                return loopStarts[2][record];
            }
        }
        // Single Battle / World Music
        else {
            // Invalid record
            if (record >= loopStarts[category].length) {
                return 0;
            }
            else {
                return loopStarts[category][record];
            }
        }
    }

    public void play() {
        clip.start();
    }

    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void loopBetweenTimestamps(int startTime) {

        if (clip == null) {
            return;
        }

        // Set looping flag to true
        isLooping = true;

        // Get audio format and calculate total frames
        AudioFormat format = clip.getFormat();
        float frameRate = format.getFrameRate();
        int totalFrames = clip.getFrameLength();

        // Convert start time to frames and ensure it's within bounds
        int startFrame = Math.max(0, (int) (startTime * frameRate / 1000));

        // Check for invalid start frame
        if (startFrame >= totalFrames || startTime <= 0) {
            clip.start();
            return;
        }

        // Remove any existing line listeners
        clip.removeLineListener(event -> {
        });

        // Set up a line listener to handle the looping
        clip.addLineListener(event -> {
            if (event.getType() == LineEvent.Type.STOP) {
                synchronized (clip) {
                    // Only loop if still in looping mode and clip is open
                    if (isLooping && clip.isOpen()) {
                        clip.setFramePosition(startFrame);
                        clip.start();
                    }
                }
            }
        });

        // Start playing from the beginning
        clip.setFramePosition(0);
        clip.start();
    }

    public void stop() {
        isLooping = false;

        if (clip != null) {
            clip.stop();
        }
    }

    public void checkVolume() {

        switch (volumeScale) {
            case 0:
                volume = -80f;
                break;
            case 1:
                volume = -20f;
                break;
            case 2:
                volume = -12f;
                break;
            case 3:
                volume = -5f;
                break;
            case 4:
                volume = 1f;
                break;
            case 5:
                volume = 6f;
                break;
        }

        setGain(volume);
    }

    private void setGain(float gain) {
        gainControl.setValue(gain);
    }

    private float getGain() {
        return gainControl.getValue();
    }
}