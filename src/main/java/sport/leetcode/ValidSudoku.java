package sport.leetcode;

/** Solution to https://leetcode.com/problems/valid-sudoku/ */
public class ValidSudoku {

    public boolean isValidSudoku(char[][] board) {
        for (int i = 0; i < 9; ++i) {
            // Check rows.
            if (isNotValid(board[i][0], board[i][1], board[i][2], board[i][3], board[i][4], board[i][5], board[i][6], board[i][7], board[i][8])) {
                return false;
            }
        }
        for (int i = 0; i < 9; ++i) {
            // Check cols.
            if (isNotValid(board[0][i], board[1][i], board[2][i], board[3][i], board[4][i], board[5][i], board[6][i], board[7][i], board[8][i])) {
                return false;
            }
        }
        int i = 0, j = 0;
        while (i < 9) {
            if (isNotValid(board[i][j], board[i][j + 1], board[i][j + 2], board[i + 1][j], board[i + 1][j + 1], board[i + 1][j + 2], board[i + 2][j], board[i + 2][j + 1], board[i + 2][j + 2])) {
                return false;
            }
            j += 3;
            if (j == 9) {
                j = 0;
                i += 3;
            }
        }
        return true;
    }

    private static boolean isNotValid(char a, char b, char c, char d, char e, char f, char g, char h, char i) {
        boolean[] filled = new boolean[9];
        return checkDigitNotFilled(a, filled) || checkDigitNotFilled(b, filled) || checkDigitNotFilled(c, filled) || checkDigitNotFilled(d, filled) || checkDigitNotFilled(e, filled) || checkDigitNotFilled(f, filled) || checkDigitNotFilled(g, filled) || checkDigitNotFilled(h, filled) || checkDigitNotFilled(i, filled);
    }

    private static boolean checkDigitNotFilled(char a, boolean[] filled) {
        if (a == '.') {
            return false;
        }
        if (a < '1' || a > '9') {
            return true;
        }
        if (filled[a - '1']) {
            return true;
        }
        filled[a - '1'] = true;
        return false;
    }

    public static void main(String[] args) {
        System.out.println(new ValidSudoku().isValidSudoku(new char[][]{
                {'8','3','.','.','7','.','.','.','.'},
                {'6','.','.','1','9','5','.','.','.'},
                {'.','9','8','.','.','.','.','6','.'},
                {'8','.','.','.','6','.','.','.','3'},
                {'4','.','.','8','.','3','.','.','1'},
                {'7','.','.','.','2','.','.','.','6'},
                {'.','6','.','.','.','.','2','8','.'},
                {'.','.','.','4','1','9','.','.','5'},
                {'.','.','.','.','8','.','.','7','9'}
        }));
    }
}
