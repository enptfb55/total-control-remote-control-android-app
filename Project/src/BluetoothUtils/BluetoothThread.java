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
		while (true) {
			if (mDevice != null) {
				ConnectToDevice();

				if (mSocket != null) {
					Log.v(TAG, "connected");
				}

			}

		}
	}

	public void cancel() {
		try {
			mSocket.close();
			mSocket = null;
		} catch (IOException e) {
			// log error
		}
	}

	public void scanForDevices() {
		if (this.mAdapter.isDiscovering()) {
			this.mAdapter.cancelDiscovery();
		}
		this.mAdapter.startDiscovery();
	}

	public void ConnectToDevice() {
		mAdapter.cancelDiscovery();

		try {
			// mSocket = mDevice.createRfcommSocketToServiceRecord(MY_UUID);
			mSocket = mDevice
					.createRfcommSocketToServiceRecord(MY_UUID);
			mSocket.connect();
		} catch (IOException connect_e) {
			Log.v(TAG, "error trying to connect to socket");
			try {
				mSocket.close();
				mSocket = null;
			} catch (IOException close_e) {
				Log.v(TAG, "error trying to close socket");
			}
		}

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

}
