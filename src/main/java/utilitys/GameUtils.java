package utilitys;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

/***
 * 
 * @author Carl Stika
 *
 */
public class GameUtils {
	public static BufferedImage loadImage(String imageName) {
		BufferedImage result = null;
		
		URL imageURL = GameUtils.class.getClassLoader().getResource(imageName);
		if(imageURL!=null) {
			try {
				result = ImageIO.read(imageURL.openStream());
			} catch (IOException e2) {
				System.out.println("Could not load image file " + imageURL.getFile());
			}
		}
		else {
			System.out.println("Could not find file " + imageName + " in resource");
		}
		
		return result;
	}
	
	public static List<String> loadTextFile(String fileName) {
		List<String> result = new ArrayList<>();
		
		URL fileURL = GameUtils.class.getClassLoader().getResource(fileName);
		if(fileURL!=null) {
			Path path;
			try {
				path = Paths.get(fileURL.toURI());
				result = Files.readAllLines(path);
			} 
			catch (URISyntaxException e) {
				System.out.println("File " + fileName + " error : " + e.getMessage());
			} 
			catch (IOException e) {
				System.out.println("File " + fileName + " error : " + e.getMessage());
			}
		}
		else {
			System.out.println("Could not find file " + fileName + " in resource");
		}
		
		return result;
	}
}
