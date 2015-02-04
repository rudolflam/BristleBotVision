package sight;

import java.awt.image.BufferedImage;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.RotatedRect;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;

import filters.BlobFilter;
import filters.ShadowFilter;

public class ShadowDetector {
	
	static String testImageLocation = "./ImageCap/1412011240565.png";
	static String testImageOut = "./ImageCap/RoomOut.jpg";
	
	private String imgPath ;
	
	/**
	 * Class to test the shadow detection
	 * 
	 * @param imgpath		File path of the image
	 */
	public ShadowDetector(String imgpath) {
		this.imgPath = imgpath;
	}
	
	/**
	 * Loads native library
	 */
	public static void init(){
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}

	/**
	 * Displays the image in a CanvasFrame
	 */
	public void show(){
		
		Mat img = new ShadowFilter(new Size(3, 3), 3.0, 0, 110).filter(Highgui.imread(imgPath)); 
		BlobFilter blobs = new BlobFilter(20);
		img = blobs.filter(img);
		List<RotatedRect> rectangles = blobs.rectangles(); 
		// find the centre of each rectangle then get the box on the lowest row
		Point lowestCentre = rectangles.stream()
									.map(rectangle -> rectangle.center)
									.reduce(new Point(0, 0), (p1, p2) -> (p1.y > p2.y) ? p1 : p2);
		System.out.println(lowestCentre);
		MatUtils.showImage(img, BufferedImage.TYPE_BYTE_GRAY);
	}
	
	public static void main(String[] args) {
		ShadowDetector detector = new ShadowDetector(testImageLocation);
		ShadowDetector.init();
		detector.show();
	}
}
