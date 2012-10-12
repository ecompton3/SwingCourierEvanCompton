package swingCourier.Models;

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
	private final char[] NORTHS = {'A','N','B'};
	private final char[] EASTS = {'B','E','C'};
	private final char[] SOUTHS = {'C','S','D'};
	private final char[] WESTS = {'D','W','A'};
	private final char[] NEXT = {SE,SW};
	private final char[] BACK = {SW,SE};
	
	public String checkGesture(String points) {
		if(checkNext(points)) {
			return "Next";
		} else if(checkBack(points)) {
			return "Back";
		}
		return "None";
	}

	private boolean checkNext(String points) {
		String regex = "^.{0,2}[C]*[D]*.{0,2}$";
		Pattern n = Pattern.compile(regex);
		if (n.matcher(points).find()) {
			return true;
		}
		return false;
	}

	private boolean checkBack(String points) {
		String regex = "^.{0,2}[D]*[C]*.{0,2}$";
		Pattern b = Pattern.compile(regex);
		if (b.matcher(points).find()) {
			return true;
		}
		return false;
	}

}
