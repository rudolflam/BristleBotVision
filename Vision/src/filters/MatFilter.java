package filters;

import org.opencv.core.Mat;

@FunctionalInterface
public interface MatFilter {
	/**
	 * @param src			source Mat matrix from opencv
	 * @return				a filtered Mat matrix
	 */
	public Mat filter( Mat src );
}
