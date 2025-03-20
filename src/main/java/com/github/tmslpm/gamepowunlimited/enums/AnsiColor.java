/*
 * # MIT License
 *
 * Copyright (c) 2024 [tmslpm](https://github.com/tmslpm)
 *
 * Permission is hereby granted, free of charge, to any person
 * obtaining a copy of this software and associated documentation
 * files (the "Software"), to deal in the Software without
 * restriction, including without limitation the rights to use,
 * copy, modify, merge, publish, distribute, sublicense, and/or
 * sell copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following
 * conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 */

package com.github.tmslpm.gamepowunlimited.enums;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

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

    @Contract(pure = true)
    AnsiColor(String s) {
        this.str = s;
    }

    @Contract(pure = true)
    @Override
    public String toString() {
        return this.str;
    }

    @Contract(pure = true)
    public @NotNull String apply(String str) {
        return this.str + str + AnsiColor.RESET;
    }

    public void println(String str) {
        System.out.println(this.apply(str));
    }
}