package com.github.tmslpm.gamepowunlimited;

import com.github.tmslpm.gamepowunlimited.base.GamePowUnlimited;
import com.github.tmslpm.gamepowunlimited.base.GameProcess;
import com.github.tmslpm.gamepowunlimited.enums.AnsiColor;
import com.github.tmslpm.gamepowunlimited.enums.GameState;

public class GameProcessCLI extends GameProcess {
    private final String selectorStr;

    public GameProcessCLI(GamePowUnlimited game) {
        super(game);
        StringBuilder selectorDisplayYPso = new StringBuilder().append("|");
        for (int i = 0; i < game.getYLength(); i++) selectorDisplayYPso.append(i + 1).append('|');
        this.selectorStr = selectorDisplayYPso + "\n" + "-".repeat(selectorDisplayYPso.length());
    }

    @Override
    public void onRender(GameState gamestate) {
        System.out.println(this.selectorStr + this.game.gridToString() + "\n" + "_".repeat(30));
    }

    @Override
    public void onPlay() {}

    @Override
    public void onTie() {
        AnsiColor.YELLOW.println("tie");
    }

    @Override
    public void onLose() {
        AnsiColor.RED.println("lose");
    }

    @Override
    public void onWin() {
        AnsiColor.GREEN.println("win");
    }

    @Override
    public boolean stopGame() {
        System.out.println("leave the game? ? (y/n)");
        return true;
        //return !Main.SCANNER.next().toLowerCase().contains("y");
    }
}