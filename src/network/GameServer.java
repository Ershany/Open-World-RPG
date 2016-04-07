package network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import gamestatemanager.GameState;
import gamestatemanager.GameStateManager;
import gamestatemanager.LevelState;
import projectile.Projectile;

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
	// player-xPos-yPos-currentTimeMillis
	// projectile-xOrig-yOrig-xDest-yDest-width-height-speed-projectileLife
	public void sendData(byte[] data) {
		if (!connected) {
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
	// player-xPos-yPos-currentTimeMillis-levelname
	// projectile-xOrig-yOrig-xDest-yDest-width-height-speed-projectileLife-levelname
	private void receiveData() {
		while (true) {
			byte[] data = new byte[1024];
			DatagramPacket packet = new DatagramPacket(data, data.length);
			try {
				socket.receive(packet);
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (!connected) {
				otherAddress = packet.getAddress();
				otherPort = packet.getPort();
				connected = true;
			}

			// Do something with the packet
			String message = new String(packet.getData()).trim();
			String[] messageData = message.split("-");
			
			GameState gs = gsm.getStates().peek();
			LevelState state = null;
			if(gs instanceof LevelState) {
				state = (LevelState)gs;
			}
			// Make sure that the player is in a state
			if(state == null) {
				return;
			} 
			
			if (messageData[0].equals("player")) {
				if(messageData[messageData.length - 1].equals(state.levelName)) {
					state.onlinePlayer.hide = false;
					state.onlinePlayer.xTarg = Float.parseFloat(messageData[1]);
					state.onlinePlayer.yTarg = Float.parseFloat(messageData[2]);
				}
				else {
					state.onlinePlayer.hide = true;
				}
			}
			else if (messageData[0].equals("projectile")) {
				if(messageData[messageData.length - 1].equals(state.levelName)) {
					state.addProjectile(
							new Projectile(Float.parseFloat(messageData[1]), Float.parseFloat(messageData[2]),
									Float.parseFloat(messageData[3]), Float.parseFloat(messageData[4]),
									Integer.parseInt(messageData[5]), Integer.parseInt(messageData[6]),
									Float.parseFloat(messageData[7]), Integer.parseInt(messageData[8]), 0, state, false));
				}
			}
		}
	}

}
