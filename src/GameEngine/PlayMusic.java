package GameEngine;

import javafx.scene.media.AudioClip;

public class PlayMusic {
    public PlayMusic(String music) {
        String musicFile = music;
        AudioClip player = new AudioClip(getClass().getClassLoader().getResource(musicFile).toExternalForm());
        player.play();
    }
}
