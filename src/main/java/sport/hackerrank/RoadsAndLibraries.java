package sport.hackerrank;

import java.util.*;

/**
 * RoadsAndLibraries tries to solve problem "Roads And Libraries" on hacker rank. https://www.hackerrank.com/challenges/torque-and-development/problem
 */
public class RoadsAndLibraries {

    private static int dfs(int i, List<List<Integer>> adjacencyList, Set<Integer> visited) {
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

    private static long roadsAndLibraries(int n, int libaryCost, int roadCost, int[][] cityEdges) {

        List<List<Integer>> adjacencyList = new ArrayList<>(n);
        for (int i = 0; i < n; ++i) {
            adjacencyList.add(new ArrayList<Integer>());
        }

        for (int i = 0; i < cityEdges.length; ++i) {
            adjacencyList.get(cityEdges[i][0] - 1).add(cityEdges[i][1] - 1);
            adjacencyList.get(cityEdges[i][1] - 1).add(cityEdges[i][0] - 1);
        }

        Set<Integer> visited = new HashSet<>();
        long sum = 0L;

        for (int i = 0; i < n; ++i) {
            if (!visited.contains(i)) {
                int vertices = dfs(i, adjacencyList, visited);
                sum += Math.min((long)vertices * libaryCost, libaryCost + ((long)vertices-1) * roadCost);
            }
        }
        return sum;
    }

    public static void main(String[] args) {
        int[][] cityEdges = new int[][]{
                {1, 2},
                {1, 3},
                {1, 4}
        };
        System.out.println(roadsAndLibraries(5, 6, 1, cityEdges));
    }
}
