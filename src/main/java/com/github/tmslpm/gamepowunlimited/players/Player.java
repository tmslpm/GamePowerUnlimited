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

package com.github.tmslpm.gamepowunlimited.players;

import com.github.tmslpm.gamepowunlimited.Main;
import com.github.tmslpm.gamepowunlimited.enums.PieceType;

import java.io.File;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Player {
    private static int UNIQUE_ID_GEN = 0;
    private final int id;
    private final PieceType type;
    private final int score;

    public Player(PieceType type) {
        this.type = type;
        this.id = Player.UNIQUE_ID_GEN++;
        this.score = 0;
    }

    public int getPosition(int maxY) {
        int posY = 0;
        do {
            System.out.println("select the column: ");
            try {
                posY = Main.SCANNER.nextInt();
            } catch (Exception ignored) {
                Main.SCANNER.next();
                posY = 0;
            }
        } while (!(posY > 0 && posY <= maxY));


        return posY;
    }

    public PieceType getType() {
        return this.type;
    }
}