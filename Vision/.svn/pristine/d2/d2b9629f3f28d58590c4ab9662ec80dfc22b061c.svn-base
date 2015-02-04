package filters;

import org.opencv.core.Mat;
import org.opencv.core.Size;

public class ShadowFilter implements MatFilter {

	private final Size ksize;
	private final double alpha, beta, threshold;
	
	
	/**
	 * @param ksize				Kernel size of blur (noise reduction)
	 * @param alpha				Alpha for brightness filter
	 * @param beta				Beta for saturation filter
	 * @param threshold			Threshold for darkness of shadows
	 */
	public ShadowFilter(Size ksize, double alpha, double beta, double threshold) {
		this.ksize = ksize;
		this.alpha = alpha;
		this.beta = beta;
		this.threshold = threshold;
	}



	@Override
	public Mat filter(Mat src) {
		
		return 	new BlackFilter(threshold).filter(
				new SaturationBrightnessFilter(alpha, beta).filter(
				new HistogramEqualizationFilter().filter(
				new BlurFilter(ksize).filter(src))));
	}

}
