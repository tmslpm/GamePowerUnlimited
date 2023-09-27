package com.github.tmslpm.gamepowunlimited.players;

import com.github.tmslpm.gamepowunlimited.enums.PieceType;

public class Player {
    private final int id;
    private final PieceType type;
    private int score;

    public Player(PieceType type, int id) {
        this.type = type;
        this.id = id;
        this.score = 0;
    }

    public int getPosition() {
        int posY = 0;
        int precision = score * 2;
        for (int x = 0; x < precision; x++) {
            for (int y = 0; y < precision; y++) {

            }
        }
        return posY;
    }

    private int getId() {
        return id;
    }

    public PieceType getType() {
        return this.type;
    }
}