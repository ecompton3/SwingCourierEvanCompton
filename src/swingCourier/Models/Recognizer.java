package swingCourier.Models;

import java.util.List;
import java.util.regex.Pattern;

public class Recognizer {
	private final char N = 'N';
	private final char NE = 'B';
	private final char E = 'E';
	private final char SE = 'C';
	private final char S = 'S';
	private final char SW = 'D';
	private final char W = 'W';
	private final char NW = 'A';
	private final char EQUAL = 'J';
	private final char[] NORTHS = {'A','N','B'};
	private final char[] EASTS = {'B','E','C'};
	private final char[] SOUTHS = {'C','S','D'};
	private final char[] WESTS = {'D','W','A'};
	private final char[] NEXT = {SE,SW};
	private final char[] BACK = {SW,SE};
	
	/**
	 * Tests a list of points to see if they form any defined gestures
	 * @param points List of Point objects to test
	 * @return String representation of gesture
	 */
	public String checkGesture(List<Point> points) {
		String pointString = buildGesture(points);
		if(checkNext(pointString)) {
			return "Next";
		} else if(checkBack(pointString)) {
			return "Back";
		} else if(checkDelete(pointString)) {
			return "Delete";
		} else if(checkSelect(pointString)) {
			return "Select";
		}
		return "None";
	}
	
	/**
	 * Checks if the String matches the delete gesture
	 * @param pointString
	 * @return
	 */
	private boolean checkDelete(String pointString) {
		String regex = "^.{0,2}[W,J,A,D,N]{1,}[S,J,D,C]{1,}[C,E,J,B]{1,}[N,J,B,A,E]{1,}[B,E,C,J]{1,}[N,J,B,A,E]{1,}[S,D]{1,}[S,J,E,D,C]{1,}.{0,2}$";
		Pattern n = Pattern.compile(regex);
		if (n.matcher(pointString).find()) {
			return true;
		}
		return false;
	}
	/**
	 * Checks if the String matches the select gesture
	 * @param pointString
	 * @return
	 */
	private boolean checkSelect(String pointString) {
		String regex = "^.{0,2}[W,J,A,D,N]{1,}[S,J,D,C]{1,}[C,E,J,B]{1,}[N,J,B,A,E]{1,}[B,E,C,J]{1,}[N,J,B,A,E]{1,}.{0,2}$";
		Pattern n = Pattern.compile(regex);
		if (n.matcher(pointString).find()) {
			return true;
		}
		return false;
	}
	/**
	 * Checks if the String matches the next gesture
	 * @param pointString
	 * @return
	 */
	private boolean checkNext(String pointString) {
		String regex = "^.{0,2}[C,S,J,E]*[C]{1,}[C,S,J,E]*[D,S,J,W]*.{0,2}$";
		Pattern n = Pattern.compile(regex);
		if (n.matcher(pointString).find()) {
			return true;
		}
		return false;
	}
	/**
	 * Checks if the String matches the back gesture
	 * @param pointString
	 * @return
	 */
	private boolean checkBack(String pointString) {
		String regex = "^.{0,2}[D,S,J,W]*[D]{1,}[D,S,J,W]*[C,S,J,E]*.{0,2}$";
		Pattern b = Pattern.compile(regex);
		if (b.matcher(pointString).find()) {
			return true;
		}
		return false;
	}
	/**
	 * Creates a string representation of the change in direction between two points
	 * @param pointString
	 * @return
	 */
	private String buildGesture(List<Point> points) {
		String pointString = "";
		for(int i = 1; i < points.size(); i++) {
			pointString = pointString + points.get(i).comparePoint(points.get(i-1));
		}
		System.out.println(pointString);
		
		return pointString;
	}

}
