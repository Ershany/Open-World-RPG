package gfx;

import gfx.AssetManager.ImageHashKey;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

/**
 * Asset management class used to retrieve sound and audio files. Prevents
 * accumulation of redundant assets by use of HashMaps,
 *
 *
 */

public class AssetManager {

    public static ArrayList<Clip> clips;
    private static final ArrayList<String> soundsList;

    private static AudioInputStream soundStream;

    private static final HashMap<ImageHashKey, BufferedImage> subImages = new HashMap<ImageHashKey, BufferedImage>();
    private static final HashMap<String, BufferedImage> spritesheet = new HashMap<String, BufferedImage>();

    static {
        clips = new ArrayList<Clip>();
        soundsList = new ArrayList<String>();
    }

    /**
     * Retrieves a sub image from a sheet. Only accesses images under the
     * "assets/images" directory.
     *
     *
     * @param x X coords of the top left corner of the requested sub image
     * @param y Y coords of the top left corner of the requested sub image
     * @param width Width of requested sub image
     * @param height Height of requested sub image
     * @param sheet File name of the source image from which the sub images are
     * pulled. Be sure to include folder and file type. (e.g.
     * "textures/tile.png" )
     * @return Requested sub image
     */
    public static BufferedImage getImage(int x, int y, int width, int height, String sheet) {
        ImageHashKey hKey = new AssetManager().new ImageHashKey(x, y, width, height, sheet);
        if (subImages.get(hKey) == null) {

            BufferedImage b = null;

            if (spritesheet.get(sheet) == null) {
                try {
                    spritesheet.put(sheet, loadImage(sheet));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            b = spritesheet.get(sheet).getSubimage(x, y, width, height);
            subImages.put(hKey, b);
        }
        return subImages.get(hKey);
    }

    /**
     *
     * @param sheet File name of the requested sheet. Be sure to include folder
     * and file type. (e.g. "textures/tile.png" )
     * @return Requested uncut sheet
     */
    public static BufferedImage getImage(String sheet) {

        if (spritesheet.get(sheet) == null) {
            try {
                spritesheet.put(sheet, loadImage(sheet));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return spritesheet.get(sheet);
    }

    class ImageHashKey {

        private int x, y, width, height;
        private String sheet;

        public ImageHashKey(int x, int y, int width, int height, String sheet) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.sheet = sheet;
        }

        @Override
        public boolean equals(Object obj) {

            boolean b = false;

            if (obj.getClass() == ImageHashKey.class) {
                ImageHashKey imgHK = (ImageHashKey) obj;

                if (imgHK.x == this.x && imgHK.y == this.y && imgHK.width == this.width && imgHK.height == this.height && imgHK.sheet == this.sheet) {
                    b = true;
                }

            }

            return b;
        }

        //TODO Make this a little less ugly
        @Override
        public int hashCode() {
            int hash = 9;
            hash = 99 * hash + this.x;
            hash = 99 * hash + this.y;
            hash = 99 * hash + this.width;
            hash = 99 * hash + this.height;
            hash = 99 * hash + Objects.hashCode(this.sheet);
            return hash;
        }

    }

    /**
     * Handles I/O side of image retrieval
     *
     * @param assetName File name of image
     * @return Requested image.
     * @throws IOException
     */
    private static BufferedImage loadImage(String assetName) throws IOException {

        //TODO retrieve once at init and put it in a constant
        URL url = AssetManager.class.getResource("AssetManager.class");

        if (url == null) {

        }

        String url2String = url.toString();
        String[] sliced = url2String.split("/");

        url2String = url2String.replaceAll(sliced[sliced.length - 2], "res/images");
        url2String = url2String.replaceAll(sliced[sliced.length - 1], assetName);

        BufferedImage img = ImageIO.read(new URL(url2String));
        return img;

    }

    /**
     *
     * Retrieves a sound file and stores it for later access.
     *
     * @param soundName File name of the sound requested.
     * @return integer value used to re-access and play the loaded sound.
     */
    public static int loadSound(String soundName) {
        int clipNum = -1;
        if (!soundsList.contains(soundName)) {
            Clip clip = null;
            AudioInputStream tempStream;

            try {

                //TODO Again. This all should be a constant.
                URL url = AssetManager.class.getResource("AssetManager.class");

                String url2String = url.toString();
                String[] sliced = url2String.split("/");

                url2String = url2String.replaceAll(sliced[sliced.length - 2], "res/sounds");
                url2String = url2String.replaceAll(sliced[sliced.length - 1], soundName);

                tempStream = AudioSystem.getAudioInputStream(new URL(url2String));

                clip = AudioSystem.getClip();
                clip.open(tempStream);

                FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                gainControl.setValue(-20.0f);

            } catch (Exception ex) {
            }

            clips.add(clips.size(), clip);

            clipNum = clips.size() - 1;
            soundsList.add(soundName);
        } else {
            clipNum = soundsList.indexOf(soundName);
        }

        return clipNum;
    }

    /**
     * Plays specified sound.
     *
     * @param sound Integer value acquired when asset was loaded.
     */
    public static void play(int sound) {
        ((Clip) clips.get(sound)).setFramePosition(0);
        ((Clip) clips.get(sound)).start();
    }

    /**
     * Stops specified sound.
     *
     * @param sound Integer value acquired when asset was loaded.
     */
    public static void stop(int sound) {
        ((Clip) clips.get(sound)).stop();
    }

}
