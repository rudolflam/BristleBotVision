package filters;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import sight.MatUtils;

public class BlackFilter implements MatFilter{
	
	private final double threshold ;
	
	public BlackFilter(double threshold) {
		this.threshold = threshold;
	}

	/**
	 * @param src		Filters the matrix to make any values larger than threshold white and anything else black
	 * @return 			returns a binary threshold matrix
	 */
	@Override
	public Mat filter(Mat src) {
		Mat out = MatUtils.createSameSizeMat(src);
		Imgproc.threshold(src, out, threshold, 255, Imgproc.THRESH_BINARY_INV);
		return out;
	}
}
