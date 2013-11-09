package huard.website.util;

public class LanguageUtils {

	private static final Range HEBREW_UNICODE_RANGE = new Range(0x0590, 0x05FF);

	public static boolean textContainsHebrew(String text) {
		for (char c : text.toCharArray()) {
			if (HEBREW_UNICODE_RANGE.inRange((int) c))
				return true;
		}
		return false;
	}


}