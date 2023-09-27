package com.github.tmslpm.gamepowunlimited.test;

import com.github.tmslpm.gamepowunlimited.GamePowerUnlimited;
import com.github.tmslpm.gamepowunlimited.utils.GameToImage;
import org.junit.jupiter.api.*;

class GenerateGameToImageTests {

    @Test
    void succeedingTest() {
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

        GameToImage.generate(g, 64);
    }

    private static GamePowerUnlimited getGame(int[][] fake_data) {
        GamePowerUnlimited game = new GamePowerUnlimited(4, 6, 7, true);
        game.setGrid(fake_data);
        return game;
    }
}