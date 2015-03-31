package main;

import java.util.HashMap;

import javax.swing.JTextPane;

import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class OutputWriter {
	public HashMap<String, Double> numbers = new HashMap<String, Double>();
	public HashMap<String, Boolean> booleans = new HashMap<String, Boolean>();
	private JTextPane text;
	private static HashMap<String,OutputWriter> instanses = new HashMap<String,OutputWriter>();
	private NetworkTable table;
	OutputWriter(NetworkTable table){
		this.table = table;
		(new WindowUpdater()).start();
	}
	public void putNumber(String key, double value){
		numbers.put(key, value);
		this.table.putNumber(key, value);
	}
	public void putBoolean(String key, boolean value){
		booleans.put(key, value);
		this.table.putBoolean(key, value);
	}
	public void putString(String key, String value){
		this.table.putString(key, value);
	}
	public double getNumber(String key){
		return this.table.getNumber(key);
	}
	public boolean getBoolean(String key){
		return this.table.getBoolean(key);
	}
	public String getString(String key){
		return this.table.getString(key);
	}
	public boolean isConnected(){
		return this.table.isConnected();
	}
	public static void setClientMode(){
		NetworkTable.setClientMode();
	}
	public static void setTeam(int number){
		NetworkTable.setTeam(number);
	}
	public NetworkTable getTheTable(){
		return this.table;
	}
	public void setTable(NetworkTable table){
		this.table = table;
	}
	public static OutputWriter getTable(String name){
		System.out.println(name);
		NetworkTable.setTeam(2984);
		NetworkTable.setClientMode();
		NetworkTable.setIPAddress("roboRIO-2984.local");
		OutputWriter writer = new OutputWriter(NetworkTable.getTable(name));
		instanses.put(name, writer);
		return writer;
	}
	public static OutputWriter getWriter(String name){
		return instanses.get(name);
	}
	public JTextPane getText(){
		return this.text;
	}
}
