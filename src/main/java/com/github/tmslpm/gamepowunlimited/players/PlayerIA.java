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
     * @param maxY - the maximum length for the axe Y
     * @return int position Y generated with precision or not
     */
    @Override
    public int getPosition(int maxY) {
        return PlayerIA.getRandomPosition(gameProcess);
    }

    public static int getRandomPosition(GamePowerUnlimited game) {
        int lastPosY = game.getLastPositionPieceAddedY() - game.getComboLength();
        //int precision = this.getScore() * 2;
        final int minAxeY = 1;
        final int maxAxeY = game.getYLength();
        return getRandomPositionFromOtherPos(lastPosY, minAxeY, maxAxeY, 3);
    }

    protected static int getRandomPositionFromOtherPos(int posAxe, int minPosAxe, int maxPosAxe, int range) {
        return RNG.nextInt(
                Math.max(posAxe - range, minPosAxe + 1),
                Math.min(posAxe + range, maxPosAxe + 1)
        );
    }
}