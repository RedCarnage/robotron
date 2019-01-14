package sounds;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/***
 * 
 * @author Carl Stika
 *
 */
public class TronSounds {
	
	private long device;
	private boolean initialized = false;
	private Clip fireSoundSource;
	private Clip saveFamilySource;
	private Clip dieFamilySource;
	private Clip robotDieSource;
	private Clip playerDieSource;
    private static TronSounds instance = null; 
	
    public static TronSounds getInstance() {
    	if(instance==null) {
    		try {
				instance = new TronSounds();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	
    	return instance;
	}
	private TronSounds() throws Exception {
		initialized = true;
        
        loadSounds();
    }
	
	
	private void loadSounds() {

		fireSoundSource = loadSoundClip("fire2.wav");
		saveFamilySource = loadSoundClip("savefamily.wav");
		dieFamilySource = loadSoundClip("familydie.wav");
		robotDieSource = loadSoundClip("robotdie.wav");
		playerDieSource = loadSoundClip("playerdie.wav");
	}
	
	
	private Clip loadSoundClip(String resourceFile) {
		Clip clip = null;
		try {
			clip = AudioSystem.getClip();
			URL imageURL = TronSounds.class.getClassLoader().getResource(resourceFile);
			if(imageURL!=null) {
				try {
					AudioInputStream inputStream = AudioSystem.getAudioInputStream(new BufferedInputStream(imageURL.openStream()));
					clip.open(inputStream);
					clip.stop();
				} 
				catch (IOException e) {
					System.out.println("Could not load sound file " + imageURL.getFile() + " exception = "  + e);
				}
			}
			else {
				System.out.println("Could not find sound file " + resourceFile);
			}
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedAudioFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return clip;
	}

	public void playfire() {
		if(initialized) {
			fireSoundSource.setFramePosition(0);
			fireSoundSource.start();
		}
	}

	public void playPickupFamily() {
		if(initialized) {
			saveFamilySource.setFramePosition(0);
			saveFamilySource.start();
		}
	}

	public void playFamilyDie() {
		if(initialized) {
			dieFamilySource.setFramePosition(0);
			dieFamilySource.start();
		}
	}
	
	public void playRobotDir() {
		if(initialized) {
			robotDieSource.setFramePosition(0);
			robotDieSource.start();
		}
	}

	public void playPlayerDie() {
		if(initialized) {
			System.out.println("Play player die");
			playerDieSource.setFramePosition(0);
			playerDieSource.start();
		}
	}
}
