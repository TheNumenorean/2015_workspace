package main;

import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class ConnectionSetter extends Thread {
	private static String[] IPs = new String[]{"10.29.84.20", "10.29.84.21"};
	
	private NetworkTable table;
	private int tableIndex = 0;
	ConnectionSetter(OutputWriter writer){
		this.table = writer.getTheTable();
	}
	
	public void run(){
		while(true){
			if(!table.isConnected()){
				this.tableIndex = (this.tableIndex + 1)%IPs.length;
				NetworkTable.setIPAddress(IPs[this.tableIndex]);
			}
			try {
				Thread.sleep(5000L);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
