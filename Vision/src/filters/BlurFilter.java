package filters;

import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import sight.MatUtils;


public class BlurFilter implements MatFilter{
	
	public static Size DEFAULT = new Size(3,3);
	private final Size ksize ;
	
	
	/**
	 * Create a filter for a Mat to remove noise using Gaussian blur
	 * 
	 * @param ksize			kernel size. Use BlurFilter.DEFAULT for default value
	 */
	public BlurFilter(Size ksize) {
		this.ksize = (ksize != null && ksize.height >= 0 && ksize.width >= 0)? ksize : DEFAULT;
	}
	
	/**
	 * Using the Gaussian Blur algorithm to blur the image
	 * 
	 * @param src			Any image matrix
	 * @return 				The matrix representing the blurred image
	 */
	@Override
	public Mat filter(Mat src) {
		Mat out = MatUtils.createSameSizeMat(src, src.type());
		Imgproc.GaussianBlur(src, out , this.ksize, 0);
		return out;
	}
}
