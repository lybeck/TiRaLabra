/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import java.util.regex.Pattern;

/**
 *
 * @author Lasse
 */
class ParseUtils {

    static void wrongNumberOfParametersInFunction(String function) {
        throw new IllegalArgumentException("Wrong number of parameters in command '" + function + "'!");
    }

    static String[] getParameterSplit(String params) {
        String[] split = params.split(",");
        for (int i = 0; i < split.length; i++) {
            split[i] = split[i].trim();
        }
        return split;
    }

    static int[] getParameterSplitAsInts(String params) {
        String[] split = ParseUtils.getParameterSplit(params);
        if (split.length == 1 && split[0].isEmpty()) {
            return new int[0];
        }
        int[] ints = new int[split.length];
        String curr = null;
        try {
            for (int i = 0; i < split.length; i++) {
                curr = split[i];
                ints[i] = Integer.parseInt(curr);
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid argument: '" + curr + "'");
        }
        return ints;
    }

    static double parseDouble(String string) {
        String[] split = string.split("/");
        if (split.length == 1) {
            return Double.parseDouble(split[0]);
        }
        if (split.length > 2) {
            throw new NumberFormatException("'" + string + "' not a parsable number!");
        }
        return Double.parseDouble(split[0]) / Double.parseDouble(split[1]);
    }

    static boolean isDouble(String toParse) {
        String number = "[-+]?[0-9]*\\.?[0-9]+([eE][-+]?[0-9]+)?";
        String regex = number + "\\s*(/\\s*" + number + ")?";
        return Pattern.matches(regex, toParse);
    }

    static boolean isNonNegativeInteger(String toParse) {
        String regex = "[+]?[0-9]+([eE][+]?[0-9]+)?";
        return Pattern.matches(regex, toParse);
    }
}
