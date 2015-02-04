package sight;

import java.util.List;

import org.opencv.core.Point;
import org.opencv.core.RotatedRect;
import org.opencv.core.Size;

public class DirectionDecision {
	
	private final int leftBoundary, rightBoundary;
	private final int minDimension;
	public DirectionDecision(int imageWidth, int imageHeight, int minDimension) {
		this.leftBoundary = imageWidth/3;
		this.rightBoundary = imageWidth - leftBoundary;
		this.minDimension = minDimension;
	}
	
	public enum Direction{
		LEFT("l"), RIGHT("r"), CENTRE("c");
		String code;
		private Direction(String code) {
			this.code = code;
		}
	}
	
	public Direction given(List<RotatedRect> rectangles){
		// find the block which is lowest in the screen
		return this.decision(this.lowestRectangle(rectangles));
		
	}
	
	/**
	 * Screen is split in three. Location of the target will determine which direction robot is going to go
	 * 
	 * @param reference			The target object
	 * @return					The direction to target object
	 */
	public Direction decision(RotatedRect reference){
		return 	(reference.center.x > rightBoundary) ?	Direction.RIGHT : 
				(reference.center.x < leftBoundary) ?	Direction.LEFT : 
														Direction.CENTRE; 
	}
	
	/**
	 * @param rectangles		A list of found objects bounded by rectangles
	 * @return					The rectangle whose centre is located lowest on the screen
	 */
	public RotatedRect lowestRectangle(List<RotatedRect> rectangles){
		return  rectangles.stream()
				.filter( rectangle -> (rectangle.size.width >= minDimension && rectangle.size.height >= minDimension))
				.reduce( new RotatedRect(new Point(0,0), new Size(), 0), (r1, r2) -> ( r1.center.y > r2.center.y) ? r1 : r2);
	}
}
