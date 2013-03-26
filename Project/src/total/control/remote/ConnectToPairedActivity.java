package total.control.remote;

import java.util.Iterator;
import java.util.Set;

import BluetoothUtils.BluetoothThread;
import android.annotation.TargetApi;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ConnectToPairedActivity extends Activity {
	private String TAG = null;
	private ListView pairedList;
	private ListView scanedList;
	private ArrayAdapter<String> pairedAdapter;
	private ArrayAdapter<String> scanedAdapter;

	private BluetoothThread mBluetoothThread;

	private static final int REQUEST_ENABLE_BT = 2;

	public BroadcastReceiver mReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub

			if (intent.getAction().equals(
					"android.bluetooth.device.action.FOUND")) {
				BluetoothDevice aDevice = (BluetoothDevice) intent
						.getParcelableExtra("android.bluetooth.device.extra.DEVICE");

				if (aDevice.getBondState() != BluetoothDevice.BOND_BONDED) {
					// make an array of strings to return to calling thread
					scanedAdapter.add(aDevice.getName() + "\n"
							+ aDevice.getAddress());

				}
				do {
					do
						return;
					while (!intent
							.getAction()
							.equals("android.bluetooth.adapter.action.DISCOVERY_FINISHED"));
				} while (scanedAdapter.getCount() != 0);

			}

		}

	};

	@TargetApi(11)
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_connect_to_paired);
		getActionBar().setDisplayHomeAsUpEnabled(true);

		TCRCService global = (TCRCService) this.getApplication();
		mBluetoothThread = global.mBluetoothThread;
		TAG = global.TAG;

		init();

		Set<BluetoothDevice> pairedSet = mBluetoothThread.getmAdapter()
				.getBondedDevices();

		if (pairedSet.size() > 0) {
			findViewById(R.id.paired_devices_title).setVisibility(0);
			getPairedDevices();
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_connect_to_paired, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			NavUtils.navigateUpFromSameTask(this);
			return true;
		case R.id.scan_button:
			doDiscovery();
			break;
		}

		return super.onOptionsItemSelected(item);
	}

	public void getPairedDevices() {

		Set<BluetoothDevice> pairedSet = mBluetoothThread.getmAdapter()
				.getBondedDevices();
		if (pairedSet.size() > 0) {

			Iterator<BluetoothDevice> pairedIterator = pairedSet.iterator();

			while (true) {

				if (!pairedIterator.hasNext()) {
					return;
				}

				BluetoothDevice pairedBldev = (BluetoothDevice) pairedIterator
						.next();
				pairedAdapter.add(pairedBldev.getName() + "\n"
						+ pairedBldev.getAddress());
			}
		}

	}

	private void init() {

		this.pairedList = (ListView) findViewById(R.id.paired_devices);
		this.scanedList = (ListView) findViewById(R.id.scaned_device);

		this.pairedAdapter = new ArrayAdapter<String>(this,
				R.layout.device_name);
		this.scanedAdapter = new ArrayAdapter<String>(this,
				R.layout.device_name);

		pairedList.setAdapter(pairedAdapter);
		this.pairedList.setOnItemClickListener(this.mDeviceClickListener);

		scanedList.setAdapter(scanedAdapter);
		this.scanedList.setOnItemClickListener(this.mDeviceClickListener);

		if (!mBluetoothThread.getmAdapter().isEnabled()) {
			Intent enableIntent = new Intent(
					BluetoothAdapter.ACTION_REQUEST_ENABLE);
			startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
		}

		IntentFilter localIntentFilter1 = new IntentFilter(
				"android.bluetooth.device.action.FOUND");
		registerReceiver(this.mReceiver, localIntentFilter1);
		IntentFilter localIntentFilter2 = new IntentFilter(
				"android.bluetooth.adapter.action.DISCOVERY_FINISHED");
		registerReceiver(this.mReceiver, localIntentFilter2);

	}

	private void doDiscovery() {
		this.setProgressBarVisibility(true);
		findViewById(R.id.scaned_devices_title).setVisibility(0);
		setTitle("Scanning");

		mBluetoothThread.scanForDevices();
	}

	public BluetoothThread getmBlueThread() {
		return mBluetoothThread;
	}

	public void setmBlueThread(BluetoothThread mBlueThread) {
		this.mBluetoothThread = mBlueThread;
	}

	private AdapterView.OnItemClickListener mDeviceClickListener = new AdapterView.OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			String str1;
			mBluetoothThread.getmAdapter().cancelDiscovery();

			if (arg0.equals(pairedList)) {
				str1 = pairedAdapter.getItem(arg2);
			} else if (arg0.equals(scanedList)) {
				str1 = scanedAdapter.getItem(arg2);
			} else {
				Log.v(TAG, "didn't work");
				return;
			}

			String str2 = str1.substring(str1.length() - 17);

			Toast.makeText(
					ConnectToPairedActivity.this.getApplicationContext(), str2,
					1).show();

			mBluetoothThread.setmDevice(mBluetoothThread.getmAdapter()
					.getRemoteDevice(str2));

			/*
			 * Intent pairDevice = new Intent(
			 * "android.bluetooth.device.action.PAIRING_REQUEST");
			 * 
			 * pairDevice.putExtra("android.bluetooth.device.extra.DEVICE",
			 * mBluetoothThread.getmDevice());
			 * 
			 * pairDevice.putExtra(
			 * "android.bluetooth.device.extra.PAIRING_VARIANT", 0);
			 * 
			 * startActivityForResult(pairDevice, 1);
			 */

			mBluetoothThread.ConnectToDevice();
		}

	};

	public void onActivtyResult(int req_code, int res_code, Intent intent) {
		Log.v(TAG, "Activity returned with " + res_code);

		switch (req_code) {
		case REQUEST_ENABLE_BT:
			if (res_code != Activity.RESULT_OK) {
				Toast.makeText(
						ConnectToPairedActivity.this.getApplicationContext(),
						"Bluetooth not enabled", 1).show();
			}
			break;

		default:
			break;
		}
	}
}
