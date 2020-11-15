package com.lc.misc.algorithm;

import java.util.Arrays;

/**
 * 数独  https://leetcode-cn.com/problems/sudoku-solver/
 */
class Solution {
    public int count = 0;

    public static void main(String[] args) {
        Solution solution = new Solution();
        char[][] board = new char[][]{{'5', '3', '.', '.', '7', '.', '.', '.', '.'}, {
                '6', '.', '.', '1', '9', '5', '.', '.', '.'}, {'.', '9', '8', '.', '.', '.', '.', '6', '.'}, {
                '8', '.', '.', '.', '6', '.', '.', '.', '3'}, {'4', '.', '.', '8', '.', '3', '.', '.', '1'}, {
                '7', '.', '.', '.', '2', '.', '.', '.', '6'}, {'.', '6', '.', '.', '.', '.', '2', '8', '.'}, {
                '.', '.', '.', '4', '1', '9', '.', '.', '5'}, {'.', '.', '.', '.', '8', '.', '.', '7', '9'}};
        solution.solveSudoku(board);

        Arrays.stream(board).forEach(item -> System.out.println(Arrays.toString(item)));

        System.out.println(solution.count);
    }

    public void solveSudoku(char[][] board) {
        dfs(board, 0);
    }

    public boolean checkXY(char[][] board, int x, int y) {
        int[] checkX = new int[10];
        for (int i = 0; i < 9; i++) {
            if (board[x][i] != '.') {
                if (++checkX[board[x][i] - '0'] > 1) {
                    return false;
                }
            }
        }
        int[] checkY = new int[10];
        for (int i = 0; i < 9; i++) {
            if (board[i][y] != '.') {
                if (++checkY[board[i][y] - '0'] > 1) {
                    return false;
                }
            }
        }

        int[] checkXY = new int[10];
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

    public boolean dfs(char[][] board, int deepth) {
        count++;
        int i = deepth / 9;
        int j = deepth % 9;
        if (deepth >= 81) {
            return true;
        }
        int z = 1;
        if (board[i][j] == '.') {
            while (z <= 9) {
                board[i][j] = (z + "").charAt(0);
                if (checkXY(board, i, j)) {
                    if (dfs(board, ++deepth))
                        return true;
                    deepth--;
                }
                z++;
            }
            board[i][j] = '.';
            return false;
        } else {
            if (dfs(board, ++deepth)) {
                return true;
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