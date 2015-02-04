package sight;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.bytedeco.javacpp.opencv_core.IplImage;

public class CaptureFrame implements Runnable {

	public final static String imageFolder = "." + File.separator + "ImageCap" + File.separator;

	private final BufferedImage buffer;
	private final String name;
	
	/**
	 * @param frame		IplImage image object from javacp
	 * @param name		The name of the output file
	 */
	public CaptureFrame(IplImage frame, String name) {
		this.buffer = frame.getBufferedImage();
		this.name = name;
	}
	
	@Override
	public void run() {
		try {
			ImageIO.write(this.buffer, "png", new File(imageFolder + name + ".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
