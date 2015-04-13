package util;

public class AngleMaster {

	public static double calculateAngle(int xOrig, int yOrig, int xDest, int yDest) {
		return Math.atan2(yDest - yOrig, xDest - xOrig);
	}
	
}
