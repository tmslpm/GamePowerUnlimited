package com.github.tmslpm.gamepowunlimited.enums;
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