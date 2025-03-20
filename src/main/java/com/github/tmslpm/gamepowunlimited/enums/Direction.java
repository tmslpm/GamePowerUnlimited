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

package com.github.tmslpm.gamepowunlimited.enums;

import org.jetbrains.annotations.Contract;

/**
 * Direction points cardinal
 * <pre>
 *  N-W  NORTH  N-E
 *
 * WEST    X    EAST
 *
 *  S-W  SOUTH  S-E
 * </pre>
 * @author tmslpm
 */
public enum Direction {
    NORTH(-1, 0){
        @Override
        public Direction opposite() {
            return Direction.SOUTH;
        }
    },

    NORTH_WEST(-1, -1) {
        @Override
        public Direction opposite() {
            return Direction.SOUTH_EAST;
        }
    },

    NORTH_EAST(-1, 1) {
        @Override
        public Direction opposite() {
            return Direction.SOUTH_WEST;
        }
    },

    WEST(0, -1) {
        @Override
        public Direction opposite() {
            return Direction.EAST;
        }
    },

    EAST(0, 1) {
        @Override
        public Direction opposite() {
            return Direction.WEST;
        }
    },

    SOUTH_WEST(1, -1){
        @Override
        public Direction opposite() {
            return Direction.NORTH_EAST;
        }
    },

    SOUTH_EAST(1, 1){
        @Override
        public Direction opposite() {
            return Direction.NORTH_WEST;
        }
    },

    SOUTH(1, 0) {
        @Override
        public Direction opposite() {
            return Direction.NORTH;
        }
    };

    private final int x;
    private final int y;

    @Contract(pure = true)
    Direction(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Direction opposite() {
        throw new RuntimeException("Not implemented");
    }
}