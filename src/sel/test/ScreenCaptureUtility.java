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
	
	public boolean areImagesEqual(String baseline, String screenshot) {
	    // Initialize BufferedImage objects for baseline and screenshot images
	    BufferedImage imgBaseLine = null;
	    BufferedImage imgScreenShot = null;
	    
	    // Print paths to the baseline and screenshot images
	    System.out.println("Baseline Image Path: " + System.getProperty("user.dir") + "/Images/baseline/" + baseline + ".png");
	    System.out.println("Screenshot Image Path: " + System.getProperty("user.dir") + "/Images/screenshots/" + screenshot + ".png");
	    
	    try {
	        // Attempt to read the baseline and screenshot images using ImageIO
	        imgBaseLine = ImageIO.read(new File(System.getProperty("user.dir") + "/Images/baseline/" + baseline + ".png"));
	        imgScreenShot = ImageIO.read(new File(System.getProperty("user.dir") + "/Images/screenshots/" + screenshot + ".png"));
	        
	        // Print the widths of the baseline and screenshot images
	        System.out.println("Baseline Image Width: " + imgBaseLine.getWidth());
	        System.out.println("Screenshot Image Width: " + imgScreenShot.getWidth());

	    } catch (IOException e) {
	        // Handle exceptions if image files cannot be read
	        e.printStackTrace();
	        System.err.println("Failed to read image files: " + e.getMessage());
	        return false; // Return false to indicate images are not equal
	    }

	    // Check if either of the images failed to load
	    if (imgBaseLine == null || imgScreenShot == null) {
	        System.err.println("One or both of the images could not be loaded.");
	        return false; // Return false to indicate images are not equal
	    }

	    // Compare the baseline and screenshot images and create a diff
	    ImageDiff diff = new ImageDiffer().makeDiff(imgBaseLine, imgScreenShot);
	    boolean isDifferent = diff.hasDiff();

	    // If the images are different, save a marked difference image
	    if (isDifferent) {
	        BufferedImage diffImg = diff.getMarkedImage();
	        try {
	            // Save the marked difference image as a PNG file
	            ImageIO.write(diffImg, "png", new File(System.getProperty("user.dir") + "/Images/diffImages/difference.png"));
	        } catch (IOException e) {
	            // Handle exceptions if the difference image cannot be saved
	            e.printStackTrace();
	            System.err.println("Failed to save the difference image: " + e.getMessage());
	        }
	    }

	    // Return true if images are equal, otherwise return false
	    return !isDifferent;
	}

	public void takePageScreenshot(WebDriver driver, String name) {
	    // Create a screenshot object using AShot library and capture the webpage's screenshot
	    Screenshot screen = new AShot().takeScreenshot(driver);
	    
	    // Convert the screenshot to a BufferedImage
	    BufferedImage bi = screen.getImage();
	    
	    // Create a File object with the path to save the screenshot
	    File file = new File(System.getProperty("user.dir") + "/Images/screenshots/" + name + ".png");
	    
	    try {
	        // Write the BufferedImage to the specified file in PNG format
	        ImageIO.write(bi, "png", file);
	    } catch (IOException e) {
	        // Handle exceptions if there's an error while saving the screenshot
	        e.printStackTrace();
	    }
	}

	
	public void prepareBaseline(WebDriver driver, String name) {
	    // Capture a screenshot of the webpage using AShot library
	    Screenshot screen = new AShot().takeScreenshot(driver);
	    
	    // Convert the screenshot to a BufferedImage
	    BufferedImage bi = screen.getImage();
	    
	    // Create a File object with the path to save the baseline screenshot
	    File file = new File(System.getProperty("user.dir") + "/Images/baseline/" + name + ".png");
	    
	    try {
	        // Write the BufferedImage to the specified file in PNG format
	        ImageIO.write(bi, "png", file);
	    } catch (IOException e) {
	        // Handle exceptions if there's an error while saving the baseline screenshot
	        e.printStackTrace();
	    }
	}

		
	}	
	

