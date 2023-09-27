package com.github.tmslpm.gamepowunlimited.enums;

/**
 * <pre><code>
 * int i, j, n;
 * for (i = 0; i < 11; i++) {
 *      for (j = 0; j < 10; j++) {
 *          n = 10 * i + j;
 *          if (n > 108) break;
 *          System.out.printf("\033[%dm %3d\033[m", n, n);
 *      }
 *      System.out.println("\n");
 * }
 * </code></pre>
 * @author tmslpm
 */
public enum AnsiColor {
    BLACK("\u001B[30m"),
    RED("\u001B[31m"),
    GREEN("\u001B[32m"),
    YELLOW("\u001B[33m"),
    BLUE("\u001B[34m"),
    MAGENTA("\u001B[35m"),
    CYAN("\u001B[36m"),
    WHITE("\u001B[37m"),
    LIGHT_BLACK("\u001B[90m"),
    LIGHT_RED("\u001B[91m"),
    LIGHT_GREEN("\u001B[92m"),
    LIGHT_YELLOW("\u001B[93m"),
    LIGHT_BLUE("\u001B[94m"),
    LIGHT_MAGENTA("\u001B[95m"),
    LIGHT_CYAN("\u001B[96m"),
    LIGHT_WHITE("\u001B[97m"),

    RESET("\u001B[0m");

    private final String str;

    AnsiColor(String s) {
        this.str = s;
    }

    @Override
    public String toString() {
        return this.str;
    }

    public String apply(String str) {
        return this.str + str + AnsiColor.RESET;
    }

    public void println(String str) {
        System.out.println(this.apply(str));
    }
}