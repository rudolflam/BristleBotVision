package filters;

import java.util.LinkedList;
import java.util.List;

import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.RotatedRect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import sight.MatUtils;

public class BlobFilter implements MatFilter {

	private final int minDimension;
	private List<RotatedRect> rectangles ;
	
	/**
	 * @param minBlobDimension		Minimum size of width or length of any rectangular blob to be found. (ie. min(width, height) > minBlobDimension)
	 */
	public BlobFilter(int minBlobDimension) {
		this.minDimension = minBlobDimension;
		this.rectangles = new LinkedList<RotatedRect>();
	}
	
	/**
	 * @return				A copy of the points describing the rectangles
	 */
	public List<RotatedRect> rectangles(){
		List<RotatedRect> copy = new LinkedList<RotatedRect>();
		copy.addAll(rectangles);
		return copy;
	}
	
	/**
	 * Find minimum rectangular bounds around values of high value
	 * Also produces a side effect, which loads all found rectangles into this object; retrieve through rectangles()
	 * 
	 * @param src			A binary matrix representing some aspect of an image
	 * @return 				The binary matrix with rectangular bounds around highlighted (high value) areas
	 */
	@Override
	public Mat filter(Mat src) {
		Mat out = MatUtils.copyOf(src);
		List<MatOfPoint> list = new LinkedList<MatOfPoint>();
		Mat hierarchy = new Mat();
		// find all contours
		Imgproc.findContours(out, list, hierarchy, Imgproc.RETR_TREE , Imgproc.CHAIN_APPROX_SIMPLE);
		
		// find for each of contour a bounding rectangle
		for (MatOfPoint matOfPoint : list) {
			// Min area of rectangle algorithm takes the 2f version of MatOfPoint
			MatOfPoint2f rectangleFloats = new MatOfPoint2f(matOfPoint.toArray());
			RotatedRect rectangle = Imgproc.minAreaRect(rectangleFloats);
			
			List<MatOfPoint> rect = new LinkedList<MatOfPoint>();
			// Collect all the rectangles in a list to be drawn
			if(rectangle.size.width > this.minDimension && rectangle.size.height > this.minDimension) {
				// put points of the rectangle in an array
				Point[] points = new Point[4];
				rectangle.points(points);
				rect.add(new MatOfPoint(points));
				this.rectangles.add(rectangle);
			}
			
			Imgproc.drawContours(out, rect, 0, new Scalar(255, 0, 0), 3);
		}
		return out;
	}

}
