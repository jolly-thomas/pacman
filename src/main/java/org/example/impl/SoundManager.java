package org.example.impl;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class SoundManager {
    private Clip clip;
    public void play(String path){
        try{
            if(clip!= null && clip.isOpen()){
                clip.close();
            }
            AudioInputStream audio = AudioSystem.getAudioInputStream(getClass().getResource(path));
            clip = AudioSystem.getClip();
            clip.open(audio);
            clip.start();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    // for playing chump(eating) sound on loop
    public void play(String path, boolean flag){
        try{
            if(clip!= null && clip.isOpen()){
                clip.close();
                AudioInputStream audio = AudioSystem.getAudioInputStream(getClass().getResource(path));
                clip = AudioSystem.getClip();
                clip.open(audio);
            }
            if(flag){ clip.loop(Clip.LOOP_CONTINUOUSLY);clip.start();}



        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }


}
