package sport.hackerrank;

import java.util.*;

/**
 * JourneyToMoon tries to solve problem "Journey To The Moon" on hacker rank.
 * https://www.hackerrank.com/challenges/journey-to-the-moon/problem
 */
public class JourneyToMoon {

    private static long dfs(int i, List<List<Integer>> adjacencyList, Set<Integer> visited) {
        Stack<Integer> nodes = new Stack<>();
        visited.add(i);
        nodes.push(i);
        int elements = 1;
        while (!nodes.empty()) {
            int top = nodes.pop();
            List<Integer> connectedFromTopElement = adjacencyList.get(top);
            for (int k : connectedFromTopElement) {
                if (!visited.contains(k)) {
                    visited.add(k);
                    nodes.push(k);
                    ++elements;
                }
            }
        }
        return elements;
    }

    private static long journeyToMoon(int n, int[][] astronauts) {

        List<List<Integer>> adjacencyList = new ArrayList<>(n);
        for (int i = 0; i < n; ++i) {
            adjacencyList.add(new ArrayList<Integer>());
        }

        for (int i = 0; i < astronauts.length; ++i) {
            adjacencyList.get(astronauts[i][0]).add(astronauts[i][1]);
            adjacencyList.get(astronauts[i][1]).add(astronauts[i][0]);
        }

        Set<Integer> visited = new HashSet<>();
        List<Long> numAstronauts = new ArrayList<>();

        long totalSum = 0;
        for (int i = 0; i < n; ++i) {
            if (!visited.contains(i)) {
                long compatriots = dfs(i, adjacencyList, visited);
                numAstronauts.add(compatriots);
                totalSum += compatriots;
            }
        }

        long answerSum = 0;
        long prefixSum = 0;
        for (int i = 0; i < numAstronauts.size(); ++i) {
            prefixSum += numAstronauts.get(i);
            answerSum += (numAstronauts.get(i) * (totalSum - prefixSum));
        }

        return answerSum;
    }

    public static void main(String[] args) {
        int[][] astronauts = new int[][]{
                {1, 2},
                {3, 4}
        };
        System.out.println(journeyToMoon(100000, astronauts));
    }
}
