package com.tcore.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.*;


public class StringUtils {

    private static final NavigableMap<Long, String> suffixes = new TreeMap<>();

    static {
        suffixes.put(1_000L, "k");
        suffixes.put(1_000_000L, "M");
        suffixes.put(1_000_000_000L, "B");
        suffixes.put(1_000_000_000_000L, "T");
        suffixes.put(1_000_000_000_000_000L, "P");
        suffixes.put(1_000_000_000_000_000_000L, "E");
    }

    /**
     * Gets the date by a format
     *
     * @param format given for the result
     * @return the date
     */
    public static String getDateString(String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        Date date = new Date();
        return formatter.format(date);
    }

    /**
     * Gets the date by a format
     *
     * @param format given for the result
     * @return the date
     */
    public static String getDateString(String format, long when) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        Date date = new Date(when);
        return formatter.format(date);
    }

    /**
     * Get only ints in a string
     *
     * @param string
     * @return
     */
    public static Integer getOnlyInts(String string) {
        StringBuilder builder = new StringBuilder();
        for (char c : string.toCharArray()) {
            if (isInt(c)) builder.append(c);
        }
        return Integer.parseInt(builder.toString());
    }

    /**
     * Gets the int value before a given char (ex: input=Cow x10 && c=x => result=10)
     *
     * @param input the whole string
     * @param c     char
     * @return all numbers before a char
     */
    public static int getNumbersPrecededByChar(String input, char c) {
        if (input.isEmpty()) return -1;
        StringBuilder builder = new StringBuilder();
        char[] chars = input.toCharArray();
        boolean flag = false;
        for (int i = 0; i < chars.length; i++) {
            if (i != 0 && flag && isInt(chars[i])) {
                builder.append(chars[i]);
            }
            if (chars[i] == c) flag = true;
        }
        if (builder.toString().isEmpty()) return -1;
        return Integer.parseInt(builder.toString());
    }

    public static boolean containsMultiple(String input, String contains) {
        if (!input.contains(contains)) return false;
        List<List<Integer>> indexes = getIndexes(input, contains);

        return !indexes.isEmpty() && indexes.size() > 1;
    }

    public static String getLastColor(String input, char c, char finalz) {
        if (input.contains(String.valueOf(c))) {
            StringBuilder builder = new StringBuilder();
            char temp = '~';
            char temp2 = '~';
            boolean combo = false;
            for (int j = input.length() - 1; j >= 0; j--) {
                char k = input.toCharArray()[j];
                if (temp == '~' && k == c && hasNext(input.toCharArray(), j)) {
                    temp = input.toCharArray()[j + 1];
                    if (temp2 == '~' && hasPrevious(input.toCharArray(), j - 1) && input.toCharArray()[j - 2] == c) {
                        temp2 = input.toCharArray()[j - 1];
                        combo = true;
                    }
                }
                if (combo) combo = false;
            }
            return builder.append(temp2 != '~' ? finalz : "").append(temp2 != '~' ? temp2 : "").append(temp != '~' ? finalz : "").append(temp != '~' ? temp : "").toString();
        } else return "";
    }

    /**
     * Gets a string with the first letter in CAPS
     *
     * @param input string
     * @return the string with the first letter UPPER case
     */
    public static String firstUpper(String input) {
        return input.substring(0, 1).toUpperCase() + input.substring(1);
    }

    /**
     * Parse this string by OGMessage logic ({0},{1},{n..} => args) && (<colorCode> to <§trueColorCode>)
     *
     * @param input string
     * @param args  to be parsed
     * @return the parsed string
     */
    public static String parseString(String input, Object... args) {
        String result = input;
        for (int i = 0; i < args.length; i++) {
            result = result.replace("{" + i + "}", String.valueOf(args[i]));
        }
        for (ChatColor chatColor : ChatColor.values()) {
            result = result.replace("<" + chatColor.getChar() + ">", chatColor.toString());
        }

        return result;
    }

    /**
     * List to String
     *
     * @param list List<String>
     * @return a string
     * @see #listToString(List, String)
     */
    public static String listToString(List<String> list) {
        return listToString(list, "");
    }

    /**
     * Gets a string by a list
     *
     * @param list to iterate
     * @param end  string to add at the end of every object
     * @return a string
     */
    public static String listToString(List<String> list, String end) {
        StringBuilder message = new StringBuilder();
        for (String a : list) {
            if (a != null) {
                message.append(a).append(end);
            }
        }
        return message.toString();
    }

    /**
     * Multiply a string for n times
     *
     * @param s string
     * @param n times
     * @return string multiplied
     */
    public static String stringMultiply(String s, int n) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            sb.append(s);
        }
        return sb.toString();
    }

    public static int evaluateRomanNumerals(String roman) {
        return (int) evaluateNextRomanNumeral(roman, roman.length() - 1, 0);
    }

    private static double evaluateNextRomanNumeral(String roman, int pos, double rightNumeral) {
        if (pos < 0) return 0;
        char ch = roman.charAt(pos);
        double value = Math.floor(Math.pow(10, "IXCM".indexOf(ch))) + 5 * Math.floor(Math.pow(10, "VLD".indexOf(ch)));
        return value * Math.signum(value + 0.5 - rightNumeral) + evaluateNextRomanNumeral(roman, pos - 1, value);
    }

    public static String intToRoman(int num) {
        StringBuilder sb = new StringBuilder();
        int times;
        String[] romans = new String[]{"I", "IV", "V", "IX", "X", "XL", "L", "XC", "C"};
        int[] arabic = new int[]{1, 4, 5, 9, 10, 40, 50, 90, 100};
        for (int i = arabic.length - 1; i >= 0; i--) {
            times = num / arabic[i];
            num %= arabic[i];
            while (times > 0) {
                sb.append(romans[i]);
                times--;
            }
        }
        return sb.toString();
    }

    /**
     * Gets the exact indexes of a word contained in a given string(full) ignoring values given
     *
     * @param full     the whole string
     * @param input    word wanted
     * @param ignoring string ignored
     * @return list of list of ints
     */
    public static List<List<Integer>> getExactIndexes(String full, String input, String... ignoring) {

        List<List<Integer>> result = new ArrayList<>();
        char[] fullChar = full.toCharArray();
        char[] inputChar = input.toCharArray();

        boolean currentControl;

        for (int i = 0; i < fullChar.length; i++) {
            List<Integer> ints = new ArrayList<>();
            currentControl = false;
            for (int k = 0; k < inputChar.length - 1; k++) {
                if (hasNext(fullChar, i + k) && Arrays.asList(ignoring).contains(String.valueOf(fullChar[i + k])) && currentControl) {
                    ints.add(i + k);
                    k--;
                    i++;
                    continue;
                }
                if (hasNext(fullChar, (i + k) - 1) && fullChar[i + k] == inputChar[k]) {
                    currentControl = true;
                    ints.add(i + k);
                } else {
                    ints.clear();
                    currentControl = false;
                    break;
                }
            }
            if (currentControl) {
                result.add(ints);
            }
        }
        return result;
    }

    /**
     * Gets the exact indexes of a word contained in a given string(full) ignoring values given
     *
     * @param full     the whole string
     * @param input    word wanted
     * @param ignoring string ignored
     * @return list of list of ints
     */
    public static List<List<Integer>> getIndexes(String full, String input, String... ignoring) {

        List<List<Integer>> result = new ArrayList<>();
        char[] fullChar = full.toCharArray();
        char[] inputChar = input.toCharArray();

        boolean currentControl;

        for (int i = 0; i < fullChar.length; i++) {
            List<Integer> ints = new ArrayList<>();
            currentControl = false;
            for (int k = 0; k < inputChar.length - 1; k++) {
                if (hasNext(fullChar, i + k) && Arrays.asList(ignoring).contains(String.valueOf(fullChar[i + k])) && currentControl) {
                    ints.add(i + k);
                    k--;
                    i++;
                    continue;
                }
                if (hasNext(fullChar, (i + k) - 1) && fullChar[i + k] == inputChar[k]) {
                    currentControl = true;
                    ints.add(i + k);
                } else {
                    ints.clear();
                    currentControl = false;
                    break;
                }
            }
            if (currentControl) {
                result.add(ints);
            }
        }
        return result;
    }

    public static String uuidConvert(UUID uuid) {
        return uuid.toString().toLowerCase().replace("-", "");
    }

    /**
     * Checks if that index is valid in a char array
     *
     * @param array char array
     * @param index index to check
     * @return if has previous
     */
    public static boolean hasPrevious(char[] array, int index) {
        try {
            char a = array[index - 1];
            return true;
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }
    }

    /**
     * Checks if that index is valid in a char array
     *
     * @param array char array
     * @param index index to check
     * @return if has next
     */
    public static boolean hasNext(char[] array, int index) {
        try {
            char a = array[index + 1];
            return true;
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }
    }

    /**
     * Gets a string between two others
     *
     * @param input the whole string
     * @param a     first string
     * @param b     second string
     * @return a string between two strings
     */
    public static String getStringBetweenTwoStrings(String input, String a, String b) {
        return input.substring(input.indexOf(a) + 1, input.indexOf(b));
    }

    /**
     * Parse a List of String
     *
     * @param input a list of string
     * @param args  args to be parsed
     * @return the parsed list
     * @see #parseString(String, Object...)
     */
    public static List<String> parseListString(List<String> input, Object... args) {
        final List<String> result = new ArrayList<>();
        input.forEach(string -> {
            String newz = string;
            for (ChatColor chatColor : ChatColor.values()) {
                newz = newz.replace("<" + chatColor.getChar() + ">", chatColor.toString());
                for (int i = 0; i < args.length; i++) {
                    newz = newz.replace("{" + i + "}", String.valueOf(args[i]));
                }
            }
            result.add(newz);
        });

        return result;
    }

    /**
     * Get the string value of following input element
     *
     * @param input composed of sentence
     * @param index starting index
     * @return the sentence starting from #index
     */
    public static String buildSentence(String[] input, int index) {
        StringBuilder builder = new StringBuilder();
        for (int i = index; i < input.length; i++) {
            builder.append(input[i]);
            if (i != input.length - 1) builder.append(" ");
        }
        return builder.toString();
    }

    /**
     * Modify a char from a string
     *
     * @param input the whole string
     * @param value the char value
     * @param index the index '
     * @return modified string
     */
    public static String setCharAt(String input, char value, int index) {
        StringBuilder builder = new StringBuilder(input);
        builder.setCharAt(index, value);
        input = builder.toString();
        return input;
    }


    /**
     * Parse and replace num format (1000 => 1k)
     *
     * @param value number format
     * @return formatted string
     */
    public static String format(long value) {
        //Long.MIN_VALUE == -Long.MIN_VALUE so we need an adjustment here
        if (value == Long.MIN_VALUE) return format(Long.MIN_VALUE + 1);
        if (value < 0) return "-" + format(-value);
        if (value < 1000) return Long.toString(value); //deal with easy case

        Map.Entry<Long, String> e = suffixes.floorEntry(value);
        Long divideBy = e.getKey();
        String suffix = e.getValue();

        long truncated = value / (divideBy / 10); //the number part of the output times 10
        boolean hasDecimal = truncated < 100 && (truncated / 10d) != (truncated / 10);
        return hasDecimal ? (truncated / 10d) + suffix : (truncated / 10) + suffix;
    }

    /**
     * Checks if a string is equals to none
     *
     * @param input String input
     * @return if a string is none
     */
    public static boolean isNone(String input) {
        return input.equalsIgnoreCase("<none>");
    }

    /**
     * Checks if a char is an int
     *
     * @param c input char
     * @return if input is an int
     * @see #isInt(String)
     */
    public static boolean isInt(char c) {
        return isInt(String.valueOf(c));
    }

    /**
     * Checks if a string is an int
     *
     * @param input input string
     * @return if input is an int
     */
    public static boolean isInt(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Checks if a string is an int
     *
     * @param input input string
     * @return if input is an int
     */
    public static boolean isLong(String input) {
        try {
            Long.parseLong(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }


    /**
     * Checks if a string is encoded
     *
     * @param text to check
     * @return if string is encoded
     */
    public static boolean isEncoded(String text, String... whitelist) {

        for (String a : whitelist) {
            text = text.replace(a, "");
        }

        Charset charset = Charset.forName("US-ASCII");
        String checked = new String(text.getBytes(charset), charset);
        return !checked.equals(text);
    }

    public static void m(String message, Object... args) {
        if (StringUtils.parseString(message, args).endsWith(".") || StringUtils.parseString(message, args).endsWith("!") || StringUtils.parseString(message, args).endsWith("?")) {
            Bukkit.getOnlinePlayers()
                    .forEach(player ->
                            //  player.sendMessage(ChatColor.GRAY + "(" + ChatColor.LIGHT_PURPLE + "!" + ChatColor.GRAY + ") " + StringUtils.parseString(message, args))
                            player.sendMessage(StringUtils.parseString(message, args))
                    );
            return;
        }
        Bukkit.getOnlinePlayers()
                .forEach(player ->
                        //  player.sendMessage(ChatColor.GRAY + "(" + ChatColor.LIGHT_PURPLE + "!" + ChatColor.GRAY + ") " + StringUtils.parseString(message, args))
                        player.sendMessage(StringUtils.parseString(message, args) + ".")
                );
    }

    public static void m(String message, Player player, Object... args) {
        if (StringUtils.parseString(message, args).endsWith(".") || StringUtils.parseString(message, args).endsWith("!") || StringUtils.parseString(message, args).endsWith("?")) {
            player.sendMessage(StringUtils.parseString(message, args));
            return;
        }
        player.sendMessage(StringUtils.parseString(message, args) + ".");
    }

    public static void op(String message) {
        Bukkit.getOnlinePlayers().forEach(player -> player.sendMessage(message));
    }

    public static void m(String message, CommandSender target, Object... args) {
        if (StringUtils.parseString(message, args).endsWith(".") || StringUtils.parseString(message, args).endsWith("!") || StringUtils.parseString(message, args).endsWith("?")) {
            target.sendMessage(StringUtils.parseString(message, args));
            return;
        }
        target.sendMessage(StringUtils.parseString(message, args) + ".");
    }

}
