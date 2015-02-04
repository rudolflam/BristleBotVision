package filters;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import sight.MatUtils;

public class ColourRGB2HSVFilter implements MatFilter{

	public static final double HUE_MIN = 0;
	public static final double SATURATION_MIN = 0;
	public static final double BRIGHTNESS_MIN = 0;
	public static final double HUE_MAX = 179;
	public static final double SATURATION_MAX = 255;
	public static final double BRIGHTNESS_MAX = 255;
	
	private final double minHue, minSaturation, minBrightness, maxHue, maxSaturation, maxBrightness;
	
	public ColourRGB2HSVFilter(double minHue, double minSaturation, double minBrightness, 
			double maxHue, double maxSaturation, double maxBrightness) {
		this.minHue = minHue;
		this.minSaturation = minSaturation;
		this.minBrightness = minBrightness;
		this.maxHue = maxHue;
		this.maxSaturation = maxSaturation;
		this.maxBrightness = maxBrightness;
	}

	/**
	 * @param src			Takes a matrix represent the RGB image
	 * @return				Returns the resultant filtered HSV image
	 */
	@Override
	public Mat filter(Mat src) {
		Mat out = MatUtils.createSameSizeMat(src);
		Imgproc.cvtColor(src, out, Imgproc.COLOR_RGB2HSV);
		Core.inRange(out, new Scalar(minHue, minSaturation, minBrightness), new Scalar(maxHue, maxSaturation, maxBrightness), out);
		return out;
	}
	
	public static void main(String[] args) {
		
	}

}
