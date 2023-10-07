package sel.test;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.comparison.ImageDiff;
import ru.yandex.qatools.ashot.comparison.ImageDiffer;
import ru.yandex.qatools.ashot.coordinates.WebDriverCoordsProvider;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

public class ScreenCaptureUtility {
	
//	public boolean areImagesEqual(String baseline, String screenshot) {
//		
//		BufferedImage imgBaseLine = null;
//		BufferedImage imgScreenShot = null;
//		try {
//			imgBaseLine = ImageIO.read( new File(System.getProperty("user.dir")+"/Images/baseline/"+ baseline + ".png"));
//			imgScreenShot= ImageIO.read( new File(System.getProperty("user.dir")+"/Images/screenshot/"+ screenshot + ".png"));
//		}catch(IOException e) {}
//		
//		ImageDiff diff = new ImageDiffer().makeDiff(imgBaseLine, imgScreenShot);
//		
//		boolean isDifferent = diff.hasDiff();
//		
//		if(isDifferent)
//		{
//			BufferedImage diffImg = diff.getMarkedImage();
//			try {
//			ImageIO.write(diffImg, "png", new File(System.getProperty("user.dir")+"/Images/diffImages/"+ baseline + ".png"));
//		}catch(IOException e) {}
//		}
//		return !isDifferent;
//	}
	
	public boolean areImagesEqual(String baseline, String screenshot) {
	    BufferedImage imgBaseLine = null;
	    BufferedImage imgScreenShot = null;
	    
	    System.out.println("Baseline Image Path: " + System.getProperty("user.dir") + "/Images/baseline/" + baseline + ".png");
	    System.out.println("Screenshot Image Path: " + System.getProperty("user.dir") + "/Images/screenshots/" + screenshot + ".png");
	    
	 
	    try {
	        imgBaseLine = ImageIO.read(new File(System.getProperty("user.dir") + "/Images/baseline/" + baseline + ".png"));
	        imgScreenShot = ImageIO.read(new File(System.getProperty("user.dir") + "/Images/screenshots/" + screenshot + ".png"));
	        System.out.println("Baseline Image Width: " + imgBaseLine.getWidth());
	        System.out.println("Screenshot Image Width: " + imgScreenShot.getWidth());

	    } catch (IOException e) {
	        e.printStackTrace();
	        System.err.println("Failed to read image files: " + e.getMessage());
	        return false;
	    }

	    if (imgBaseLine == null || imgScreenShot == null) {
	        System.err.println("One or both of the images could not be loaded.");
	        return false;
	    }

	    ImageDiff diff = new ImageDiffer().makeDiff(imgBaseLine, imgScreenShot);
	    boolean isDifferent = diff.hasDiff();

	    if (isDifferent) {
	        BufferedImage diffImg = diff.getMarkedImage();
	        try {
	            ImageIO.write(diffImg, "png", new File(System.getProperty("user.dir") + "/Images/diffImages/difference.png"));
	        } catch (IOException e) {
	            e.printStackTrace();
	            System.err.println("Failed to save the difference image: " + e.getMessage());
	        }
	    }

	    return !isDifferent;
	}



	public void takePageScreenshot(WebDriver driver, String name) {
		
		//Screenshot screen = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(100)).takeScreenshot(driver);
		
		Screenshot screen = new AShot().takeScreenshot(driver);
		
		BufferedImage bi = screen.getImage();
		
		File file = new File(System.getProperty("user.dir")+"/Images/screenshots/"+ name + ".png");
		try {
		ImageIO.write(bi, "png", file);
		}catch(IOException e) {
			e.printStackTrace();
		}
		
	}
	
public void prepareBaseline(WebDriver driver, String name) {
		
		Screenshot screen = new AShot().takeScreenshot(driver);
		BufferedImage bi = screen.getImage();
		
		File file = new File(System.getProperty("user.dir")+"/Images/baseline/"+ name + ".png");
		try {
		ImageIO.write(bi, "png", file);
		}catch(IOException e) {
			e.printStackTrace();
		}
		
	}

	
//	public void takeElementScreenshot(WebDriver driver, String name, WebElement element) {
//		
//		Screenshot screen = new AShot().coordsProvider(new WebDriverCoordsProvider()).takeScreenshot(driver, element);
//		BufferedImage bi = screen.getImage();
//		
//		File file = new File(System.getProperty("user.dir")+"/Images/screenshots/"+ name + ".png");
//		try {
//		ImageIO.write(bi, "png", file);
//		}catch(IOException e) {
//			e.printStackTrace();
//		}
//	
//	}
		
	
}
