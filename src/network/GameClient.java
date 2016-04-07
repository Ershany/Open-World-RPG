package network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import gamestatemanager.GameState;
import gamestatemanager.GameStateManager;
import gamestatemanager.LevelState;

public class GameClient {

	private InetAddress address;
	private DatagramSocket socket;
	private GameStateManager gsm;

	public GameClient(GameStateManager gsm, String ipAddress) {
		this.gsm = gsm;
		try {
			this.socket = new DatagramSocket();
			this.address = InetAddress.getByName(ipAddress);
		} catch (UnknownHostException e) {
			e.printStackTrace();
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
		DatagramPacket packet = new DatagramPacket(data, data.length, address, GameServer.PORT_NUMBER);
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
