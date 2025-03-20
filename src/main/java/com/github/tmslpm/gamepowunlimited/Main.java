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


import org.jetbrains.annotations.NotNull;

import java.util.Scanner;

public class Main {
  public final static Scanner SCANNER = new Scanner(System.in);

  public static void main(String @NotNull [] args) {
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
      if (position > 0) {
        GameProcessAction.start(position);
      } else {
        throw new IllegalArgumentException("bad position, excepted 1 to 7");
      }
      System.out.println("Success called main, type: Github Action (position play: " + position + ")");
    } else {
      GameProcessCLI.start();
      System.out.println("Success called main, type: CLI");
    }

  }

}
