
package cn.spdb.harrier.common.uitls;

public class StringUtils extends org.apache.commons.lang3.StringUtils {
	public static final String EMPTY = "";

	public static boolean isEmpty(final String cs) {
		return cs == null || cs.length() == 0;
	}

	public static boolean isNotEmpty(final String cs) {
		return !isEmpty(cs);
	}

	public static boolean isBlank(String s) {
		if (isEmpty(s)) {
			return true;
		}
		return s.trim().length() == 0;
	}

	public static boolean isNotBlank(String s) {
		return !isBlank(s);
	}

	public static boolean inStringIgnoreCase(String str, String... strs) {
		if (str != null && strs != null) {
			for (String s : strs) {
				if (str.equalsIgnoreCase(s.trim())) {
					return true;
				}
			}
		}
		return false;
	}
}
