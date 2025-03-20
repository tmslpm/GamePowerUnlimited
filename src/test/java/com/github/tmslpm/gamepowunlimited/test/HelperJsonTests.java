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

import com.github.tmslpm.gamepowunlimited.GameProcessAction;
import com.github.tmslpm.gamepowunlimited.GamePowerUnlimited;
import com.github.tmslpm.gamepowunlimited.enums.GameState;
import com.github.tmslpm.gamepowunlimited.enums.PieceType;
import com.github.tmslpm.gamepowunlimited.players.Player;
import com.github.tmslpm.gamepowunlimited.players.PlayerIA;
import com.github.tmslpm.gamepowunlimited.utils.HelperJSON;
import org.junit.jupiter.api.*;

import java.nio.file.Files;
import java.nio.file.Path;

public class HelperJsonTests {
    public static final String TEST_PATH_OUT = "out/unit.test.json";
    public static final String TEST_PATH_OUT_1 = "out/unit.test_group.json";

    @Test
    @DisplayName("Test method: HelperJSON.toFile, write the file with fake data")
    void test_helper_json_write_file() {
        // test creation basic object
        HelperJSON.toFile(TEST_PATH_OUT, new ObjectTest());
        Path of = Path.of(TEST_PATH_OUT);
        Assertions.assertTrue(Files.exists(of));

        HelperJSON.toFile(TEST_PATH_OUT_1, new ObjectTestGroup());
        Assertions.assertTrue(Files.exists(of));
    }

    @Test
    @DisplayName("Test method: HelperJSON.fromFile, read the json from file with json data")
    void test_helper_json_read_file() {
        ObjectTest result = HelperJSON.fromFile(TEST_PATH_OUT, ObjectTest.class);
        // test bad value
        Assertions.assertNotEquals("bad_thomas", result.first_name);
        Assertions.assertNotEquals(" lapoire", result.last_name);
        // test good value
        Assertions.assertEquals("thomas", result.first_name);
        Assertions.assertEquals("lapoire", result.last_name);
        Assertions.assertEquals("lapoire", result.last_name);
        Assertions.assertEquals(0, result.test_int);
        Assertions.assertTrue(result.test_boolean);

        // test
        ObjectTestGroup result1 = HelperJSON.fromFile(TEST_PATH_OUT_1, ObjectTestGroup.class);
        Assertions.assertEquals("group", result1.groupName);
    }

    @Test
    @DisplayName("Test method: HelperJSON.toFile, test save game process")
    void test_helper_json_save_game() {
        GamePowerUnlimited game = getGame();
        game.setPlayers(new Player[] {new Player(PieceType.YELLOW), new PlayerIA(game, PieceType.RED)});
        HelperJSON.toFile(TEST_PATH_OUT, game);
        GameProcessAction result = HelperJSON.fromFile(TEST_PATH_OUT, GameProcessAction.class);
        Assertions.assertNotNull(result);
    }

    @Test
    @DisplayName("Test method: HelperJSON.toFile, test cycle save/read game process")
    void test_cycle_json_save_read_game() {
        // first start
        {
            // init empty game
            final GamePowerUnlimited game = new GamePowerUnlimited(4, 6, 7, true);

            // add 2 players
            game.setPlayers(new Player[] {
                    new Player(PieceType.YELLOW),
                    new PlayerIA(game, PieceType.RED)
            });

            // Save json to file
            HelperJSON.toFile(TEST_PATH_OUT, game);
            Assertions.assertTrue(Files.exists(Path.of(TEST_PATH_OUT)));
            Assertions.assertEquals(0, game.getCounterRound());
            // try to get data
            GameProcessAction result = HelperJSON.fromFile(TEST_PATH_OUT, GameProcessAction.class);
            Assertions.assertNotNull(result);
        }

        // deserialize object, play ply 1 & 2 and serialize
        for (int i = 1; i < 4; i++){
            // Create instance from JSON
            final GameProcessAction game = HelperJSON.fromFile(TEST_PATH_OUT, GameProcessAction.class);
            Assertions.assertNotNull(game);
            Assertions.assertNotNull(game.getCurrentPlayer());
            Assertions.assertNotNull(game.getGrid());
            PieceType plyType = game.getCurrentPlayer().getType();
            Assertions.assertTrue(PieceType.YELLOW.equals(plyType) || PieceType.RED.equals(plyType));

            // ply 1 play
            Assertions.assertTrue(game.insertPieces(i, PieceType.YELLOW));
            Assertions.assertEquals(game.checkGameState(), GameState.PLAY);
            // ply 2 play
            Assertions.assertTrue(game.insertPieces(i + 1, PieceType.RED));
            Assertions.assertEquals(game.checkGameState(), GameState.PLAY);
            // Save
            HelperJSON.toFile(TEST_PATH_OUT, game);
        }

        // check WIN/PLAY
        {
            // Create instance from JSON
            final GameProcessAction game = HelperJSON.fromFile(TEST_PATH_OUT, GameProcessAction.class);
            Assertions.assertNotNull(game);
            Assertions.assertNotNull(game.getCurrentPlayer());
            Assertions.assertNotNull(game.getGrid());
            PieceType plyType = game.getCurrentPlayer().getType();
            Assertions.assertTrue(PieceType.YELLOW.equals(plyType) || PieceType.RED.equals(plyType));
            Assertions.assertTrue(game.insertPieces(1, PieceType.YELLOW));
            Assertions.assertTrue(game.insertPieces(1, PieceType.YELLOW));
            Assertions.assertTrue(game.insertPieces(1, PieceType.YELLOW));
            Assertions.assertEquals(GameState.WIN, game.checkGameState());
            game.resetGame();
            Assertions.assertEquals(GameState.PLAY, game.checkGameState());
            // Save
            HelperJSON.toFile(TEST_PATH_OUT, game);
        }

        // check tie/play
        {
            final GameProcessAction game = HelperJSON.fromFile(TEST_PATH_OUT, GameProcessAction.class);
            Assertions.assertNotNull(game);
            Assertions.assertNotNull(game.getCurrentPlayer());
            Assertions.assertNotNull(game.getGrid());
            PieceType plyType = game.getCurrentPlayer().getType();
            Assertions.assertTrue(PieceType.YELLOW.equals(plyType) || PieceType.RED.equals(plyType));
            Assertions.assertEquals(GameState.PLAY, game.checkGameState());
            for (int y = 1; y < 8; y++) {
                while (game.insertPieces(y, PieceType.BARRIER)) {
                    // insert max
                }
            }
            Assertions.assertEquals(GameState.TIE, game.checkGameState());
        }
    }

    private static GamePowerUnlimited getGame() {
        GamePowerUnlimited game = new GamePowerUnlimited(4, 6, 7, true);
        game.setGrid(new int[][] {
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                {1,1,1,1,0,0,0,0,0,0,0,1,1,1,1},
                {1,1,1,1,0,0,0,0,0,0,0,1,1,1,1},
                {1,1,1,1,0,0,0,0,0,0,0,1,1,1,1},
                {1,1,1,1,0,0,2,0,0,0,0,1,1,1,1},
                {1,1,1,1,0,2,3,0,0,0,0,1,1,1,1},
                {1,1,1,1,2,3,3,0,0,0,0,1,1,1,1},
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
        });


        return game;

    }

    private static class ObjectTest {

        private final String first_name;
        private final String last_name;
        private final int test_int = 0;
        private final boolean test_boolean = true;


        ObjectTest() {
            this.first_name = "thomas";
            this.last_name = "lapoire";
        }

    }

    private static class ObjectTestGroup {
        private final String groupName = "group";
        private final Player[] objectTests = { new Player(PieceType.RED),  new Player(PieceType.YELLOW)};
        ObjectTestGroup() {}
    }


}

