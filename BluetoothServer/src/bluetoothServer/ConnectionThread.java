package bluetoothServer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.microedition.io.StreamConnection;

import jirsend.Jirsend;

public class ConnectionThread implements Runnable {

	BluetoothServer bluetoothServer;
	private StreamConnection mConnection;
	OutputStream outStream;
	InputStream inStream;

	Jirsend jirsend;
	
	private boolean status = true;

	private boolean Debug = false;

	ConnectionThread(BluetoothServer bluetoothServer,
			StreamConnection connection) {

		try {
			this.bluetoothServer = bluetoothServer;
			mConnection = connection;
			outStream = mConnection.openOutputStream();
			inStream = mConnection.openInputStream();
		} catch (IOException e) {
			// e.printStackTrace();
		}
	}

	public void run() {
		// TODO Auto-generated method stub

		System.out.println("Connected: ConnectedThread running.");
		try {
			jirsend = new Jirsend();
		} catch (Exception e) {
			System.out.println("Unable to create Jirsend");
			return;
		}
		while (status) {

			String line = readLine();
			try {
				if (line.equals(TCRC_Protocol.GET_REMOTES.toString())) {
					String[] remotes = jirsend.listRemotes();
					for (int i = 0; i < remotes.length; i++) {
						sendLine(remotes[i]);
					}
					sendLine(TCRC_Protocol.END.toString());
				} else if (line.equals(TCRC_Protocol.SET_REMOTE.toString())) {
					String remote = readLine();
					jirsend.setRemote(remote);
					println("Remote set to:" + remote);
				} else if (line.equals(TCRC_Protocol.SEND_COMMAND.toString())) {
					String command = readLine();
					jirsend.sendCommand(command);
					println("Command sent:" + command);
				}

				Thread.sleep(150);
			} catch (Exception e) {
				// TODO Auto-generated catch block

			}

		}

	}

	private void closeConnection() {
		try {
			mConnection.close();
			status = false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}
	}

	private void send(String string) {
		String line = string;
		try {
			outStream.write(line.getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			closeConnection();
		}
	}

	private void sendLine(String string) {
		String line = string + '\n';
		send(line);
	}

	private String readLine() {

		try {
			while (true) {
				byte[] tmp = new byte[1];
				String hexString = "";
				StringBuilder string = new StringBuilder();

				if (inStream.available() > 0) {
					while (true) {
						int r = inStream.read(tmp);
						hexString = Integer.toString((tmp[0] & 0xff) + 0x100,
								16).substring(1);

						char chr = (char) Integer.parseInt(hexString, 16);
						if (chr == '\n') {
							break;
						}
						string.append(chr);
						if (r == -1 || inStream.available() <= 0) {
							break;
						}

					}
					println("Recieved [" + string + "]");
					return string.toString();
				}

			}
		} catch (IOException e) {
			closeConnection();
		}
		return null;
	}

	public void setDebug(boolean debug) {
		Debug = debug;
	}

	public void println(String msg) {
		if (Debug) {
			System.out.println(msg);
		}
	}
}
