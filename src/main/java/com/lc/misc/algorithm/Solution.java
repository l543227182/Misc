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

    public boolean checkXY(char[][] board, int x, int y, char z) {
        for (int i = 0; i < 9; i++) {
            if (i != y && board[x][i] != '.' && board[x][i] == z) {
                return false;
            }
            if (i != x && board[i][y] != '.' && board[i][y] == z) {
                return false;
            }
            int xBox = (x / 3) * 3 + i / 3;
            int yBox = (y / 3) * 3 + i % 3;
            if (xBox != x && yBox != y && board[xBox][yBox] != '.' && board[xBox][yBox] == z) {
                return false;
            }
        }
        return true;
    }

    public boolean dfs(char[][] board, int deepth) {
        if (deepth >= 81) {
            return true;
        }
        int i = deepth / 9;
        int j = deepth % 9;

        if (board[i][j] == '.') {
            char z = '0';
            while (++z <= '9') {
                board[i][j] = z;
                if (checkXY(board, i, j, z)) {
                    if (dfs(board, deepth + 1)) {
                        return true;
                    }
                }
            }
            board[i][j] = '.';
            return false;
        } else {
            if (dfs(board, deepth + 1)) {
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