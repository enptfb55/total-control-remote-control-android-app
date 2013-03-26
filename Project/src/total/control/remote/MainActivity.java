package total.control.remote;

import BluetoothUtils.BluetoothThread;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class MainActivity extends Activity {

	TCRCService mGlobal;
	private BluetoothThread mBluetoothThread;
	private String TAG;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mGlobal = (TCRCService) this.getApplication();
		TAG = mGlobal.TAG;
		mBluetoothThread = mGlobal.mBluetoothThread;

		if (mBluetoothThread.getmAdapter() == null) {
			Log.v(TAG, "Bluetooth adapter is not availible");
			// finish();
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.activity_main, menu);

		return true;
	}

	public boolean onOptionsItemSelected(MenuItem menu_item) {
		switch (menu_item.getItemId()) {
		case R.id.menu_settings:
			Intent settings_activity = new Intent(this, SettingsActivity.class);
			startActivity(settings_activity);
			return true;
		default:
			return false;

		}
	}

}
