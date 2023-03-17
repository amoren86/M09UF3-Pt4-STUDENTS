package cat.institutmarianao.ftp.client.sync;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Toni Moreno <amoren86@xtec.cat>
 */
public class DataChannelSynchronizer {
	// Indicates that the data channel is ready (if true) or not (if false)
	boolean dataChannelReady = false;

	/**
	 * Mark a data channel as ready for send/receive data
	 */
	public synchronized void setReady() {
		setDataChannelReady(true);
	}

	/**
	 * Mark a data channel as not ready
	 */
	public synchronized void setNotReady() {
		setDataChannelReady(false);
	}

	/**
	 * Waits the data channel to be ready for send/receive data
	 */
	public synchronized void waitToReady() {
		waitDataChannelReady(true);
	}

	private void waitDataChannelReady(boolean condition) {
		try {
			while (dataChannelReady != condition) {
				wait();
			}
		} catch (InterruptedException ex) {
			Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
		}
	}

	private void setDataChannelReady(boolean newStatus) {
		dataChannelReady = newStatus;
		notify();
	}
}
