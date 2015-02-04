package filters;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import sight.MatUtils;

public class GreyScaleFilter implements MatFilter{
	/**
	 * Turn a colour image into a greyscale image
	 * 
	 * @param src		A matrix for a colour image
	 * @return			A grey coloured image matrix
	 */
	public Mat filter(Mat src){
		Mat out = MatUtils.createSameSizeMat(src, src.type());
		// convert to grayscale and put into out 
		Imgproc.cvtColor(src, out, Imgproc.COLOR_RGB2GRAY);
		return out;
	}
}
