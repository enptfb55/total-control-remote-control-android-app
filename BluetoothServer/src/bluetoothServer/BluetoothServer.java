package bluetoothServer;

import javax.bluetooth.DiscoveryAgent;
import javax.bluetooth.LocalDevice;
import javax.bluetooth.UUID;
import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;
import javax.microedition.io.StreamConnectionNotifier;

import jirsend.Jirsend;

public class BluetoothServer extends Thread {

	UUID uuid = new UUID("fa87c0d0afac11de8a390800200c9a66", false);

	String url = "btspp://localhost:" + uuid.toString() + ";name=xxxx";
	
	private boolean Debug = false;

	/** Constructor */

	public BluetoothServer() {

	}

	@Override
	public void run() {

		waitForConnection();

	}

	/** Waiting for connection from devices */

	private void waitForConnection() {

		// retrieve the local Bluetooth device object

		LocalDevice local = null;

		StreamConnectionNotifier notifier = null;

		StreamConnection connection = null;

		// setup the server to listen for connection

		try {

			local = LocalDevice.getLocalDevice();

			local.setDiscoverable(DiscoveryAgent.GIAC); // generally
														// discoverable,
														// discoveryTimeout
														// should be disabled -
														// but isn't.

			notifier = (StreamConnectionNotifier) Connector.open(url);

		} catch (Exception e) {
			System.out.println("Server exception: " + e.getMessage());

			//e.printStackTrace();

		}

		// waiting for connection

		while (true) {

			try {

				System.out.println("waiting for connection...");

				connection = notifier.acceptAndOpen();

				ConnectionThread conn = new ConnectionThread(this, connection);

				conn.run();

				System.out.println("ConncetionThread.started...");

			} catch (Exception e) {
				System.out.println("Server exception: " + e.getMessage());

				//e.printStackTrace();

			}

		}

	}
	
	public void setDebug (boolean debug){
		Debug = debug;
	}
	
	private void println (String msg){
		System.out.println (msg);
	}

	public static void main(String[] args) {

		System.out.println("Server startup now");
		new BluetoothServer().start();

	}

}
