package total.control.remote;

import BluetoothUtils.BluetoothThread;
import android.app.Application;

public class TCRCService extends Application {
	final String TAG = "Total Control Remote Control";
	BluetoothThread mBluetoothThread = new BluetoothThread();
}
