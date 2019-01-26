package itsu.java.musicplayerfx;

import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;
import itsu.java.musicplayerfx.http.iTunesAPI;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
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
import java.util.HashMap;
import java.util.Map;

public class SongPlayerController {

    private static MediaPlayer player;
    private static Timeline timer;
    private static Image albumArtWork;
    private static Mp3File file;

    private static boolean isPlaying = false;
    private static boolean isPausing = false;
    private static boolean toPlay = false;

    public static void init() {
        timer = new Timeline(new KeyFrame(Duration.millis(1000), e -> update()));
        timer.setCycleCount(Timeline.INDEFINITE);
    }

    public static void setPlayMusic(File musicFile) {
        player = new MediaPlayer(new Media(musicFile.toURI().toString()));
        player.setVolume(10);

        try {
            file = new Mp3File(musicFile);
        } catch (UnsupportedTagException | InvalidDataException | IOException e) {
            e.printStackTrace();
        }

        player.setOnStopped(() -> isPlaying = false);

        player.setOnPaused(() -> {
            isPausing = true;
            timer.stop();
            GUIManager.getDrawerContent().toggleStartStop(false);
        });

        player.setOnPlaying(() -> {
            isPlaying = true;
            isPausing = false;
            timer.play();
            GUIManager.getDrawerContent().toggleStartStop(true);
        });

        player.setOnReady(() -> {
            Thread thread = new Thread(() -> {
                albumArtWork = getAlbumImage();
                Platform.runLater(() -> {
                    GUIManager.ready();
                });
            });
            thread.start();
            if (toPlay) playMusic();
        });

    }

    public static void back() {

    }

    public static void go() {

    }

    public static void stop() {

    }

    public static void play() {
        if (toPlay) player.play();
        toPlay = true;
    }

    public static void pause() {
        player.pause();
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
        player.play();
    }

    public static void toggleStartPause() {
        if (isPausing) {
            play();
            GUIManager.getDrawerContent().toggleStartStop(true);
        } else {
            pause();
            GUIManager.getDrawerContent().toggleStartStop(false);
        }
    }

    private static void update() {
        GUIManager.update();
    }

    public static Image getAlbumArtWork() {
        return albumArtWork;
    }

    public static MediaPlayer getPlayer() {
        return player;
    }

    public static void setAudioSpectrumListener(AudioSpectrumListener listener) {
        player.setAudioSpectrumListener(listener);
    }

    public static Map<String, Object> getSongData() {
        Map<String, Object> data = new HashMap<>();

        if (file != null) {
            if (file.hasId3v2Tag()) {
                ID3v2 id3v2 = file.getId3v2Tag();
                data.put("id3v2", true);
                data.put("title", id3v2.getTitle() != null ? id3v2.getTitle() : new File(file.getFilename()).getName());
                data.put("artist", id3v2.getArtist() != null ? id3v2.getArtist() : "Unknown");
                data.put("album", id3v2.getAlbum() != null ? id3v2.getAlbum() : "Unknown");
                data.put("year", id3v2.getYear() != null ? id3v2.getYear() : "Unknown");
                data.put("track", id3v2.getTrack() != null ? id3v2.getTrack() : "Unknown");
                data.put("rating", id3v2.getWmpRating());
                data.put("genre", id3v2.getGenre());
                data.put("path", file.getFilename());
                data.put("artwork", id3v2.getAlbumImage());
                return data;
            }
        }

        ObservableMap<String, Object> map = SongPlayerController.getPlayer().getMedia().getMetadata();
        data.put("id3v2", false);
        data.put("title", map.get("title") != null ? String.valueOf(map.get("title")) : new File(player.getMedia().getSource()).getName());
        data.put("artist", map.get("artist") != null ? map.get("artist") : "Unknown");
        data.put("album", map.get("album") != null ? map.get("album") : "Unknown");
        return data;
    }

    public static boolean isPlaying() {
        return isPlaying;
    }

    private static Image getAlbumImage() {
        Image image = null;
        Map<String, Object> data = getSongData();

        if (data.get("artwork") != null) {
            image = new Image(new ByteArrayInputStream((byte[]) data.get("artwork")));

        } else {
            if (!data.get("album").equals("Unknown")) {
                String url = iTunesAPI.getArtwork(String.valueOf(data.get("album")), String.valueOf(data.get("artist")));
                if (url != null) image = new Image(url);
            }
        }

        if (image == null) image = new Image(SongPlayerController.class.getClassLoader().getResource("image/NoImage.png").toExternalForm());

        return image;
    }
}
