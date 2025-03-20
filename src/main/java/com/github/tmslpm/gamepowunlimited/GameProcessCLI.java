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

package com.github.tmslpm.gamepowunlimited;

import com.github.tmslpm.gamepowunlimited.enums.AnsiColor;
import com.github.tmslpm.gamepowunlimited.enums.GameState;
import com.github.tmslpm.gamepowunlimited.enums.PieceType;
import com.github.tmslpm.gamepowunlimited.players.Player;
import com.github.tmslpm.gamepowunlimited.players.PlayerIA;

public class GameProcessCLI extends GamePowerUnlimited {
    private final String selectorStr;

    public GameProcessCLI(int comboLength, int xLength, int yLength, boolean registerInfoForIA) {
        super(comboLength, xLength, yLength, registerInfoForIA);

        StringBuilder selectorDisplayYPso = new StringBuilder().append("|");

        for (int i = 0; i < this.getYLength(); i++) {
            selectorDisplayYPso.append(i + 1).append('|');
        }

        this.selectorStr = selectorDisplayYPso + "\n" + "-".repeat(selectorDisplayYPso.length());
    }

    public static void start() {
        GameProcessCLI game = new GameProcessCLI(4, 6, 7, true);
        // Set player
        game.setPlayers(new Player[] {new Player(PieceType.RED), new PlayerIA(game, PieceType.YELLOW)});
        // Start game
        do game.playRound();
        while (game.continueGame());
    }

    private boolean continueGame() {
        System.out.println("leave the game? ? (y/n)");
        return true;
        //return !Main.SCANNER.next().toLowerCase().contains("y");
    }

    @Override
    public void onRender(GameState gamestate) {
        StringBuilder sb = new StringBuilder();
        for (int x = 0; x < this.getXLength(); x++) {
            sb.append("\n|");
            for (int y = 0; y < this.getYLength(); y++) {
                PieceType piece = this.getGrid()[x + this.getComboLength()][y + this.getComboLength()];
                sb.append(PieceType.EMPTY.equals(piece) ? " " : piece).append("|");
            }
        }

        System.out.println(this.selectorStr + sb + "\n" + "_".repeat(30));
    }

    @Override
    public void onTie() {
        AnsiColor.YELLOW.println("tie");
    }

    @Override
    public void onWin() {
        AnsiColor.GREEN.println("win");
    }

}