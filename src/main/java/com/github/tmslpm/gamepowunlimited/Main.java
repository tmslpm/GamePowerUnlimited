package com.github.tmslpm.gamepowunlimited;


import java.util.Scanner;

public class Main {
    public final static Scanner SCANNER = new Scanner(System.in);

    public static void main(String[] args) {
        boolean action_github = false;
        int position = 0;

        for (String arg : args) {
            switch (arg) {
                case "action_github":
                    action_github = true;
                    break;
                case "position_1":
                    position = 1;
                    break;
                case "position_2":
                    position = 2;
                    break;
                case "position_3":
                    position = 3;
                    break;
                case "position_4":
                    position = 4;
                    break;
                case "position_5":
                    position = 5;
                    break;
                case "position_6":
                    position = 6;
                    break;
                case "position_7":
                    position = 7;
                    break;
            }
        }

        if (action_github) {
            if (position > 0) GameProcessAction.start(position);
            else throw new IllegalArgumentException("bad position, excepted 1 to 7");
            System.out.println("Success called main, type: Github Action (position play: " + position + ")");
        } else {
            GameProcessCLI.start();
            System.out.println("Success called main, type: CLI");
        }

    }


}