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

package com.github.tmslpm.gamepowunlimited.test;

import com.github.tmslpm.gamepowunlimited.GamePowerUnlimited;
import com.github.tmslpm.gamepowunlimited.enums.GameState;
import com.github.tmslpm.gamepowunlimited.enums.PieceType;
import org.junit.jupiter.api.*;

class GamePowTests {

    @Test
    @DisplayName("test game.isPieceAt(...) equals to entry")
    void test_method_isPieceAt() {
        GamePowerUnlimited game = new GamePowerUnlimited(4, 6, 7, true);
        // check corner left bottom (first column)
        Assertions.assertTrue(game.isPieceAt(9, 4, PieceType.EMPTY),
                "test if piece at x:9,y:4 is PieceType.EMPTY (first column)"
        );
        // check if is correct corner left bottom
        Assertions.assertTrue(game.isPieceAt(10, 4, PieceType.BARRIER));
        Assertions.assertTrue(game.isPieceAt(9, 3, PieceType.BARRIER));
        Assertions.assertTrue(game.isPieceAt(10, 3, PieceType.BARRIER));

        game.insertPieces(1, PieceType.RED);
        Assertions.assertTrue(game.isPieceAt(9, 4, PieceType.RED),
                "test if piece at x:9,y:4 is PieceType.RED (first column)"
        );

        game.insertPieces(1, PieceType.YELLOW);
        Assertions.assertTrue(game.isPieceAt(8, 4, PieceType.YELLOW),
                "test if piece at x:8,y:4 is PieceType.YELLOW (first column)"
        );
    }

    @Test
    @DisplayName("test game.insertPieces(...) return true if possible or false")
    void test_method_insertPieces() {
        GamePowerUnlimited game = new GamePowerUnlimited(4, 6, 7, true);
        // piece added = 1
        Assertions.assertTrue(game.insertPieces(1, PieceType.RED));
        Assertions.assertEquals(1, game.getTotalPiecesAdded());

        // piece added = 2
        Assertions.assertTrue(game.insertPieces(1, PieceType.YELLOW));
        Assertions.assertEquals(2, game.getTotalPiecesAdded());

        // piece added = 3
        Assertions.assertTrue(game.insertPieces(1, PieceType.RED));
        Assertions.assertEquals(3, game.getTotalPiecesAdded());

        // piece added = 4
        Assertions.assertTrue(game.insertPieces(1, PieceType.YELLOW));
        Assertions.assertEquals(4, game.getTotalPiecesAdded());

        // piece added = 5
        Assertions.assertTrue(game.insertPieces(1, PieceType.RED));
        Assertions.assertEquals(5, game.getTotalPiecesAdded());

        // piece added = 6
        Assertions.assertTrue(game.insertPieces(1, PieceType.RED));
        Assertions.assertEquals(6, game.getTotalPiecesAdded());

        // impossible column is full
        Assertions.assertFalse(game.insertPieces(1, PieceType.RED));
        Assertions.assertEquals(6, game.getTotalPiecesAdded());
    }

    @Test
    @DisplayName("test game.checkGameState(...) without other piece with combo equal to win ")
    void test_method_checkGameState_win_without_other_piece() {
        Assertions.assertEquals(GameState.WIN, getGame(new int[][] {
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                {1,1,1,1,0,0,0,0,0,0,0,1,1,1,1},
                {1,1,1,1,0,0,0,0,0,0,0,1,1,1,1},
                {1,1,1,1,0,0,0,2,0,0,0,1,1,1,1},
                {1,1,1,1,0,0,2,0,0,0,0,1,1,1,1},
                {1,1,1,1,0,2,0,0,0,0,0,1,1,1,1},
                {1,1,1,1,2,0,0,0,0,0,0,1,1,1,1},
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
        }).checkGameState());

        Assertions.assertEquals(GameState.WIN, getGame(new int[][] {
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                {1,1,1,1,0,0,0,0,0,0,0,1,1,1,1},
                {1,1,1,1,0,0,0,0,2,0,0,1,1,1,1},
                {1,1,1,1,0,0,0,2,0,0,0,1,1,1,1},
                {1,1,1,1,0,0,2,0,0,0,0,1,1,1,1},
                {1,1,1,1,0,2,0,0,0,0,0,1,1,1,1},
                {1,1,1,1,0,0,0,0,0,0,0,1,1,1,1},
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
        }).checkGameState());

        //////////////////////

        Assertions.assertEquals(GameState.WIN, getGame(new int[][] {
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                {1,1,1,1,2,0,0,0,0,0,0,1,1,1,1},
                {1,1,1,1,0,2,0,0,0,0,0,1,1,1,1},
                {1,1,1,1,0,0,2,0,0,0,0,1,1,1,1},
                {1,1,1,1,0,0,0,2,0,0,0,1,1,1,1},
                {1,1,1,1,0,0,0,0,0,0,0,1,1,1,1},
                {1,1,1,1,0,0,0,0,0,0,0,1,1,1,1},
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
        }).checkGameState());

        Assertions.assertEquals(GameState.WIN, getGame(new int[][] {
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                {1,1,1,1,0,0,0,0,0,0,0,1,1,1,1},
                {1,1,1,1,0,2,0,0,0,0,0,1,1,1,1},
                {1,1,1,1,0,0,2,0,0,0,0,1,1,1,1},
                {1,1,1,1,0,0,0,2,0,0,0,1,1,1,1},
                {1,1,1,1,0,0,0,0,2,0,0,1,1,1,1},
                {1,1,1,1,0,0,0,0,0,0,0,1,1,1,1},
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
        }).checkGameState());

        /////////////////

        Assertions.assertEquals(GameState.WIN, getGame(new int[][] {
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                {1,1,1,1,0,0,0,0,0,0,2,1,1,1,1},
                {1,1,1,1,0,0,0,0,0,2,0,1,1,1,1},
                {1,1,1,1,0,0,0,0,2,0,0,1,1,1,1},
                {1,1,1,1,0,0,0,2,0,0,0,1,1,1,1},
                {1,1,1,1,0,0,0,0,0,0,0,1,1,1,1},
                {1,1,1,1,0,0,0,0,0,0,0,1,1,1,1},
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
        }).checkGameState());

        Assertions.assertEquals(GameState.WIN, getGame(new int[][] {
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                {1,1,1,1,0,0,0,0,0,0,0,1,1,1,1},
                {1,1,1,1,0,0,0,0,0,2,0,1,1,1,1},
                {1,1,1,1,0,0,0,0,2,0,0,1,1,1,1},
                {1,1,1,1,0,0,0,2,0,0,0,1,1,1,1},
                {1,1,1,1,0,0,2,0,0,0,0,1,1,1,1},
                {1,1,1,1,0,0,0,0,0,0,0,1,1,1,1},
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
        }).checkGameState());

        /////////////////

        Assertions.assertEquals(GameState.WIN, getGame(new int[][] {
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                {1,1,1,1,0,0,0,0,0,0,0,1,1,1,1},
                {1,1,1,1,0,0,0,0,0,0,0,1,1,1,1},
                {1,1,1,1,0,0,0,2,0,0,0,1,1,1,1},
                {1,1,1,1,0,0,0,0,2,0,0,1,1,1,1},
                {1,1,1,1,0,0,0,0,0,2,0,1,1,1,1},
                {1,1,1,1,0,0,0,0,0,0,2,1,1,1,1},
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
        }).checkGameState());

        Assertions.assertEquals(GameState.WIN, getGame(new int[][] {
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                {1,1,1,1,0,0,0,0,0,0,0,1,1,1,1},
                {1,1,1,1,0,0,2,0,0,0,0,1,1,1,1},
                {1,1,1,1,0,0,0,2,0,0,0,1,1,1,1},
                {1,1,1,1,0,0,0,0,2,0,0,1,1,1,1},
                {1,1,1,1,0,0,0,0,0,2,0,1,1,1,1},
                {1,1,1,1,0,0,0,0,0,0,0,1,1,1,1},
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
        }).checkGameState());

        /////////////////////////

        Assertions.assertEquals(GameState.WIN, getGame(new int[][] {
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                {1,1,1,1,0,0,0,0,0,0,0,1,1,1,1},
                {1,1,1,1,0,0,0,0,0,0,0,1,1,1,1},
                {1,1,1,1,2,0,0,0,0,0,0,1,1,1,1},
                {1,1,1,1,2,0,0,0,0,0,0,1,1,1,1},
                {1,1,1,1,2,0,0,0,0,0,0,1,1,1,1},
                {1,1,1,1,2,0,0,0,0,0,0,1,1,1,1},
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
        }).checkGameState());

        Assertions.assertEquals(GameState.WIN, getGame(new int[][] {
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                {1,1,1,1,0,0,0,0,0,0,0,1,1,1,1},
                {1,1,1,1,2,0,0,0,0,0,0,1,1,1,1},
                {1,1,1,1,2,0,0,0,0,0,0,1,1,1,1},
                {1,1,1,1,2,0,0,0,0,0,0,1,1,1,1},
                {1,1,1,1,2,0,0,0,0,0,0,1,1,1,1},
                {1,1,1,1,0,0,0,0,0,0,0,1,1,1,1},
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
        }).checkGameState());

        Assertions.assertEquals(GameState.WIN, getGame(new int[][] {
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                {1,1,1,1,2,0,0,0,0,0,0,1,1,1,1},
                {1,1,1,1,2,0,0,0,0,0,0,1,1,1,1},
                {1,1,1,1,2,0,0,0,0,0,0,1,1,1,1},
                {1,1,1,1,2,0,0,0,0,0,0,1,1,1,1},
                {1,1,1,1,0,0,0,0,0,0,0,1,1,1,1},
                {1,1,1,1,0,0,0,0,0,0,0,1,1,1,1},
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
        }).checkGameState());

        ////////////////////////////::

        Assertions.assertEquals(GameState.WIN, getGame(new int[][] {
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                {1,1,1,1,2,2,2,2,0,0,0,1,1,1,1},
                {1,1,1,1,0,0,0,0,0,0,0,1,1,1,1},
                {1,1,1,1,0,0,0,0,0,0,0,1,1,1,1},
                {1,1,1,1,0,0,0,0,0,0,0,1,1,1,1},
                {1,1,1,1,0,0,0,0,0,0,0,1,1,1,1},
                {1,1,1,1,0,0,0,0,0,0,0,1,1,1,1},
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
        }).checkGameState());

        Assertions.assertEquals(GameState.WIN, getGame(new int[][] {
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                {1,1,1,1,0,2,2,2,2,0,0,1,1,1,1},
                {1,1,1,1,0,0,0,0,0,0,0,1,1,1,1},
                {1,1,1,1,0,0,0,0,0,0,0,1,1,1,1},
                {1,1,1,1,0,0,0,0,0,0,0,1,1,1,1},
                {1,1,1,1,0,0,0,0,0,0,0,1,1,1,1},
                {1,1,1,1,0,0,0,0,0,0,0,1,1,1,1},
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
        }).checkGameState());

        Assertions.assertEquals(GameState.WIN, getGame(new int[][] {
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                {1,1,1,1,0,0,2,2,2,2,0,1,1,1,1},
                {1,1,1,1,0,0,0,0,0,0,0,1,1,1,1},
                {1,1,1,1,0,0,0,0,0,0,0,1,1,1,1},
                {1,1,1,1,0,0,0,0,0,0,0,1,1,1,1},
                {1,1,1,1,0,0,0,0,0,0,0,1,1,1,1},
                {1,1,1,1,0,0,0,0,0,0,0,1,1,1,1},
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
        }).checkGameState());
    }

    @Test
    @DisplayName("test game.checkGameState(...) win combo south-west <-> north-east ")
    void test_method_combo_south_west__north_east() {
        GamePowerUnlimited g = getGame(new int[][]{
                /*00*/{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                /*01*/{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                /*02*/{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                /*03*/{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                /*04*/{1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1},
                /*05*/{1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1},
                /*06*/{1, 1, 1, 1, 3, 3, 0, 2, 0, 0, 0, 1, 1, 1, 1},
                /*07*/{1, 1, 1, 1, 3, 3, 2, 2, 0, 0, 0, 1, 1, 1, 1},
                /*08*/{1, 1, 1, 1, 3, 2, 3, 3, 0, 0, 0, 1, 1, 1, 1},
                /*09*/{1, 1, 1, 1, 2, 2, 3, 3, 0, 0, 0, 1, 1, 1, 1},
                /*10*/{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                /*11*/{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                /*12*/{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                /*13*/{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
        });

        // to south-west
        g.setLastPosXY(6, 7);
        Assertions.assertTrue(g.isPieceAt(6, 7, PieceType.RED));
        Assertions.assertTrue(g.isPieceAt(6, 6, PieceType.EMPTY));
        Assertions.assertTrue(g.isPieceAt(7, 7, PieceType.RED));

        // to north-east
        g.setLastPosXY(9, 4);
        Assertions.assertTrue(g.isPieceAt(9, 4, PieceType.RED));
        Assertions.assertTrue(g.isPieceAt(8, 4, PieceType.YELLOW));
        Assertions.assertTrue(g.isPieceAt(9, 5, PieceType.RED));
        Assertions.assertEquals(GameState.WIN, g.checkGameState());

        // from mid to south-west/north-east
        g.setLastPosXY(8, 5);
        Assertions.assertTrue(g.isPieceAt(8, 5, PieceType.RED));
        Assertions.assertTrue(g.isPieceAt(9, 5, PieceType.RED));
        Assertions.assertTrue(g.isPieceAt(8, 4, PieceType.YELLOW));
        Assertions.assertEquals(GameState.WIN, g.checkGameState());
    }

    @Test
    @DisplayName("test game.checkGameState(...) win combo north-west <-> south-east ")
    void test_method_combo_north_west__south_east() {
        GamePowerUnlimited g = getGame(new int[][]{
                /*00*/{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                /*01*/{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                /*02*/{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                /*03*/{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                /*04*/{1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1},
                /*05*/{1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1},
                /*06*/{1, 1, 1, 1, 3, 2, 3, 3, 0, 0, 0, 1, 1, 1, 1},
                /*07*/{1, 1, 1, 1, 3, 3, 2, 2, 3, 0, 0, 1, 1, 1, 1},
                /*08*/{1, 1, 1, 1, 3, 2, 3, 3, 2, 3, 0, 1, 1, 1, 1},
                /*09*/{1, 1, 1, 1, 2, 3, 3, 3, 2, 3, 3, 1, 1, 1, 1},
                /*10*/{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                /*11*/{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                /*12*/{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                /*13*/{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
        });
        // to south-east
        g.setLastPosXY(6, 7);
        Assertions.assertTrue(g.isPieceAt(6, 7, PieceType.YELLOW));
        Assertions.assertTrue(g.isPieceAt(5, 7, PieceType.EMPTY));
        Assertions.assertTrue(g.isPieceAt(6, 8, PieceType.EMPTY));
        Assertions.assertTrue(g.isPieceAt(6, 6, PieceType.YELLOW));
        Assertions.assertTrue(g.isPieceAt(7, 7, PieceType.RED));
        Assertions.assertEquals(GameState.WIN, g.checkGameState());

        // to north-west
        g.setLastPosXY(9, 10);
        Assertions.assertTrue(g.isPieceAt(8, 10, PieceType.EMPTY));
        Assertions.assertTrue(g.isPieceAt(9, 9, PieceType.YELLOW));
        Assertions.assertTrue(g.isPieceAt(10, 10, PieceType.BARRIER));
        Assertions.assertEquals(GameState.WIN, g.checkGameState());

        // from mid to north-west/south-east
        g.setLastPosXY(8, 9);
        Assertions.assertTrue(g.isPieceAt(8, 9, PieceType.YELLOW));
        Assertions.assertTrue(g.isPieceAt(8, 10, PieceType.EMPTY));
        Assertions.assertTrue(g.isPieceAt(9, 9, PieceType.YELLOW));
        Assertions.assertTrue(g.isPieceAt(7, 9, PieceType.EMPTY));
        Assertions.assertEquals(GameState.WIN, g.checkGameState());
    }

    @Test
    @DisplayName("test game.checkGameState(...) win combo north <-> south ")
    void test_method_combo_north__south() {
        GamePowerUnlimited g = getGame(new int[][]{
                /*00*/{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                /*01*/{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                /*02*/{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                /*03*/{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                /*04*/{1, 1, 1, 1, 0, 0, 3, 0, 0, 0, 0, 1, 1, 1, 1},
                /*05*/{1, 1, 1, 1, 0, 0, 2, 0, 0, 0, 0, 1, 1, 1, 1},
                /*06*/{1, 1, 1, 1, 0, 0, 2, 0, 0, 0, 0, 1, 1, 1, 1},
                /*07*/{1, 1, 1, 1, 0, 0, 2, 0, 0, 0, 0, 1, 1, 1, 1},
                /*08*/{1, 1, 1, 1, 0, 3, 2, 2, 3, 0, 0, 1, 1, 1, 1},
                /*09*/{1, 1, 1, 1, 0, 3, 3, 3, 2, 0, 0, 1, 1, 1, 1},
                /*10*/{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                /*11*/{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                /*12*/{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                /*13*/{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
        });
        // to north
        g.setLastPosXY(8, 6);
        Assertions.assertTrue(g.isPieceAt(8, 6, PieceType.RED));
        Assertions.assertTrue(g.isPieceAt(8, 7, PieceType.RED));
        Assertions.assertTrue(g.isPieceAt(8, 5, PieceType.YELLOW));
        Assertions.assertEquals(GameState.WIN, g.checkGameState());

        // to south
        g.setLastPosXY(5, 6);
        Assertions.assertTrue(g.isPieceAt(5, 6, PieceType.RED));
        Assertions.assertTrue(g.isPieceAt(4, 6, PieceType.YELLOW));
        Assertions.assertTrue(g.isPieceAt(5, 7, PieceType.EMPTY));
        Assertions.assertTrue(g.isPieceAt(5, 5, PieceType.EMPTY));
        Assertions.assertEquals(GameState.WIN, g.checkGameState());

        // from mid to north/south
        g.setLastPosXY(6, 6);
        Assertions.assertTrue(g.isPieceAt(6, 6, PieceType.RED));
        Assertions.assertTrue(g.isPieceAt(6, 5, PieceType.EMPTY));
        Assertions.assertTrue(g.isPieceAt(6, 7, PieceType.EMPTY));
        Assertions.assertEquals(GameState.WIN, g.checkGameState());
    }

    @Test
    @DisplayName("test game.checkGameState(...) win combo west <-> east ")
    void test_method_combo_west__east() {
        GamePowerUnlimited g = getGame(new int[][]{
                /*00*/{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                /*01*/{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                /*02*/{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                /*03*/{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                /*04*/{1, 1, 1, 1, 0, 0, 3, 0, 0, 0, 0, 1, 1, 1, 1},
                /*05*/{1, 1, 1, 1, 0, 0, 2, 0, 0, 0, 0, 1, 1, 1, 1},
                /*06*/{1, 1, 1, 1, 0, 0, 3, 0, 0, 0, 0, 1, 1, 1, 1},
                /*07*/{1, 1, 1, 1, 0, 2, 2, 2, 2, 0, 0, 1, 1, 1, 1},
                /*08*/{1, 1, 1, 1, 0, 3, 3, 2, 3, 0, 0, 1, 1, 1, 1},
                /*09*/{1, 1, 1, 1, 0, 3, 3, 3, 2, 0, 0, 1, 1, 1, 1},
                /*10*/{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                /*11*/{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                /*12*/{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                /*13*/{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
        });
        // to east
        g.setLastPosXY(7, 5);
        Assertions.assertTrue(g.isPieceAt(7,5, PieceType.RED));
        Assertions.assertTrue(g.isPieceAt(6,5, PieceType.EMPTY));
        Assertions.assertTrue(g.isPieceAt(8,5, PieceType.YELLOW));
        Assertions.assertEquals(GameState.WIN, g.checkGameState());

        // to west
        g.setLastPosXY(7, 8);
        Assertions.assertTrue(g.isPieceAt(7,8, PieceType.RED));
        Assertions.assertTrue(g.isPieceAt(6,8, PieceType.EMPTY));
        Assertions.assertTrue(g.isPieceAt(8,8, PieceType.YELLOW));
        Assertions.assertEquals(GameState.WIN, g.checkGameState());

        // from mid to west/east
        g.setLastPosXY(7, 7);
        Assertions.assertTrue(g.isPieceAt(7,7, PieceType.RED));
        Assertions.assertTrue(g.isPieceAt(6,7, PieceType.EMPTY));
        Assertions.assertTrue(g.isPieceAt(8,7, PieceType.RED));
        Assertions.assertEquals(GameState.WIN, g.checkGameState());
    }
    @Test
    @DisplayName("test game.checkGameState(...) equals to PLAY")
    void test_method_checkGameState_play() {
        //System.out.println(Arrays.toString(game.getComboToBeCancelled().get(0)));
        Assertions.assertEquals(GameState.PLAY, getGame(new int[][] {
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                {1,1,1,1,0,0,0,0,0,0,0,1,1,1,1},
                {1,1,1,1,0,0,0,0,0,0,0,1,1,1,1},
                {1,1,1,1,0,0,0,0,0,0,0,1,1,1,1},
                {1,1,1,1,0,0,0,0,0,0,0,1,1,1,1},
                {1,1,1,1,0,0,0,0,0,0,0,1,1,1,1},
                {1,1,1,1,0,0,0,0,0,0,0,1,1,1,1},
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
        }).checkGameState());
    }

    @Test
    @DisplayName("test game.checkGameState(...) equals to TIE")
    void test_method_checkGameState_tie() {
        //System.out.println(Arrays.toString(game.getComboToBeCancelled().get(0)));
        GamePowerUnlimited g = getGame(new int[][]{
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 0, 3, 2, 3, 2, 3, 2, 1, 1, 1, 1},
                {1, 1, 1, 1, 3, 2, 3, 3, 3, 2, 3, 1, 1, 1, 1},
                {1, 1, 1, 1, 3, 3, 3, 2, 2, 3, 2, 1, 1, 1, 1},
                {1, 1, 1, 1, 3, 2, 3, 3, 3, 2, 3, 1, 1, 1, 1},
                {1, 1, 1, 1, 2, 3, 2, 3, 3, 3, 2, 1, 1, 1, 1},
                {1, 1, 1, 1, 3, 2, 3, 2, 3, 2, 3, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
        });
        Assertions.assertEquals(GameState.PLAY, g.checkGameState());
        g.insertPieces(1, PieceType.RED);
        Assertions.assertEquals(GameState.TIE, g.checkGameState());
    }

    private static GamePowerUnlimited getGame(int[][] fake_data) {
        GamePowerUnlimited game = new GamePowerUnlimited(4, 6, 7, true);
        game.setGrid(fake_data);
        return game;
    }

}
