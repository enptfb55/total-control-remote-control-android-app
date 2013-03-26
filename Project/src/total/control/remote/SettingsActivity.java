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
import android.widget.Button;

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

		final Button connect_to_device_button = (Button) findViewById(R.id.connect_to_device_button);

		connect_to_device_button.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent connect_to_device_activity = new Intent(
						SettingsActivity.this.getApplicationContext(),
						ConnectToPairedActivity.class);
				startActivity(connect_to_device_activity);
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
