package filters;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import sight.MatUtils;

public class HistogramEqualizationFilter implements MatFilter{

	/**
	 * Uses histogram equalization to enchance contrast of an image, but converts it into greyscale
	 * 
	 * @param src 			Takes a matrix for a colour image
	 * @return				A matrix for a grey image that has enhanced contrast for dark and light areas
	 */
	@Override
	public Mat filter(Mat src) {
		Mat out = MatUtils.createSameSizeMat(src, src.type());
		Imgproc.cvtColor(src, out, Imgproc.COLOR_BGR2GRAY);
		Imgproc.equalizeHist(out, out);
		return out;
	}
	
}
