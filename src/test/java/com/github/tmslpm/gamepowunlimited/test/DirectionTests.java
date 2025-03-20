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

import com.github.tmslpm.gamepowunlimited.enums.Direction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DirectionTests {

    @Test
    @DisplayName("test move to Direction.NORTH")
    void testMoveToNorth() {
        int[][] grid = getGrid();
        int posX = 1;
        int posY = 1;
        Assertions.assertEquals(grid[posX][posY], 5);
        posX = posX + Direction.NORTH.getX();
        posY = posY + Direction.NORTH.getY();
        Assertions.assertEquals(grid[posX][posY], 2);
    }

    @Test
    @DisplayName("test move to Direction.NORTH_EAST")
    void testMoveToNorthEast() {
        int[][] grid = getGrid();
        int posX = 1;
        int posY = 1;
        Assertions.assertEquals(grid[posX][posY], 5);
        posX = posX + Direction.NORTH_EAST.getX();
        posY = posY + Direction.NORTH_EAST.getY();
        Assertions.assertEquals(grid[posX][posY], 3);
    }

    @Test
    @DisplayName("test move to Direction.NORTH_WEST")
    void testMoveToNorthWest() {
        int[][] grid = getGrid();
        int posX = 1;
        int posY = 1;
        Assertions.assertEquals(grid[posX][posY], 5);
        posX = posX + Direction.NORTH_WEST.getX();
        posY = posY + Direction.NORTH_WEST.getY();
        Assertions.assertEquals(grid[posX][posY], 1);
    }

    @Test
    @DisplayName("test move to Direction.SOUTH")
    void testMoveToSouth() {
        int[][] grid = getGrid();
        int posX = 1;
        int posY = 1;
        Assertions.assertEquals(grid[posX][posY], 5);
        posX = posX + Direction.SOUTH.getX();
        posY = posY + Direction.SOUTH.getY();
        Assertions.assertEquals(grid[posX][posY], 8);
    }

    @Test
    @DisplayName("test move to Direction.SOUTH_EAST")
    void testMoveToSouthEast() {
        int[][] grid = getGrid();
        int posX = 1;
        int posY = 1;
        Assertions.assertEquals(grid[posX][posY], 5);
        posX = posX + Direction.SOUTH_EAST.getX();
        posY = posY + Direction.SOUTH_EAST.getY();
        Assertions.assertEquals(grid[posX][posY], 9);
    }

    @Test
    @DisplayName("test move to Direction.SOUTH_WEST")
    void testMoveToSouthWest() {
        int[][] grid = getGrid();
        int posX = 1;
        int posY = 1;
        Assertions.assertEquals(grid[posX][posY], 5);
        posX = posX + Direction.SOUTH_WEST.getX();
        posY = posY + Direction.SOUTH_WEST.getY();
        Assertions.assertEquals(grid[posX][posY], 7);
    }

    @Test
    @DisplayName("test move to Direction.WEST")
    void testMoveToWest() {
        int[][] grid = getGrid();
        int posX = 1;
        int posY = 1;
        Assertions.assertEquals(grid[posX][posY], 5);
        posX = posX + Direction.WEST.getX();
        posY = posY + Direction.WEST.getY();
        Assertions.assertEquals(grid[posX][posY], 4);
    }

    @Test
    @DisplayName("test move to Direction.EAST")
    void testMoveToEast() {
        int[][] grid = getGrid();
        int posX = 1;
        int posY = 1;
        Assertions.assertEquals(grid[posX][posY], 5);
        posX = posX + Direction.EAST.getX();
        posY = posY + Direction.EAST.getY();
        Assertions.assertEquals(grid[posX][posY], 6);
    }

    private static int[][] getGrid() {
        return new int[][]{
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
    }

}
