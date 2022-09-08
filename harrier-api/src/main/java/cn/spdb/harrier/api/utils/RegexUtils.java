
package cn.spdb.harrier.api.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This is Regex expression utils.
 */
public class RegexUtils {

    /**
     * check number regex expression
     */
    private static final String CHECK_NUMBER = "^-?\\d+(\\.\\d+)?$";

    private static final String LINUX_USERNAME_PATTERN = "[a-z_][a-z\\d_]{0,30}";

    private RegexUtils() {
    }

    /**
     * check if the input is number
     *
     * @param str input
     * @return
     */
    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile(CHECK_NUMBER);
        Matcher isNum = pattern.matcher(str);
        return isNum.matches();
    }

    /**
     * check if the input is a valid linux username
     * @param str input
     * @return boolean
     */
    public static boolean isValidLinuxUserName(String str) {
        Pattern pattern = Pattern.compile(LINUX_USERNAME_PATTERN);
        return pattern.matcher(str).matches();
    }

    public static String escapeNRT(String str) {
        // Logging should not be vulnerable to injection attacks: Replace pattern-breaking characters
        if (str != null && !str.isEmpty()) {
            return str.replaceAll("[\n|\r|\t]", "_");
        }
        return null;
    }

}
