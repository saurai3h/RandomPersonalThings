package sport.leetcode;

import java.util.*;

public class EvaluateDivision {
    // DO NOT MODIFY THE LIST. IT IS READ ONLY
    public int isMatch(final String A, final String B) {
        int a = A.length();
        int b = B.length();
        if (a == 0) {
            for(int i = 0; i < b; ++i) {
                if(B.charAt(i) != '*')return 0;
            }
            return 1;
        }
        if (b == 0) {
            return 0;
        }

        boolean[][] matches = new boolean[a][b];

        if(B.charAt(0) == A.charAt(0) || B.charAt(0) == '*' || B.charAt(0) == '.') matches[0][0] = true;

        for(int j = 1; j < b; ++j) {
            if (B.charAt(j) == A.charAt(0) || B.charAt(j) == '.') {
                boolean allMatched = true;
                for (int k = 0; k < j; ++k) {
                    if(B.charAt(k) != '*') {
                        allMatched = false;
                        break;
                    }
                }
                if (allMatched) matches[0][j] = true;
            } else if (B.charAt(j) == '*') {
                matches[0][j] = matches[0][j-1];
            }
        }

        for (int i = 1; i < a; ++i) {
            matches[i][0] = B.charAt(0) == '*';
            for (int j = 1; j < b; ++j) {
                if (A.charAt(i) == B.charAt(j) || B.charAt(j) == '.') {
                    matches[i][j] = matches[i-1][j-1];
                } else if (B.charAt(j) == '*') {
                    for (int k = 0; k <= i; ++k) {
                        if (matches[i-k][j-1]) {
                            matches[i][j] = true;
                            break;
                        }
                    }
                }
            }
        }
        return matches[a-1][b-1] ? 1 : 0;
    }

    public static void main(String[] args) {
        System.out.println(new EvaluateDivision().isMatch("ac", "ab*c"));
    }
}
