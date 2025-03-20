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
import com.github.tmslpm.gamepowunlimited.GameProcessAction;
import com.github.tmslpm.gamepowunlimited.enums.GameState;
import com.github.tmslpm.gamepowunlimited.enums.PieceType;
import com.github.tmslpm.gamepowunlimited.utils.GameToImage;
import com.github.tmslpm.gamepowunlimited.utils.HelperFile;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

class GenerateGameToImageTests {

    @Test
    void testCreateImageAndFileForTheGame() {
        GamePowerUnlimited g = getGame(new int[][]{
                /*00*/{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                /*01*/{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                /*02*/{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                /*03*/{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                /*04*/{1, 1, 1, 1, 3, 2, 2, 2, 2, 2, 3, 1, 1, 1, 1},
                /*05*/{1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 1, 1, 1, 1},
                /*06*/{1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 1, 1, 1, 1},
                /*07*/{1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 1, 1, 1, 1},
                /*08*/{1, 1, 1, 1, 3, 2, 2, 2, 2, 2, 2, 1, 1, 1, 1},
                /*09*/{1, 1, 1, 1, 3, 2, 2, 2, 2, 2, 3, 1, 1, 1, 1},
                /*10*/{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                /*11*/{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                /*12*/{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                /*13*/{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
        });

        Path pathImage = GameProcessAction.createOutPathImage("out");
        GameToImage.generate(g, 64, pathImage);
        Assertions.assertTrue(Files.exists(pathImage));
    }

    @Test
    void testFileNameGen() {
        //List<Path> list = HelperFile.listSourceFiles(Path.of("out"), "game_to_image*.{png}");
        String[] nameGen = HelperFile.createUniqueName("game_to_image_", 5);
        Assertions.assertEquals(1, HelperFile.createUniqueName("game_to_image_", 1).length);
        Assertions.assertEquals(5, nameGen.length);
        Assertions.assertNotEquals(nameGen[0], nameGen[1]);
        Assertions.assertNotEquals(nameGen[1], nameGen[2]);
        Assertions.assertNotEquals(nameGen[2], nameGen[3]);
        Assertions.assertNotEquals(nameGen[3], nameGen[4]);
        System.out.println(Arrays.toString(nameGen));
    }

    @Test
    void testFileDelete() {
        // GENERATE FAKE FILE
        Path tmpDir = Path.of("out/test_delete");
        int totalFileGeneratedForTheTest = 4;
        try {
            if (Files.notExists(tmpDir)) Files.createDirectories(tmpDir);
            for (int i = 0; i < totalFileGeneratedForTheTest; i++) {
                Path v = tmpDir.resolve("test." + i + ".txt");
                if (Files.notExists(v)) Files.createFile(v);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // CHECK IS CREATED
        for (int i = 0; i < totalFileGeneratedForTheTest; i++)
            Assertions.assertTrue(Files.exists(tmpDir.resolve("test." + i + ".txt")));

        // TEST DELETE METHOD
        HelperFile.deleteFiles(tmpDir, "test*.txt");

        // CHECK IS DELETED
        for (int i = 0; i < totalFileGeneratedForTheTest; i++)
            Assertions.assertTrue(Files.notExists(tmpDir.resolve("test." + i + ".txt")));
    }

    private static GamePowerUnlimited getGame(int[][] fake_data) {
        GamePowerUnlimited game = new GamePowerUnlimited(4, 6, 7, true);
        game.setGrid(fake_data);
        return game;
    }
}