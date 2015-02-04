package filters;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;

import sight.MatUtils;

public class TintFilter implements MatFilter{

	private final int maxThreshold;
	
	/**
	 * Filters all value that are above a threshold
	 * 
	 * @param threshold			The minimum intensity for filter to accept value
	 */
	public TintFilter(int threshold) {
		this.maxThreshold = threshold;
	}
	
	
	@Override
	public Mat filter(Mat src) {
		Mat out = MatUtils.createSameSizeMat(src);
		Core.inRange(src, new Scalar(0, 0, 0), new Scalar(maxThreshold, maxThreshold, maxThreshold), out);
		return out;
	}
	
	
	
}
