package BluetoothUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.UUID;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.util.Log;

@SuppressLint("NewApi")
public class BluetoothThread extends Thread implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final UUID MY_UUID = UUID
			.fromString("fa87c0d0-afac-11de-8a39-0800200c9a66");
	private final BluetoothAdapter mAdapter = BluetoothAdapter
			.getDefaultAdapter();

	private BluetoothDevice mDevice = null;
	private BluetoothSocket mSocket = null;
	private InputStream mInStream = null;
	private OutputStream mOutStream = null;
	private Context mContext;

	private String TAG = "Total Control Remote Control";

	public BluetoothThread() {
	}

	public void run() {

	}

	public void cancel() {
		try {
			mSocket.close();
			mSocket = null;
		} catch (IOException e) {
			Log.v(TAG, "error trying to close socket");
		}
	}

	public void scanForDevices() {
		if (this.mAdapter.isDiscovering()) {
			this.mAdapter.cancelDiscovery();
		}
		this.mAdapter.startDiscovery();
	}

	public boolean ConnectToDevice() {
		mAdapter.cancelDiscovery();

		try {
			// mSocket = mDevice.createRfcommSocketToServiceRecord(MY_UUID);
			mSocket = mDevice.createRfcommSocketToServiceRecord(MY_UUID);
			mSocket.connect();

			if (mSocket != null) {
				Log.v(TAG, "connected");

			} else {
				return false;
			}

			mInStream = mSocket.getInputStream();
			mOutStream = mSocket.getOutputStream();

		} catch (IOException connect_e) {
			Log.v(TAG, "error trying to connect to socket");
			cancel();
			return false;
		}

		return true;
	}

	public static UUID getMyUuid() {
		return MY_UUID;
	}

	public BluetoothAdapter getmAdapter() {
		return mAdapter;
	}

	public BluetoothDevice getmDevice() {
		return mDevice;
	}

	public void setmDevice(BluetoothDevice device_) {
		this.mDevice = device_;
	}

	public BluetoothSocket getmSocket() {
		return mSocket;
	}

	public InputStream getmInStream() {
		return mInStream;
	}

	public OutputStream getmOutStream() {
		return mOutStream;
	}

	public Context getmContext() {
		return mContext;
	}

	public void setmContext(Context mContext) {
		this.mContext = mContext;
	}

	public boolean isConnected() {
		if (mSocket != null) {
			return true;
		}

		return false;
	}

	public boolean send(String str) {

		try {
			mOutStream.write(str.getBytes());
		} catch (IOException e) {
			cancel();
			return false;
		}

		return true;
	}

	public boolean sendLine(String str) {
		String line = str + '\n';
		return send(line);
	}

	public String readLine() {

		try {
			while (true) {
				byte[] tmp = new byte[1];
				String hexString = "";
				StringBuilder string = new StringBuilder();

				if (mInStream.available() > 0) {
					while (true) {
						int r = mInStream.read(tmp);
						hexString = Integer.toString((tmp[0] & 0xff) + 0x100,
								16).substring(1);

						char chr = (char) Integer.parseInt(hexString, 16);
						if (chr == '\n') {
							break;
						}
						string.append(chr);
						if (r == -1 || mInStream.available() <= 0) {
							break;
						}

					}
					System.out.println("Recieved [" + string + "]");
					return string.toString();
				}

			}
		} catch (IOException e) {
			cancel();
		}
		return null;
	}

}
