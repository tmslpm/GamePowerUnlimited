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

import java.awt.*;

/**
 * The different types of pieces that make up the game map/grid
 * @author tmslpm
 */
public enum PieceType {
    EMPTY(" ", Color.BLACK), // don't move, the test use this index 0 = EMPTY
    BARRIER(" ", Color.BLACK), // don't move, the test use this index 1 = BARRIER
    RED(AnsiColor.RED.apply("O"), new Color(255, 69, 69)), // don't move, the test use this  index 2 = RED
    YELLOW(AnsiColor.YELLOW.apply("O"), new Color(239, 195, 0)), // don't move, the test use this index 3 = YELLOW
    MAGENTA(AnsiColor.MAGENTA.apply("O"), Color.MAGENTA),
    BLACK(AnsiColor.BLACK.apply("O"), Color.BLACK),
    BLUE(AnsiColor.BLUE.apply("O"), Color.BLUE),
    CYAN(AnsiColor.CYAN.apply("O"), Color.CYAN),
    WHITE(AnsiColor.WHITE.apply("O"), Color.WHITE),
    GREEN(AnsiColor.GREEN.apply("O"), Color.GREEN),
    LIGHT_RED(AnsiColor.LIGHT_RED.apply("O"), new Color(255,204,203)),
    LIGHT_YELLOW(AnsiColor.LIGHT_YELLOW.apply("O"), new Color(255,255,190)),
    LIGHT_MAGENTA(AnsiColor.LIGHT_MAGENTA.apply("O"), new Color(255,125,255)),
    LIGHT_BLACK(AnsiColor.LIGHT_BLACK.apply("O"), new Color(45,45,45)),
    LIGHT_BLUE(AnsiColor.LIGHT_BLUE.apply("O"), new Color(173, 216, 230)),
    LIGHT_CYAN(AnsiColor.LIGHT_CYAN.apply("O"), new Color(224,255,255)),
    LIGHT_WHITE(AnsiColor.LIGHT_WHITE.apply("O"), new Color(255,255,247)),
    LIGHT_GREEN(AnsiColor.LIGHT_GREEN.apply("O"), new Color(144,238,144)),
    ;

    private final String str;
    private final Color color;

    PieceType(String str, Color color) {
        this.str = str;
        this.color = color;
    }

    @Override
    public String toString() {
        return str;
    }

    public Color getColor() {
        return color;
    }
}