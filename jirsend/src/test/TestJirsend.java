package test;

import jirsend.Jirsend;

public class TestJirsend {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		

		try {
			Jirsend jirsend = new Jirsend("/dev/lircd");

			
			jirsend.sendCommand("samsung", "key_power");
		} catch (Exception e) {

		}
	}

}
