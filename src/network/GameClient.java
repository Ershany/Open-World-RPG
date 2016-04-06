package network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import gamestatemanager.GameStateManager;

public class GameClient extends Thread {

	public static final int PORT_NUMBER = 4871;
	private InetAddress address; 
	private DatagramSocket socket;
	private GameStateManager gsm;
	
	public GameClient(GameStateManager gsm, String ipAddress) {
		this.gsm = gsm;
		try {
			this.socket = new DatagramSocket();
			this.address = InetAddress.getByName(ipAddress);
		} catch(UnknownHostException e) {
			e.printStackTrace();
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		while(true) {
			byte[] data = new byte[1024];
			DatagramPacket packet = new DatagramPacket(data, data.length);
			try {
				socket.receive(packet);
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println("Server: " + new String(packet.getData()));
		}
	}
	
	public void sendData(byte[] data) {
		DatagramPacket packet = new DatagramPacket(data, data.length, address, PORT_NUMBER);
		try {
			socket.send(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
