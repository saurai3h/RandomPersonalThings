package sport.hackerrank;

/**
 * Abbreviation tries to solve problem "Abbreviation" on hacker rank. https://www.hackerrank.com/challenges/abbr/problem
 */
public class Abbreviation {

    private static boolean isSmall(char a) {
        return (a >= 'a' && a <= 'z');
    }

    private static boolean equals(char a, char b) {
        return (a == b) || (a - 'a' + 'A' == b);
    }

    // Complete the abbreviation function below.
    private static String abbreviation(String a, String b) {

        if (a.length() < b.length()) {
            return "NO";
        }

        boolean[][] isPossible = new boolean[a.length() + 1][b.length() + 1];
        isPossible[0][0] = true;
        for (int i = 1; i <= a.length(); ++i) {
            if (isSmall(a.charAt(i-1))) {
                isPossible[i][0] = true;
            } else {
                break;
            }
        }

        for (int i = 1; i <= a.length(); ++i) {
            for (int j = 1; j <= b.length(); ++j) {
                if (equals(a.charAt(i-1), b.charAt(j-1)) && isPossible[i-1][j-1]) {
                    isPossible[i][j] = true;
                } else {
                    if (isSmall(a.charAt(i-1))) {
                        isPossible[i][j] = isPossible[i-1][j];
                    } else {
                        isPossible[i][j] = false;
                    }
                }
            }
        }

        return isPossible[a.length()][b.length()] ? "YES" : "NO";
    }

    public static void main(String[] args) {
        System.out.println(abbreviation("dsadada", ""));
    }
}
