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
		if(checkSelect(pointString)) {
			return "Select";
		} else if(checkUp(pointString)) {
			return "Up";
		}else if(checkDown(pointString)) {
			return "Down";
		}else if(checkList(pointString)) {
			return "List";
		} else if(checkNext(pointString)) {
			return "Next";
		}else if(checkDeleteItem(pointString)) {
			return "DeleteItem";
		} else if(checkBack(pointString)) {
			return "Back";
		} else if(checkDelete(pointString)) {
			return "Delete";
		} 
		return "None";
	}
	
	/**
	 * Checks if the String matches the delete gesture
	 * @param pointString
	 * @return
	 */
	private boolean checkDelete(String pointString) {
		String regex = "^.{0,2}[W,J,A,D,N]{1,}[S,J,D,C]{1,}[C,E,J,B]{1,}[N,J,B,A,E]{1,}[B,E,C,J]{1,}[N,J,B,A,E,W]{1,}[S,D]{1,}[S,J,E,D,C]{1,}.{0,2}$";
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
		String regex = "^.{0,2}[W,J,A,D,N]{1,}[S,J,D,C]{1,}[C,E,J,B]{1,}[N,J,B,A,E]{1,}[B,E,C,J]{1,}[N,J,B,A,W]*[N]{1,}[N,J,B,A,E,W]{1,}.{0,2}$";
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
	
	private boolean checkList(String pointString) {
		String regex = "^.{0,2}[W,A,D,J]{1,}[C,S,D,J,E]*[S]{1,}[C,S,D,J,E]*[B,E,C,A,J]{1,}.{0,2}$";
		Pattern b = Pattern.compile(regex);
		if(b.matcher(pointString).find()) {
			return true;
		}
		return false;
	}
	
	private boolean checkUp(String pointString) {
		String regex = "^.{0,2}[N,B,E,J]*[B]{1,}[N,B,E,J]*[S,C,E,J]*[C]{1,}[S,C,E,J]*.{0,2}$";
		Pattern b = Pattern.compile(regex);
		if(b.matcher(pointString).find()) {
			return true;
		}
		return false;
	}
	private boolean checkDown(String pointString) {
		String regex = "^.{0,2}[S,C,E,J]*[C]{1,}[S,C,E,J]*[N,B,E,J]*[B]{1,}[N,B,E,J]*.{0,2}$";
		Pattern b = Pattern.compile(regex);
		if(b.matcher(pointString).find()) {
			return true;
		}
		return false;
	}
	
	private boolean checkDeleteItem(String pointString) {
		String regex = "^.{0,2}[S,C,E,J]*[C]{1,}[S,C,E,J]*[A,N,B,J]*[N]{1,}[A,N,B,J]*[S,D,W,J]*[D]{1,}[S,D,W,J]*.{0,2}$";
		Pattern b = Pattern.compile(regex);
		if(b.matcher(pointString).find()) {
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
