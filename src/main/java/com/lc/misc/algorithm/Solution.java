package com.lc.misc.algorithm;

class Solution {

    private int[] nums = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9};
    private int xLength = 9;
    private int yLength = 9;

    public boolean isOk = false;

    public static void main(String[] args) {
        Solution solution = new Solution();
        char[][] board = new char[][]{{'5', '3', '.', '.', '7', '.', '.', '.', '.'}, {
                '6', '.', '.', '1', '9', '5', '.', '.', '.'}, {'.', '9', '8', '.', '.', '.', '.', '6', '.'}, {
                '8', '.', '.', '.', '6', '.', '.', '.', '3'}, {'4', '.', '.', '8', '.', '3', '.', '.', '1'}, {
                '7', '.', '.', '.', '2', '.', '.', '.', '6'}, {'.', '6', '.', '.', '.', '.', '2', '8', '.'}, {
                '.', '.', '.', '4', '1', '9', '.', '.', '5'}, {'.', '.', '.', '.', '8', '.', '.', '7', '9'}};
        solution.solveSudoku(board);

        System.out.println(board);
    }

    public void solveSudoku(char[][] board) {

    }

    public boolean checkXY(char[][] board, int x, int y) {
        int[] checkX = new int[9];
        for (int i = 0; i < 9; i++) {
            if (board[x][i] != '.') {
                if (++checkX[board[x][i] - '0'] > 1) {
                    return false;
                }
            }
        }
        int[] checkY = new int[9];
        for (int i = 0; i < 9; i++) {
            if (board[i][y] != '.') {
                if (++checkY[board[i][y] - '0'] > 1) {
                    return false;
                }
            }
        }

        int[] checkXY = new int[9];
        int xBox = (x / 3) * 3;
        int yBox = (y / 3) * 3;
        for (int i = xBox; i < xBox + 3; i++) {
            for (int j = yBox; j < yBox + 3; j++) {
                if (board[i][j] != '.') {
                    if (++checkXY[board[i][j] - '0'] > 1) {
                        return false;
                    }
                }
            }
        }
        return true;
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
 * [['5','3','.','.','7','.','.','.','.'],
 * ['6','.','.','1','9','5','.','.','.'],
 * ['.','9','8','.','.','.','.','6','.'],['8','.','.','.','6','.','.','.','3'],['4','.','.','8','.','3','.','.','1'],
 * ['7','.','.','.','2','.','.','.','6'],['.','6','.','.','.','.','2','8','.'],['.','.','.','4','1','9','.','.','5'],['.','.','.','.','8','.','.','7','9']]
 **/