package com.lc.misc.algorithm;

class Solution {

    private int[] nums = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9};
    private int xLength = 9;
    private int yLength = 9;

    public boolean isOk = false;

    public void solveSudoku(char[][] board) {

    }

    public boolean checkXY(char[][] board, int x, int y) {
        for (int i = 0; i < 9; i++) {
            //board[i][0];
        }
        return false;
    }

    public boolean dfs(char[][] board, int x, int y) {
        for (int i = x; i < xLength; i++) {
            for (int j = y; j < yLength; j++) {
                int z = 1;
                if (board[i][j] == '.') {
                    while (z <= 9) {
                        if (checkXY(board, i, j)) {
                            board[i][j] = (z + "").charAt(0);
                            dfs(board, i, j);
                        }
                        z++;
                    }
                }
            }
        }
        return false;
    }
}

/**
 * [["5","3",".",".","7",".",".",".","."],
 * ["6",".",".","1","9","5",".",".","."],
 * [".","9","8",".",".",".",".","6","."],["8",".",".",".","6",".",".",".","3"],["4",".",".","8",".","3",".",".","1"],
 * ["7",".",".",".","2",".",".",".","6"],[".","6",".",".",".",".","2","8","."],[".",".",".","4","1","9",".",".","5"],[".",".",".",".","8",".",".","7","9"]]
 **/