package itsu.java.musicplayerfx;

import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;
import itsu.java.musicplayerfx.http.iTunesAPI;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ObservableMap;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.media.AudioSpectrumListener;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

public class SongPlayerController {

    private static MediaPlayer player;
    private static Timeline timer;
    private static Image albumArkWork;
    private static Mp3File file;

    private static boolean isPlaying = false;
    private static boolean toPlay = false;

    public static void init() {
        isPlaying = false;

        timer = new Timeline(new KeyFrame(Duration.millis(1000), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                update();
            }
        }));
    }

    public static void setPlayMusic(File musicFile) {
        player = new MediaPlayer(new Media(musicFile.toURI().toString()));
        player.setVolume(10);

        try {
            file = new Mp3File(musicFile);
            if (file.hasId3v2Tag()) {
                ID3v2 id3v2 = file.getId3v2Tag();
                byte[] album = id3v2.getAlbumImage();
                if (album != null) albumArkWork = new Image(new ByteArrayInputStream(album));
            }
        } catch (UnsupportedTagException | InvalidDataException | IOException e) {
            e.printStackTrace();
        }

        player.setAudioSpectrumListener(new AudioSpectrumListener() {
            @Override
            public void spectrumDataUpdate(double timestamp, double duration, float[] magnitudes, float[] phases) {

            }
        });

        player.setOnStopped(() -> {
            isPlaying = false;
        });

        player.setOnReady(() -> {
            GUIManager.getDrawerContent().setMaxTime(player.getTotalDuration().toSeconds());
            GUIManager.getDrawerContent().setMaxTimeLabel(secToString(player.getTotalDuration().toSeconds()));

            timer.setCycleCount((int) player.getTotalDuration().toSeconds());

            if (toPlay) {
                playMusic();
            }
        });
    }

    public static void back() {

    }

    public static void go() {

    }

    public static void stop() {

    }

    public static void play() {
        if (toPlay) playMusic();
        toPlay = true;
    }

    public static void pause() {
        isPlaying = false;
        player.pause();
        timer.stop();
        GUIManager.getDrawerContent().toggleStartStop(false);
    }

    public static boolean mute() {
        player.setMute(!player.isMute());
        return player.isMute();
    }

    public static void seek(double value) {
        player.seek(new Duration(value * 1000));
    }

    public static void volume(double volume) {
        player.setVolume(volume * 0.1);
    }

    private static void playMusic() {
        isPlaying = true;
        timer.play();
        player.play();

        String album = "";
        String artist = "";

        if (file != null) {
            if (file.hasId3v2Tag()) {
                ID3v2 id3v2 = file.getId3v2Tag();
                GUIManager.getDrawerContent().setSongName(id3v2.getTitle());
                GUIManager.getDrawerContent().setAlbumName(id3v2.getArtist() + " / " + id3v2.getAlbum());

                album = id3v2.getAlbum();
                artist = id3v2.getArtist();
            }
        } else {
            ObservableMap<String, Object> map = SongPlayerController.getPlayer().getMedia().getMetadata();
            if (map.containsKey("title")) GUIManager.getDrawerContent().setSongName(String.valueOf(map.get("title")));

            String src = "";
            if (map.containsKey("artist")) {
                src = String.valueOf(map.get("artist"));
                artist = String.valueOf(map.get("artist"));
            }

            if (!src.equals("")) {
                if (map.containsKey("album")) src = src + " / " + String.valueOf(map.get("album"));
            } else {
                if (map.containsKey("album")) src = String.valueOf(map.get("album"));
            }
            if (map.containsKey("album")) {
                album = String.valueOf(map.get("album"));
            }


            if (!src.equals("")) GUIManager.getDrawerContent().setAlbumName(src);
        }

        if (albumArkWork == null) {
            String url = iTunesAPI.getArtwork(album, artist);
            if (url != null) albumArkWork = new Image(url);
        }

        GUIManager.getDrawerContent().toggleStartStop(true);
        GUIManager.play();
    }

    public static void toggleStartPause() {
        if (isPlaying) {
            pause();
            GUIManager.getDrawerContent().toggleStartStop(false);
            isPlaying = false;

        } else {
            play();
            GUIManager.getDrawerContent().toggleStartStop(true);
            isPlaying = true;
        }
    }

    private static void update() {
        GUIManager.update();
    }

    public static String secToString(double rawSec) {
        double min = rawSec / 60;
        double sec = rawSec % 60;

        String secString = "" + (int) sec;
        if (sec < 10) secString = "0" + secString;

        return (int) min + ":" + secString;
    }

    public static void setAlbumArkWork(Image albumArkWork) {
        SongPlayerController.albumArkWork = albumArkWork;
    }

    public static Image getAlbumArkWork() {
        return albumArkWork;
    }

    public static MediaPlayer getPlayer() {
        return player;
    }

    public static void setAudipSpectrumListener(AudioSpectrumListener listener) {
        player.setAudioSpectrumListener(listener);
    }

    public static Mp3File getFile() {
        return file;
    }
}
