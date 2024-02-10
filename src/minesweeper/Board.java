/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package minesweeper;

/**
 * TODO: Specification
 */
// public class Board {
    
//     // TODO: Abstraction function, rep invariant, rep exposure, thread safety
    
//     // TODO: Specify, test, and implement in problem 2
    
// }
import java.util.Random;

public class Board {
    private Square[][] grid;
    private int width;
    private int height;
    
    public class Square {
        private boolean isBomb;
        private boolean isFlagged;
        private boolean isDug;
        private int adjacentMines;

        public Square() {
            this.isBomb = false;
            this.isFlagged = false;
            this.isDug = false;
            this.adjacentMines = 0;
        }

        public boolean isBomb() {
            return isBomb;
        }

        public void setBomb(boolean bomb) {
            isBomb = bomb;
        }

        public boolean isFlagged() {
            return isFlagged;
        }

        public void setFlagged(boolean flagged) {
            isFlagged = flagged;
        }

        public boolean isDug() {
            return isDug;
        }

        public void setDug(boolean dug) {
            isDug = dug;
        }

        public int getAdjacentMines() {
            return adjacentMines;
        }

        public void setAdjacentMines(int adjacentMines) {
            this.adjacentMines = adjacentMines;
        }
    }

    public Board(int width, int height) {
        this.width = width;
        this.height = height;
        grid = new Square[height][width];
        initializeBoard();
        placeBombs(width,height);
        calculateAdjacencyCounts();
    }

    private void initializeBoard() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                grid[y][x] = new Square();
            }
        }
    }

    private void placeBombs(int sizeX, int sizeY) {
        Random random = new Random();
        for (int y = 0; y < sizeY; y++) {
        for (int x = 0; x < sizeX; x++) {
            if (random.nextDouble() < 0.25) {
                grid[y][x].setBomb(true);
            }
        }
    }
    }
    public int getWidth(){
        return width;
    }
    public int getHeight(){
        return height;
    }
    public Square getSquareAt(int x, int y){
        if (x >= 0 && x < height && y >= 0 && y < width) {
            return grid[x][y];
        } else {
            throw new IndexOutOfBoundsException("Coordinates out of board bounds.");
        }
    }

    public void calculateAdjacencyCounts() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (!grid[y][x].isBomb()) {
                    int count = 0;
                    for (int dy = -1; dy <= 1; dy++) {
                        for (int dx = -1; dx <= 1; dx++) {
                            int nx = x + dx;
                            int ny = y + dy;
                            if (isWithinBounds(nx, ny) && grid[ny][nx].isBomb()) {
                                count++;
                            }
                        }
                    }
                    grid[y][x].setAdjacentMines(count);
                }
            }
        }
    }
    
    public class BoomException extends RuntimeException {
        public BoomException(String message) {
            super(message);
        }
    }

    public synchronized boolean dig(int x, int y) {
        boolean hitBomb = false;
        if (isWithinBounds(x, y) && !grid[y][x].isDug() && !grid[y][x].isFlagged()) {
            if (grid[y][x].isBomb()) {
                
                hitBomb = true;
                
                grid[y][x].setBomb(false);
            }
            
            grid[y][x].setDug(true);
            if (grid[y][x].getAdjacentMines() == 0) {
                
                for (int dy = -1; dy <= 1; dy++) {
                    for (int dx = -1; dx <= 1; dx++) {
                        int nx = x + dx;
                        int ny = y + dy;
                        if (isWithinBounds(nx, ny) && !grid[ny][nx].isDug()) {
                            dig(nx, ny);
                        }
                    }
                }
            }
        }
        return hitBomb;
    }

    public synchronized void flag(int x, int y) {
        if (isWithinBounds(x, y) && !grid[y][x].isDug()) {
            grid[y][x].setFlagged(!grid[y][x].isFlagged());
        }
    }

    private boolean isWithinBounds(int x, int y) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Square square = grid[y][x];
                if (square.isDug()) {
                    if (square.isBomb()) {
                        builder.append("* ");
                    } else {
                        builder.append(square.getAdjacentMines() + " ");
                    }
                } else if (square.isFlagged()) {
                    builder.append("F ");
                } else {
                    builder.append("- ");
                }
            }
            builder.append("\n");
        }
        return builder.toString();
    }
}
