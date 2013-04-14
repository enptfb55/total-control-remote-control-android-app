package total.control.remote;

import BluetoothUtils.BluetoothThread;
import BluetoothUtils.TCRC_Protocol;
import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class SetRemote extends Activity {
	private BluetoothThread mBluetoothThread;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_set_remote);
		getActionBar().setDisplayHomeAsUpEnabled(true);

		final TCRCService global = (TCRCService) this.getApplication();
		mBluetoothThread = global.mBluetoothThread;

		if (mBluetoothThread.isConnected()) {
			final ListView remoteList = (ListView) findViewById(R.id.remote_list);

			ArrayAdapter<String> remoteListAdapter = new ArrayAdapter<String>(
					SetRemote.this.getApplicationContext(),
					android.R.layout.simple_list_item_1);

			mBluetoothThread.sendLine(TCRC_Protocol.GET_REMOTES.toString());

			while (true) {
				String line = mBluetoothThread.readLine();

				if (line.equals(TCRC_Protocol.END.toString())) {
					break;
				}

				remoteListAdapter.add(line);

			}

			remoteList.setAdapter(remoteListAdapter);

			remoteList.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> adapter, View v,
						int pos, long id) {
					// TODO Auto-generated method stub
					String remote = (String) remoteList.getItemAtPosition(pos);
					mBluetoothThread.sendLine(TCRC_Protocol.SET_REMOTE
							.toString());
					mBluetoothThread.sendLine(remote);
					global.Remote = remote;
					finish();
				}

			});

		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.set_remote, menu);
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
