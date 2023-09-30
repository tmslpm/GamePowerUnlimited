package com.github.tmslpm.gamepowunlimited.players;

import com.github.tmslpm.gamepowunlimited.Main;
import com.github.tmslpm.gamepowunlimited.enums.PieceType;

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