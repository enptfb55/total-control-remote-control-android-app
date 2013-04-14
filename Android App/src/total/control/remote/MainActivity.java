package total.control.remote;

import BluetoothUtils.BluetoothThread;
import BluetoothUtils.TCRC_Protocol;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.Editable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends Activity {

	TCRCService mGlobal;
	private BluetoothThread mBluetoothThread;
	private String TAG;
	Vibrator vibe = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.samsung);
		vibe = (Vibrator) MainActivity.this.getApplicationContext()
				.getSystemService(Context.VIBRATOR_SERVICE);

		mGlobal = (TCRCService) this.getApplication();
		TAG = mGlobal.TAG;
		mBluetoothThread = mGlobal.mBluetoothThread;

		if (mBluetoothThread.getmAdapter() == null) {
			Log.v(TAG, "Bluetooth adapter is not availible");
			// finish();
		} else if (!mBluetoothThread.isConnected()) {
			Intent connect_to_device_activity = new Intent(this,
					ConnectToPairedActivity.class);
			startActivity(connect_to_device_activity);
		}

		OnClickListener handleButtonClick = new handleClick();

		final Button power = (Button) findViewById(R.id.tv_power);
		final Button vol_up = (Button) findViewById(R.id.tv_vol_up);
		final Button vol_down = (Button) findViewById(R.id.tv_vol_down);
		final Button ch_up = (Button) findViewById(R.id.tv_chan_up);
		final Button ch_down = (Button) findViewById(R.id.tv_chan_down);
		final Button arrow_up = (Button) findViewById(R.id.tv_arrow_up);
		final Button arrow_down = (Button) findViewById(R.id.tv_arrow_down);
		final Button arrow_left = (Button) findViewById(R.id.tv_arrow_left);
		final Button arrow_right = (Button) findViewById(R.id.tv_arrow_right);
		final Button enter = (Button) findViewById(R.id.tv_enter);
		final Button menu = (Button) findViewById(R.id.tv_menu);
		final Button mute = (Button) findViewById(R.id.tv_mute);
		final Button sleep = (Button) findViewById(R.id.tv_sleep);
		final Button return_ = (Button) findViewById(R.id.tv_return);
		final Button exit = (Button) findViewById(R.id.tv_exit);
		final ImageButton play = (ImageButton) findViewById(R.id.tv_play);
		final Button source = (Button) findViewById(R.id.tv_source);

		power.setOnClickListener(handleButtonClick);
		vol_up.setOnClickListener(handleButtonClick);
		vol_down.setOnClickListener(handleButtonClick);
		ch_up.setOnClickListener(handleButtonClick);
		ch_down.setOnClickListener(handleButtonClick);
		arrow_up.setOnClickListener(handleButtonClick);
		arrow_down.setOnClickListener(handleButtonClick);
		arrow_left.setOnClickListener(handleButtonClick);
		arrow_right.setOnClickListener(handleButtonClick);
		enter.setOnClickListener(handleButtonClick);
		menu.setOnClickListener(handleButtonClick);
		mute.setOnClickListener(handleButtonClick);
		sleep.setOnClickListener(handleButtonClick);
		return_.setOnClickListener(handleButtonClick);
		exit.setOnClickListener(handleButtonClick);
		play.setOnClickListener(handleButtonClick);
		source.setOnClickListener(handleButtonClick);

		final Button sendButton = (Button) findViewById(R.id.send);

		sendButton.setOnClickListener(new numberListener());

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
		case R.id.connect_to_device:
			Intent connect_to_device_activity = new Intent(this,
					ConnectToPairedActivity.class);
			startActivity(connect_to_device_activity);
			return true;
		case R.id.set_remote:
			Intent set_remote_activity = new Intent(this, SetRemote.class);
			startActivity(set_remote_activity);
			return true;
		default:
			return false;

		}
	}

	private class handleClick implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			vibe.vibrate(20);
			if (!mBluetoothThread.isConnected()) {
				toaster("Not connected to TCRC device");
				return;
			} else if (mGlobal.Remote == null) {
				toaster("No remote is set");
				return;
			}

			String val = null;

			switch (v.getId()) {
			case R.id.tv_power:
				val = "key_power";
				break;
			case R.id.tv_menu:
				val = "key_menu";
				break;
			case R.id.tv_mute:
				val = "key_mute";
				break;
			case R.id.tv_exit:
				val = "key_exit";
				break;
			case R.id.tv_sleep:
				val = "key_sleep";
				break;
			case R.id.tv_arrow_down:
				val = "key_down";
				break;
			case R.id.tv_arrow_up:
				val = "key_up";
				break;
			case R.id.tv_arrow_left:
				val = "key_left";
				break;
			case R.id.tv_arrow_right:
				val = "key_right";
				break;
			case R.id.tv_enter:
				val = "key_enter";
				break;
			case R.id.tv_vol_up:
				val = "key_vol+";
				break;
			case R.id.tv_vol_down:
				val = "key_vol-";
				break;
			case R.id.tv_chan_up:
				val = "key_ch+";
				break;
			case R.id.tv_chan_down:
				val = "key_ch-";
				break;
			case R.id.tv_play:
				val = "key_play";
				break;
			case R.id.tv_source:
				val = "key_source";
				break;
			default:
				return;
			}
			if (mBluetoothThread
					.sendLine(TCRC_Protocol.SEND_COMMAND.toString())) {
				if (!mBluetoothThread.sendLine(val)) {
					toaster("Error sending" + val);
				}
			}

		}
	}

	private class numberListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub

			if (!mBluetoothThread.isConnected()) {
				toaster("Not connected to TCRC device");
				return;
			} else if (mGlobal.Remote == null) {
				toaster("No remote is set");
				return;
			}

			final EditText textField = (EditText) findViewById(R.id.number_field);
			String[] text = textField.getText().toString().split("");

			for (int i = 1; i < text.length; i++) {
				if (text != null) {
					String val = "key_" + text[i];
					if (text[i].equals("-")) {
						val = "key_dash";
					}

					if (mBluetoothThread.sendLine(TCRC_Protocol.SEND_COMMAND
							.toString())) {
						if (!mBluetoothThread.sendLine(val)) {
							toaster("Error sending" + val);
						}
					}
				}
			}

			textField.setText("");
		}

	}

	public void toaster(String str) {
		Toast.makeText(MainActivity.this.getApplicationContext(), str, 1)
				.show();
	}
}
