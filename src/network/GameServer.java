package network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import gamestatemanager.GameState;
import gamestatemanager.GameStateManager;
import gamestatemanager.LevelState;

public class GameServer {

	public static final int PORT_NUMBER = 4871;
	private DatagramSocket socket;
	private GameStateManager gsm;
	
	// Other players stats
	public boolean connected;
	private InetAddress otherAddress;
	private int otherPort;

	public GameServer(GameStateManager gsm) {
		this.gsm = gsm;
		try {
			this.socket = new DatagramSocket(PORT_NUMBER);
		} catch (SocketException e) {
			e.printStackTrace();
		}

		Thread receive = new Thread(new Runnable() {
			@Override
			public void run() {
				receiveData();
			}
		});
		receive.start();
	}

	// Prefixing Guide:
	// player-xPos-yPos
	public void sendData(byte[] data) {
		if(!connected) {
			System.out.println("No connection established");
			return;
		}
		DatagramPacket packet = new DatagramPacket(data, data.length, otherAddress, otherPort);
		try {
			socket.send(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Prefixing Guide:
	// player-xPos-yPos
	private void receiveData() {
		while (true) {
			byte[] data = new byte[1024];
			DatagramPacket packet = new DatagramPacket(data, data.length);
			try {
				socket.receive(packet);
			} catch (IOException e) {
				e.printStackTrace();
			}
			if(!connected) {
				otherAddress = packet.getAddress();
				otherPort = packet.getPort();
				connected = true;
			}

			// Do something with the packet
			String message = new String(packet.getData()).trim();
			String[] messageData = message.split("-");
			if(messageData[0].equals("player")) {
				GameState gs = gsm.getStates().peek();
				if(gs instanceof LevelState) {
					LevelState state = (LevelState)gs;
					state.onlinePlayer.xTarg = Float.parseFloat(messageData[1]);
					state.onlinePlayer.yTarg = Float.parseFloat(messageData[2]);
				}
			}
		}
	}

}
