package sounds;

import java.io.IOException;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class TronSounds {
	
//	private WaveData fireSound = null;
	private long device;
	private boolean initialized = false;
	private Clip fireSoundSource;
	private Clip saveFamilySource;
	private Clip dieFamilySource;
	private Clip robotDieSource;
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

		fireSoundSource = loadSoundClip("/fire2.wav");
		saveFamilySource = loadSoundClip("/savefamily.wav");
		dieFamilySource = loadSoundClip("/familydie.wav");
		robotDieSource = loadSoundClip("/robotdie.wav");
	}
	
	
	private Clip loadSoundClip(String resourceFile) {
		Clip clip = null;
		try {
			clip = AudioSystem.getClip();
			AudioInputStream inputStream = AudioSystem.getAudioInputStream(TronSounds.class.getResourceAsStream(resourceFile));
			clip.open(inputStream);
			clip.stop();
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedAudioFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
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
}
