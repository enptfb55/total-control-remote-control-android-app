package jirsend;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.etsy.net.JUDS;
import com.etsy.net.UnixDomainSocketClient;

public class Jirsend {

	private final String lircdSocketPath = "/dev/lircd";

	private UnixDomainSocketClient client;
	private InputStream in;
	private OutputStream out;

	private String remote;

	private boolean Debug = false;

	public Jirsend() throws IOException {
		connect(lircdSocketPath);
	}

	public Jirsend(String socketPath) throws IOException {
		connect(socketPath);
	}

	private void connect(String socketPath) throws IOException {

		client = new UnixDomainSocketClient(socketPath, JUDS.SOCK_STREAM);
		in = client.getInputStream();
		out = client.getOutputStream();

	}

	public void sendCommand(String command) throws Exception {

		sendPacket("send_once " + remote + " " + command);

	}

	public void sendCommand(String remote_, String command) throws Exception {

		sendPacket("send_once " + remote_ + " " + command);

	}

	public String[] listRemotes() throws Exception {
		String[] remotes = null;

		remotes = sendPacket("list  ");

		return remotes;
	}

	private String[] sendPacket(String buffer) throws Exception {
		String[] remotes = null;

		String sent = buffer + "\n";
		out.write(sent.getBytes());

		PacketState state = PacketState.P_BEGIN;

		int n = 0;
		int data_n = 0;

		while (true) {
			String line;
			line = readLine();

			if (line == null) {
				throw new Exception("string returned is null");
			}

			switch (state) {
			case P_BEGIN:
				if (!line.equals("BEGIN")) {
					continue;
				}
				state = PacketState.P_MESSAGE;
				break;
			case P_DATA:
				if (line.equals("END")) {
					return remotes;
				} else if (line.equals("DATA")) {
					state = PacketState.P_N;
				} else {
					throw new Exception("Bad packet");
				}
				break;
			case P_DATA_N:
				remotes[n] = line;
				n++;
				if (n == data_n) {
					state = PacketState.P_END;
				}
				break;
			case P_END:
				if (line.equals("END")) {
					return remotes;
				} else {
					throw new Exception("Bad Packet");
				}
			case P_MESSAGE:
				if (!line.equals(buffer) || line.length() != buffer.length()) {
					state = PacketState.P_BEGIN;
					continue;
				}
				state = PacketState.P_STATUS;
				break;
			case P_N:
				data_n = Integer.valueOf(line);
				remotes = new String[data_n];
				if (data_n == 0) {
					throw new Exception("Bad packet");
				} else {
					state = PacketState.P_DATA_N;
				}
				break;
			case P_STATUS:

				if (line.equals("SUCCESS")) {

				} else if (line.equals("END")) {

					return (remotes);
				} else if (line.equals("ERROR")) {
					throw new Exception("command failed " + buffer);
				} else {
					throw new Exception("Bad packet");
				}

				state = PacketState.P_DATA;
				break;
			default:
				break;

			}

		}

	}

	private String readLine() throws IOException {

		String hexString = "";
		StringBuilder strRcv = new StringBuilder();
		byte[] tmp = new byte[1];

		while (in.read(tmp) > 0) {

			hexString = Integer.toString((tmp[0] & 0xff) + 0x100, 16)
					.substring(1);

			char chr = (char) Integer.parseInt(hexString, 16);

			if (chr == '\n') {
				break;
			}

			strRcv.append(chr);

		}
		printMessage("Recieved [" + strRcv + "]");

		return strRcv.toString();
	}

	public void setRemote(String remote_) {
		this.remote = remote_;
	}

	private void printMessage(String message) {
		if (Debug) {
			System.out.println(message);
		}
	}
	
	public void setDebug (boolean debug){
		Debug = debug;
	}
}
