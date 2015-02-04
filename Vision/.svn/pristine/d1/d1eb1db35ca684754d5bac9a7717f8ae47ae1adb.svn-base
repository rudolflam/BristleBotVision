package sudoku;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.RotatedRect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;
import org.opencv.utils.Converters;

import sight.MatUtils;
import sight.RoboViewPort;
import filters.BlackFilter;
import filters.BlurFilter;
import filters.GreyScaleFilter;
import filters.HistogramEqualizationFilter;
import filters.SaturationBrightnessFilter;

public class SudokuFinder {
	
	public static final int virtualGridDimension = 150*9;
	
//	public static RotatedRect locateGrid(Mat src){
//		RotatedRect dummy = new RotatedRect(new Point(), new Size(0, 0), 0);
//		//find the biggest rectangular thing in the image
//		return MatUtils.rectangles(src).reduce(dummy, (r1, r2) -> (r1.size.area() > r2.size.area()) ? r1 : r2);
//	}
//	
//	public static Mat perspectiveTransform(RotatedRect r){
//		Mat destination = new Mat(new Size(virtualGridDimension, virtualGridDimension), CvType.CV_8UC3);
//		return Imgproc.getPerspectiveTransform(src, destination);
//	}
	
	public static Mat[][] findBoxes(Mat src){
		Mat[][] boxes = new Mat[9][9];
		Mat grid = findGrid(src);
		Mat buffer = MatUtils.copyOf(grid);
		// buffer will now be single channeled
		buffer = new HistogramEqualizationFilter().filter(buffer);
		
//		MatUtils.showImage((buffer), BufferedImage.TYPE_BYTE_GRAY);
		Mat edges = new Mat();
		Imgproc.Canny(buffer, edges, 10, 100, 3, true);
		List<MatOfPoint> contours = new LinkedList<MatOfPoint>();
		Mat hierarchy = new Mat();
		Imgproc.findContours(edges, contours, hierarchy, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);
		
		// accept 5% error
		double boxSizeHat = src.rows() / 9;
		double delta = src.rows() / 9 * 0.1;
		
		contours.stream()
		.map( contour -> new MatOfPoint2f(contour.toArray()))
		.map( mat -> Imgproc.minAreaRect(mat) ).forEach(x -> System.out.println(x.size));
		
		List<RotatedRect> rectangles = contours.stream()
			.map( contour -> new MatOfPoint2f(contour.toArray()))
			.map( mat -> Imgproc.minAreaRect(mat) )
			.filter( rectangle -> (rectangle.size.width > boxSizeHat - delta && rectangle.size.width < boxSizeHat + delta) &&
					(rectangle.size.height > boxSizeHat - delta && rectangle.size.height < boxSizeHat + delta) )
			.collect(Collectors.toList());
//		
		System.out.println(rectangles.size());
		
		return null;
	}
	
	public static Mat findGrid(Mat src){
		return perspectiveTransform(src, findLargeGrid(src));
	}
	
	public static Mat perspectiveTransform(Mat src, MatOfPoint2f grid){
		
		Mat out = new Mat(virtualGridDimension, virtualGridDimension, CvType.CV_32F);
		Mat temp = MatUtils.copyOf(src);
		temp.convertTo(temp, CvType.CV_32F);
		temp = temp.reshape(2);
		MatOfPoint2f format = new MatOfPoint2f(new Point(0, 0), new Point(0, virtualGridDimension), new Point(virtualGridDimension, virtualGridDimension), new Point(virtualGridDimension, 0));
		System.out.println(grid.size() + " " + format.size());
		System.out.println(grid.size().equals(new Size(1,4)));
		Mat transform = Imgproc.getPerspectiveTransform(grid, format);
		Imgproc.warpPerspective(src, out, transform, out.size());
		return out;
	}

	private static MatOfPoint2f findLargeGrid(Mat src) {
		Mat buffer = new GreyScaleFilter().filter(new BlurFilter(new Size(5,5)).filter(new SaturationBrightnessFilter(3.0, 0).filter(src)));
		Imgproc.adaptiveThreshold( buffer, buffer, 255, Imgproc.ADAPTIVE_THRESH_MEAN_C, Imgproc.THRESH_BINARY, 13, 15);
		Mat edges = new Mat();
		Imgproc.Canny(buffer, edges, 10, 100, 3, true);
		List<MatOfPoint> contours = new LinkedList<MatOfPoint>();
		Mat hierarchy = new Mat();
		Imgproc.findContours(edges, contours, hierarchy, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);
		
		List<MatOfPoint2f> mats = contours.stream()
				.map( contour -> new MatOfPoint2f(contour.toArray()))
				.map( mat -> {
					MatOfPoint2f out = new MatOfPoint2f();
					Imgproc.approxPolyDP(mat, out, 0.02 * Imgproc.arcLength(mat, true), true);
					return out;})
				.filter(mat -> mat.size().equals(new Size(1,4)))
				.collect(Collectors.toList());
		MatOfPoint2f grid = mats.stream().reduce(mats.get(0), (m1, m2) -> Imgproc.contourArea(m1) > Imgproc.contourArea(m2) ? m1 : m2);
		
		// put points of the rectangle in an array
		return grid;
	}
	private final String grabLocation = "";
	
	public static void main(String[] args) {
		RoboViewPort.init();
//		MatUtils.showImage(findGrid(Highgui.imread("./Img/su.bmp")), BufferedImage.TYPE_3BYTE_BGR);
		Mat[][] boxes = findBoxes(Highgui.imread("./Img/su.bmp"));
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
//				MatUtils.showImage(boxes[i][j], BufferedImage.TYPE_3BYTE_BGR);
			}
		}
		
//		MatUtils.showImage(new GreyScaleFilter().filter( new BlurFilter(new Size(5,5)).filter(Highgui.imread("./Img/su.bmp"))), BufferedImage.TYPE_BYTE_GRAY);
	}
	
}
