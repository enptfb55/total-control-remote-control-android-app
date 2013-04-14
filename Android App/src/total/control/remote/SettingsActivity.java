package total.control.remote;

import BluetoothUtils.BluetoothThread;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer.TrackInfo;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class SettingsActivity extends Activity {
	private BluetoothThread mBluetoothThread;

	@TargetApi(11)
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		getActionBar().setDisplayHomeAsUpEnabled(true);

		TCRCService global = (TCRCService) this.getApplication();
		mBluetoothThread = global.mBluetoothThread;

		final ListView lv = (ListView) findViewById(R.id.settings_list);
		ArrayAdapter<String> ad = new ArrayAdapter<String>(
				SettingsActivity.this.getApplicationContext(),
				android.R.layout.simple_list_item_1);
		ad.add("Connect to device");
		if (mBluetoothThread.isConnected()) {
			ad.add("Set Remote");
		}

		lv.setAdapter(ad);

		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adptr, View v, int pos,
					long id) {
				// TODO Auto-generated method stub
				String item = (String) lv.getItemAtPosition(pos);

				if (item.equals("Connect to device")) {
					Intent connect_to_device_activity = new Intent(
							SettingsActivity.this.getApplicationContext(),
							ConnectToPairedActivity.class);
					startActivity(connect_to_device_activity);
				} else if (item.equals("Set Remote")) {
					Intent set_remote_activity = new Intent(
							SettingsActivity.this.getApplicationContext(),
							SetRemote.class);

					startActivity(set_remote_activity);
				}
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_settings, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
