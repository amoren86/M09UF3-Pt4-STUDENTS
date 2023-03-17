/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cat.institutmarianao.ftp.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Toni Moreno <amoren86@xtec.cat>
 */
public class ClientFtpData {
	IOException exception = null;
	InputStream in = null;
	OutputStream out = null;
	Socket socket;
	boolean closeInput = false;
	boolean closeOutput = false;

	public void init(Socket socket, OutputStream out, boolean pCloseOutput) throws IOException {
		init(socket, out);
		closeOutput = pCloseOutput;
	}

	public void init(Socket socket, OutputStream out) throws IOException {
		this.socket = socket;
		in = socket.getInputStream();
		this.out = out;
	}

	public void init(Socket socket, InputStream in, boolean pCloseInput) throws IOException {
		init(socket, in);
		closeInput = pCloseInput;
	}

	public void init(Socket socket, InputStream in) throws IOException {
		this.socket = socket;
		this.in = in;
		out = socket.getOutputStream();
	}

	public boolean hasError() {
		return exception != null;
	}

	public IOException getError() {
		return exception;
	}

	public void close() {
		try {
			if (closeInput && in != null) {
				in.close();
			}
			if (closeOutput && out != null) {
				out.close();
			}
			if (socket != null && !socket.isClosed()) {
				if (!socket.isInputShutdown()) {
					socket.shutdownInput();
				}
				if (!socket.isOutputShutdown()) {
					socket.shutdownOutput();
				}
				socket.close();
			}
		} catch (IOException ex) {
			Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
			exception = ex;
		}
	}

	public void transfer() {
		try {
			int len;
			byte[] buffer = new byte[1024];
			while ((len = in.read(buffer)) != -1) {
				out.write(buffer, 0, len);
			}
		} catch (IOException ex) {
			Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
			exception = ex;
		} finally {
			close();
		}
	}
}
