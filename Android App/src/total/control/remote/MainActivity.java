package total.control.remote;

import BluetoothUtils.BluetoothThread;
import BluetoothUtils.TCRC_Protocol;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
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

		loadButtons();
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

	private void loadButtons() {

		if (mGlobal.Remote == null) {
			return;
		}

		OnClickListener handleButtonClick = new handleClick();
		OnClickListener numberClick = new numberListener();

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
		final Button source = (Button) findViewById(R.id.tv_source);
		final ImageButton play = (ImageButton) findViewById(R.id.tv_play);
		final ImageButton stop = (ImageButton) findViewById(R.id.tv_stop);
		final ImageButton ff = (ImageButton) findViewById(R.id.tv_ff);
		final ImageButton rr = (ImageButton) findViewById(R.id.tv_rr);
		final ImageButton prev = (ImageButton) findViewById(R.id.tv_prev);
		final ImageButton next = (ImageButton) findViewById(R.id.tv_next);
		final Button sendButton = (Button) findViewById(R.id.send);
		final EditText textField = (EditText) findViewById(R.id.number_field);

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
		source.setOnClickListener(handleButtonClick);
		play.setOnClickListener(handleButtonClick);
		stop.setOnClickListener(handleButtonClick);
		rr.setOnClickListener(handleButtonClick);
		ff.setOnClickListener(handleButtonClick);
		prev.setOnClickListener(handleButtonClick);
		next.setOnClickListener(handleButtonClick);
		sendButton.setOnClickListener(numberClick);

		if (mGlobal.Remote.equals("Samsung")) {
			power.setVisibility(View.VISIBLE);
			vol_up.setVisibility(View.VISIBLE);
			vol_down.setVisibility(View.VISIBLE);
			ch_up.setVisibility(View.VISIBLE);
			ch_down.setVisibility(View.VISIBLE);
			arrow_up.setVisibility(View.VISIBLE);
			arrow_down.setVisibility(View.VISIBLE);
			arrow_left.setVisibility(View.VISIBLE);
			arrow_right.setVisibility(View.VISIBLE);
			enter.setVisibility(View.VISIBLE);
			menu.setVisibility(View.VISIBLE);
			mute.setVisibility(View.VISIBLE);
			sleep.setVisibility(View.VISIBLE);
			return_.setVisibility(View.VISIBLE);
			exit.setVisibility(View.VISIBLE);
			source.setVisibility(View.VISIBLE);
			play.setVisibility(View.INVISIBLE);
			stop.setVisibility(View.INVISIBLE);
			rr.setVisibility(View.INVISIBLE);
			ff.setVisibility(View.INVISIBLE);
			prev.setVisibility(View.INVISIBLE);
			next.setVisibility(View.INVISIBLE);
			sendButton.setVisibility(View.VISIBLE);
			textField.setVisibility(View.VISIBLE);
		} else if (mGlobal.Remote.equals("Apple")) {
			power.setVisibility(View.INVISIBLE);
			vol_up.setVisibility(View.INVISIBLE);
			vol_down.setVisibility(View.INVISIBLE);
			ch_up.setVisibility(View.INVISIBLE);
			ch_down.setVisibility(View.INVISIBLE);
			arrow_up.setVisibility(View.VISIBLE);
			arrow_down.setVisibility(View.VISIBLE);
			arrow_left.setVisibility(View.VISIBLE);
			arrow_right.setVisibility(View.VISIBLE);
			enter.setVisibility(View.VISIBLE);
			menu.setVisibility(View.VISIBLE);
			mute.setVisibility(View.INVISIBLE);
			sleep.setVisibility(View.INVISIBLE);
			return_.setVisibility(View.INVISIBLE);
			exit.setVisibility(View.INVISIBLE);
			source.setVisibility(View.INVISIBLE);
			play.setVisibility(View.VISIBLE);
			stop.setVisibility(View.VISIBLE);
			rr.setVisibility(View.VISIBLE);
			ff.setVisibility(View.VISIBLE);
			prev.setVisibility(View.INVISIBLE);
			next.setVisibility(View.INVISIBLE);
			sendButton.setVisibility(View.INVISIBLE);
			textField.setVisibility(View.INVISIBLE);
		} else if (mGlobal.Remote.equals("Sony")) {
			power.setVisibility(View.VISIBLE);
			vol_up.setVisibility(View.INVISIBLE);
			vol_down.setVisibility(View.INVISIBLE);
			ch_up.setVisibility(View.INVISIBLE);
			ch_down.setVisibility(View.INVISIBLE);
			arrow_up.setVisibility(View.VISIBLE);
			arrow_down.setVisibility(View.VISIBLE);
			arrow_left.setVisibility(View.VISIBLE);
			arrow_right.setVisibility(View.VISIBLE);
			enter.setVisibility(View.VISIBLE);
			menu.setVisibility(View.VISIBLE);
			mute.setVisibility(View.INVISIBLE);
			sleep.setVisibility(View.INVISIBLE);
			return_.setVisibility(View.VISIBLE);
			exit.setVisibility(View.INVISIBLE);
			source.setVisibility(View.INVISIBLE);
			play.setVisibility(View.VISIBLE);
			stop.setVisibility(View.VISIBLE);
			rr.setVisibility(View.VISIBLE);
			ff.setVisibility(View.VISIBLE);
			prev.setVisibility(View.VISIBLE);
			next.setVisibility(View.VISIBLE);
			sendButton.setVisibility(View.INVISIBLE);
			textField.setVisibility(View.INVISIBLE);
		} else {
			power.setVisibility(View.VISIBLE);
			vol_up.setVisibility(View.VISIBLE);
			vol_down.setVisibility(View.VISIBLE);
			ch_up.setVisibility(View.VISIBLE);
			ch_down.setVisibility(View.VISIBLE);
			arrow_up.setVisibility(View.VISIBLE);
			arrow_down.setVisibility(View.VISIBLE);
			arrow_left.setVisibility(View.VISIBLE);
			arrow_right.setVisibility(View.VISIBLE);
			enter.setVisibility(View.VISIBLE);
			menu.setVisibility(View.VISIBLE);
			mute.setVisibility(View.VISIBLE);
			sleep.setVisibility(View.VISIBLE);
			return_.setVisibility(View.VISIBLE);
			exit.setVisibility(View.VISIBLE);
			source.setVisibility(View.VISIBLE);
			play.setVisibility(View.VISIBLE);
			stop.setVisibility(View.VISIBLE);
			rr.setVisibility(View.VISIBLE);
			ff.setVisibility(View.VISIBLE);
			prev.setVisibility(View.VISIBLE);
			next.setVisibility(View.VISIBLE);
			sendButton.setVisibility(View.VISIBLE);
			textField.setVisibility(View.VISIBLE);
		}

	}

	private class handleClick implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			vibe.vibrate(15);
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
			case R.id.tv_source:
				val = "key_source";
				break;
			case R.id.tv_play:
				val = "key_play";
				break;
			case R.id.tv_stop:
				val = "key_stop";
				break;
			case R.id.tv_rr:
				val = "key_rew";
				break;
			case R.id.tv_ff:
				val = "key_fwd";
				break;
			case R.id.tv_prev:
				val = "key_prev";
				break;
			case R.id.tv_next:
				val = "key_next";
				break;
			default:
				return;
			}
			if (mBluetoothThread
					.sendLine(TCRC_Protocol.SEND_COMMAND.toString())) {
				if (!mBluetoothThread.sendLine(val)) {
					toaster("Error sending" + val);
				}
			} else {
				toaster("Error. Try recconecting");
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
					} else {
						toaster("Error. Try recconecting");
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
