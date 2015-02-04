package sight;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import gnu.io.CommPortIdentifier;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.UnsupportedCommOperationException;

public class CerealPortLine {
	
	private final int timeout;
	private final CommPortIdentifier id;
	
	private SerialPort line;
	public CerealPortLine(int timeoutMillisecond, String... portIDs) {
		this.id = CerealPortUtils.getAvailablePortID(portIDs);
		this.timeout = timeoutMillisecond;
	}
	
	public boolean setup(int dataRate){
		if( this.id != null ){
			try {
				this.line = (SerialPort) id.open(CerealPortLine.class.getName(), timeout);
				this.line.setSerialPortParams(dataRate,
						SerialPort.DATABITS_8,
						SerialPort.STOPBITS_1,
						SerialPort.PARITY_NONE);
				return true;
			} catch (UnsupportedCommOperationException e) {
				e.printStackTrace();
			} catch (PortInUseException e) {
				e.printStackTrace();
			}
		}
		RoboViewPort.logger.severe("The communication port id was not available");
		return false;
	}
	
	public OutputStream outputStream() throws IOException{
		return this.line.getOutputStream();
	}
	
	public InputStream inputStream() throws IOException{
		return this.line.getInputStream();
	}
	
	
	/**
	 * Example usage
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		char[] cmds = {'l','c','r','c'};
		int index = 0;
		CerealPortLine pipe = new CerealPortLine(2000, "COM1", "COM2","COM3");
		pipe.setup(19200);
		OutputStream out = null;
		try {
			out = pipe.line.getOutputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
		while(true){
			index = (index +1) % 4;
			try {
				if(out!=null) out.write(cmds[index]);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
}
