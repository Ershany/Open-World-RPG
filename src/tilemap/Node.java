package tilemap;

import util.Vector2f;

public class Node {

	//gcost is the distance of your journey, from tile to tile
	//hcost is the distance from where you are, to the goal
	//fcost is combination of gcost and hcost
	public Vector2f tile;
	public Node parent;
	public double fCost, gCost, hCost; 
	
	public Node(Vector2f tile, Node parent, double gCost, double hCost) {
		this.tile = tile;
		this.parent = parent;
		this.gCost = gCost;
		this.hCost = hCost;
		this.fCost = this.gCost + this.hCost;
	}
	
}
