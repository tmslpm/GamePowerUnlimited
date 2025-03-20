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

import com.github.tmslpm.gamepowunlimited.GamePowerUnlimited;
import com.github.tmslpm.gamepowunlimited.enums.PieceType;

import java.util.Random;

public class PlayerIA extends Player {

    private static final Random RNG = new Random();
    private final transient GamePowerUnlimited gameProcess;

    public PlayerIA(GamePowerUnlimited gameProcess, PieceType type) {
        super(type);
        this.gameProcess = gameProcess;
    }

    /**v
     * ---------------
     * | | | | | | | |
     * | | | | |x| | |
     * | | | | |0| | |
     * | | | | |0| | |
     * | | | | |0| | |
     * ---------------
     * | | | | | | | |
     * | | | | | | | |
     * | | | | | | | |
     * | | |0|0| | | |
     * |x|0|0|0|x| | |
     * ---------------
     * | | | | | | | |
     * | | | | |x| | |
     * | | | |0|8| | |
     * | | |0|8|0| | |
     * | |0|8|8|0| | |
     *
     * @param maxY - the maximum length for the axe Y
     * @return int position Y generated with precision or not
     */
    @Override
    public int getPosition(int maxY) {
        return PlayerIA.getRandomPosition(gameProcess);
    }

    public static int getRandomPosition(GamePowerUnlimited game) {
        int lastPosY = game.getLastPositionPieceAddedY() - game.getComboLength();

        System.out.println(lastPosY);
        //int precision = this.getScore() * 2;
        final int minAxeY = 1;
        final int maxAxeY = game.getYLength();

        if (!game.isPieceAt(game.getComboLength(), lastPosY, PieceType.EMPTY)) {
            int y;
            do {
                 y = RNG.nextInt(minAxeY, maxAxeY);
            } while (game.isPieceAt(game.getComboLength(), y, PieceType.EMPTY) );
            return y;
        } else {
            return getRandomPositionFromOtherPos(lastPosY, minAxeY, maxAxeY, 3);
        }
    }

    protected static int getRandomPositionFromOtherPos(int posAxe, int minPosAxe, int maxPosAxe, int range) {
        return RNG.nextInt(
                Math.max(posAxe - range, minPosAxe + 1),
                Math.min(posAxe + range, maxPosAxe + 1)
        );
    }
}