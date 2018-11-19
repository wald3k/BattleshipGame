/*
 * Sounds from http://www.freesound.org/
 */
package battleshipproject;

import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/**
 *
 * @author Waldemar Sobiecki
 */
public class PlaySounds {
    
    public static void missSound(){
        try{        
        String soundName = "/sounds/miss.wav"; 
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(PlaySounds.class.getClass().getResource(soundName));
        Clip clip = AudioSystem.getClip();
        clip.open(audioInputStream);
        clip.start();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public static void hitSound(){
        try{        
        String soundName = "/sounds/hit.wav"; 
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(PlaySounds.class.getClass().getResource(soundName));
        Clip clip = AudioSystem.getClip();
        clip.open(audioInputStream);
        clip.start();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public static void sunkSound(){
        try{        
        String soundName = "/sounds/destroyed.wav"; 
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(PlaySounds.class.getClass().getResource(soundName));
        Clip clip = AudioSystem.getClip();
        clip.open(audioInputStream);
        clip.start();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public static void readySound(){
        try{        
        String soundName = "/sounds/ready.wav"; 
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(PlaySounds.class.getClass().getResource(soundName));
        Clip clip = AudioSystem.getClip();
        clip.open(audioInputStream);
        clip.start();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public static void lostShipSound(){
        try{        
        String soundName = "/sounds/abandonShip.wav"; 
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(PlaySounds.class.getClass().getResource(soundName));
        Clip clip = AudioSystem.getClip();
        clip.open(audioInputStream);
        clip.start();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
     public static void deployShipSound(){        
         
        try{        
        String soundName = "/sounds/deployShip.wav";         
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(PlaySounds.class.getClass().getResource(soundName));
        Clip clip = AudioSystem.getClip();
        clip.open(audioInputStream);
        clip.start();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
     
     public static void endGameSound(){
        try{        
        String soundName = "src/sounds/endGame.wav"; 
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(PlaySounds.class.getClass().getResource(soundName));
        Clip clip = AudioSystem.getClip();
        clip.open(audioInputStream);
        clip.start();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
}
