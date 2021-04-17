package sport.leetcode;

import java.util.Arrays;

public class CircleInside {
    public int[] countPoints(int[][] points, int[][] queries) {
        if (queries.length == 0) {
            return new int[]{};
        }
        int[] answer = new int[queries.length];
        for (int i = 0 ; i < queries.length; ++i) {
            int val = 0;
            for (int[] point : points) {
                if (dist(point[0], queries[i][0]) + dist(point[1], queries[i][1]) <= ((long) queries[i][2] * queries[i][2])) {
                    ++val;
                }
            }
            answer[i] = val;
        }
        return answer;
    }

    private static long dist(int x1, int x2) {
        long d = x1 - x2;
        return d * d;
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(new CircleInside().countPoints(new int[][]{{1, 3}, {3, 3}, {5, 3}, {2, 2}}, new int[][]{{2, 3, 1}, {4, 3, 1}, {1, 1, 2}})));
        System.out.println(Arrays.toString(new CircleInside().countPoints(new int[][]{{1,1},{2,2},{3,3},{4,4},{5,5}}, new int[][]{{1,2,2},{2,2,2},{4,3,2},{4,3,3}})));
    }
}
