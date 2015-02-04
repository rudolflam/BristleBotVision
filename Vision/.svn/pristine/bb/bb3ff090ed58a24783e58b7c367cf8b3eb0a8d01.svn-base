package sight;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.BiConsumer;

import org.bytedeco.javacpp.opencv_core.IplImage;
import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.FrameGrabber.Exception;
import org.bytedeco.javacv.OpenCVFrameGrabber;
import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;

public class CamViewPort {
	
	final String grabLocation ;
	
	/**
	 * @param ip		IP of the android phone with IP webcam installed
	 */
	public CamViewPort(String ip) {
		this.grabLocation = "http://"+ip+"/video?dummy=param.mjpg";
	}
	
	/**
	 * Opens a javacv CanvasFrame for viewing through the remote camera
	 * 
	 * @param timeInterval			Time in millisecond between each capture
	 * @param imageProcessing		Biconsumer taking the image name and the Mat matrix object from opencv
	 */
	public void captureAndProcess(long timeInterval, final BiConsumer<String,Mat> imageProcessing){
		OpenCVFrameGrabber grabber = new OpenCVFrameGrabber(grabLocation);
		grabber.setFormat("mjpeg");
		
		// Setup consumer pool
		ExecutorService pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
		
		try {
			grabber.start();
			IplImage frame = grabber.grab();
			
			CanvasFrame canvas = new CanvasFrame("Grabbed image");
			canvas.setCanvasSize(frame.width(), frame.height());
			
			long lastTime = System.currentTimeMillis();
			final long delta = timeInterval;
			// only capture images when screen is available
			while( canvas.isVisible() && (frame = grabber.grab()) != null) {
				canvas.showImage(frame);
				long currentTime = System.currentTimeMillis();
				if( currentTime >= lastTime + delta){
					String filename = "" + currentTime;
					new CaptureFrame(frame, filename).run();
					final BiConsumer<String, Mat> consumer = imageProcessing;
					pool.submit( () -> {
						consumer.accept(filename, Highgui.imread(CaptureFrame.imageFolder + filename));
					});
					lastTime = currentTime;
				}
			}
			
			grabber.stop();
			canvas.dispose();
			System.exit(0);
		} catch (Exception e) {
			System.err.println("The image grabber has failed to grab");
			e.printStackTrace();
		}
	}
	
	public void view(){
		OpenCVFrameGrabber grabber = new OpenCVFrameGrabber(grabLocation);
		grabber.setFormat("mjpeg");
		try {
			grabber.start();
			IplImage frame = grabber.grab();
			
			CanvasFrame canvas = new CanvasFrame("Grabbed image");
			canvas.setCanvasSize(frame.width(), frame.height());
			
			while( canvas.isVisible() && (frame = grabber.grab()) != null) {
				canvas.showImage(frame);
			}
			
			grabber.stop();
			canvas.dispose();
			System.exit(0);
		} catch (Exception e) {
			System.err.println("The image grabber has failed to grab");
			e.printStackTrace();
		}
	}
	
	final static String ip = "142.157.110.31:8080";
	
	public static void main(String[] args) {
		CamViewPort viewport = new CamViewPort(ip);
		viewport.captureAndProcess(2000, (name, image) -> {});
//		viewport.view();
	}
}
