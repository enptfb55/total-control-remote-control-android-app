package BluetoothUtils;

import java.util.Iterator;
import java.util.Set;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.ArrayAdapter;

public class BluetoothManager {
	private final BluetoothAdapter mAdapter = BluetoothAdapter
			.getDefaultAdapter();
	public static final int REQUEST_ENABLE_BT = 1;

	private ArrayAdapter<String> mPairedArray;
	private ArrayAdapter<String> mScanedArray;

	private ConnectThread mConnect;
	private ConnectedThread mConnected;

	public BluetoothManager() {

	}

	public BluetoothManager(ArrayAdapter<String> mPairedArray_,
			ArrayAdapter<String> mScanedArray_) {
		this.mPairedArray = mPairedArray_;
		this.mScanedArray = mScanedArray_;
	}

	public void getPairedDevices() {
		Set<BluetoothDevice> pairedSet = mAdapter.getBondedDevices();
		if (pairedSet.size() > 0) {

			Iterator<BluetoothDevice> pairedIterator = pairedSet.iterator();

			while (true) {

				if (!pairedIterator.hasNext()) {
					return;
				}

				BluetoothDevice pairedBldev = (BluetoothDevice) pairedIterator
						.next();

				this.mPairedArray.add(pairedBldev.getName() + "\n"
						+ pairedBldev.getAddress());
			}
		}
	}

	public final BroadcastReceiver mReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context_, Intent intent_) {
			// TODO Auto-generated method stub
			if (intent_.getAction().equals(
					"android.bluetooth.device.action.FOUND")) {
				BluetoothDevice aBluetoothDevice = (BluetoothDevice) intent_
						.getParcelableExtra("android.bluetooth.device.extra.DEVICE");

				if (aBluetoothDevice.getBondState() != 12) {
					mScanedArray.add(aBluetoothDevice.getName() + "\n"
							+ aBluetoothDevice.getAddress());
				}
				do {
					do
						return;
					while (!"android.bluetooth.adapter.action.DISCOVERY_FINISHED"
							.equals(intent_.getAction()));

				} while (mScanedArray.getCount() != 0);

			}
		}

	};

	public BluetoothAdapter getmAdapter() {
		return mAdapter;
	}

	public ArrayAdapter<String> getmPairedArray() {
		return mPairedArray;
	}

	public void setmPairedArray(ArrayAdapter<String> mPairedArray) {
		this.mPairedArray = mPairedArray;
	}

	public ArrayAdapter<String> getmScanedArray() {
		return mScanedArray;
	}

	public void setmScanedArray(ArrayAdapter<String> mScanedArray) {
		this.mScanedArray = mScanedArray;
	}

}
