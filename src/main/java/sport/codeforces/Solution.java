package sport.codeforces;

import java.io.IOException;
import java.util.Scanner;

public final class Solution {

    private static String abbreviate(String word) {
        if (word.length() <= 10) {
            return word;
        } else {
            return word.charAt(0) + String.valueOf(word.length() - 2) + word.charAt(word.length() - 1);
        }
    }

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        while (n > 0) {
            String word = scanner.next();
            System.out.println(abbreviate(word));
            n--;
        }
        scanner.close();
    }
}
