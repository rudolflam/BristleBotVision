package sight;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

import javax.swing.JFrame;

import org.bytedeco.javacpp.opencv_core.IplImage;
import org.bytedeco.javacv.CanvasFrame;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.RotatedRect;
import org.opencv.imgproc.Imgproc;

public class MatUtils {
	/**
	 * Create new matrix of the same size using alternative cvtype
	 * 
	 * @param src				Matrix used as template
	 * @param cvtype			The cvtype of the new Mat matrix object
	 * @return					A new Mat matrix of the same size as the source Mat
	 */
	public static Mat createSameSizeMat(Mat src, int cvtype){
		return new Mat(src.rows(), src.cols(), cvtype);
	}
	
	/**
	 * Create new matrix of the same size
	 * 
	 * @param src				Matrix used as template
	 * @return					A new Mat matrix of the same size as the source Mat
	 */
	public static Mat createSameSizeMat(Mat src){
		return new Mat(src.rows(), src.cols(), src.type());
	}
	
	/**
	 * @param image					A java BufferedImage
	 * @param cvtype				The cvtype for the opencv Mat object
	 * @return						The opencv matrix object Mat
	 */
	public static Mat getMatfromBufferedImage(BufferedImage image, int cvtype){
		Mat m = new Mat(image.getHeight(), image.getHeight(), cvtype );
		m.put(m.rows(), m.cols(), ((DataBufferByte) image.getRaster().getDataBuffer()).getData());
		return m;
	}
	
	/**
	 * @param src					An opencv Mat object for an image
	 * @param bufferedImageType		The type of buffered image (ex BufferedImage.TYPE_BYTE_GRAY for grey images)
	 * @return						The java BufferedImage of the matrix
	 */
	public static BufferedImage getBufferedImageFromMat(Mat src, int bufferedImageType){
		
		// get bytes from src
		byte[] matrixData = new byte[src.rows() * src.cols() * src.channels()];
		src.get(0,0, matrixData);
		
		// get access to bytes in new buffered image
		BufferedImage buffered = new BufferedImage(src.cols(), src.rows(), bufferedImageType);
		byte[] outBytes = ((DataBufferByte)buffered.getRaster().getDataBuffer()).getData();
	
		System.arraycopy(matrixData, 0, outBytes, 0, matrixData.length);
		return buffered;
	}
	
	/**
	 * Shows an image in a canvas frame
	 * 
	 * @param img		The image to be displayed
	 */
	public static void showImage(BufferedImage img){
		CanvasFrame frame = new CanvasFrame("Image output");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				super.windowClosed(e);
				System.exit(0);
			}
		});
		frame.showImage( IplImage.createFrom(img) );
	}
	
	/**
	 * Shows an image in a canvas frame
	 * 
	 * @param img					The image to be displayed
	 * @param bufferedImageType		The type of BufferImage showing the underlying structure of the image. null or negative values will use default BufferedImage.TYPE_3BYTE_BGR.
	 */
	public static void showImage(Mat img, Integer bufferedImageType){
		int type = (bufferedImageType == null || bufferedImageType < 0) ? BufferedImage.TYPE_3BYTE_BGR: bufferedImageType;
		CanvasFrame frame = new CanvasFrame("Image output");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				super.windowClosed(e);
				System.exit(0);
			}
		});
		frame.showImage( IplImage.createFrom(getBufferedImageFromMat(img, type)) );
	}
	
	/**
	 * @param src				The Mat matrix to be copied
	 * @return					A copy of the matrix
	 */
	public static Mat copyOf(Mat src){
		Mat out = MatUtils.createSameSizeMat(src);
		src.copyTo(out);
		return out;
	}
	
	/**
	 * Retrieves all contours in an image using opencv
	 * 
	 * @param imageSrc			The Mat matrix of the image 
	 * @return					Stream of MatOfPoint which can be fitted using rectangles or ellipses
	 */
	public static Stream<MatOfPoint> contours(Mat imageSrc){
		Mat out = MatUtils.copyOf(imageSrc);
		List<MatOfPoint> list = new LinkedList<MatOfPoint>();
		Mat hierarchy = new Mat();
		// find all contours
		Imgproc.findContours(out, list, hierarchy, Imgproc.RETR_TREE , Imgproc.CHAIN_APPROX_SIMPLE);
		return list.stream();
	}
	
	/**
	 * @param imageSrc			The Mat matrix of the image
	 * @return					Stream of RotatedRect that are found in the image
	 */
	public static Stream<RotatedRect> rectangles(Mat imageSrc){
		return MatUtils.contours(imageSrc)
				.filter(contour -> Imgproc.isContourConvex(contour))
				.map(contour -> Imgproc.minAreaRect(new MatOfPoint2f(contour.toArray())));
	}
	
}
