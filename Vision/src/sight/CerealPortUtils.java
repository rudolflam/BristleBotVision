package sight;

import gnu.io.CommPortIdentifier;

import java.util.Enumeration;

public class CerealPortUtils {

	public static CommPortIdentifier getAvailablePortID(String... portNames){
		CommPortIdentifier portId = null;
		Enumeration<CommPortIdentifier> portEnum = CommPortIdentifier.getPortIdentifiers();

		//First, Find an instance of serial port as set in PORT_NAMES.
		while (portEnum.hasMoreElements()) {
			CommPortIdentifier currPortId = (CommPortIdentifier) portEnum.nextElement();
			for (String name : portNames) {
				if(currPortId.getName().equals(name)) {
					portId = currPortId;
					RoboViewPort.logger.info("Using port "+portId.getName());
					return portId;
				}
			}

		}
		return null;
	}
}
