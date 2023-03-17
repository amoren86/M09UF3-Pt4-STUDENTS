package cat.institutmarianao.ftp.client.test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import cat.institutmarianao.ftp.client.ClientFtp;

/**
 * @author Toni Moreno <amoren86@xtec.cat>
 */
public class ClientFptTest1 {

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		ClientFtp protocol = new ClientFtp();
		try {

			protocol.init(System.out);
			protocol.connectTo("ftp.gnu.org", 21);

			protocol.authenticate("anonymous", "guest");
			System.out.println(protocol.sendCwd("/"));
			System.out.println(protocol.sendPassv());
			System.out.println(protocol.sendList(System.out, false));
			System.out.println(protocol.sendPwd());
			System.out.println(protocol.sendCwd("gnu"));
			System.out.println(protocol.sendPwd());
			System.out.println(protocol.sendPassv());
			System.out.println(protocol.sendList(System.out, false));
			System.out.println(protocol.sendCwd("Licenses"));
			System.out.println(protocol.sendPwd());
			System.out.println(protocol.sendPassv());
			System.out.println(protocol.sendList(System.out, false));
			System.out.println(protocol.sendCdup());
			System.out.println(protocol.sendPwd());
			System.out.println(protocol.sendPassv());
			System.out.println(protocol.sendRetr("ProgramIndex", new FileOutputStream("ProgramIndex"), true));

			System.out.println(protocol.sendQuit());
		} catch (IOException ex) {
			protocol.close();
			Logger.getLogger(ClientFptTest1.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}
