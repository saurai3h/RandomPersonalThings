package sport.leetcode;

public class Grumpy {
    public int maxSatisfied(int[] customers, int[] grumpy, int X) {
        if (customers.length == 0) {
            return 0;
        }

        int n = customers.length;
        int[] prefixSum = new int[n];
        int[] prefixSumGrumpy = new int[n];
        for (int i = 0 ; i < n ; ++i) {
            if (i == 0) {
                prefixSum[i] = customers[i];
                prefixSumGrumpy[i] = grumpy[i] == 0 ? customers[i] : 0;
            } else {
                prefixSum[i] = prefixSum[i-1] + customers[i];
                prefixSumGrumpy[i] = prefixSumGrumpy[i-1] + (grumpy[i] == 0 ? customers[i] : 0);
            }
        }
        int answer = prefixSumGrumpy[n-1];
        int maxYet = 0;
        for (int i = X - 1; i < n; ++i) {
            int additionPossible;
            if (i >= X) {
                additionPossible = (prefixSum[i] - prefixSum[i - X]) - (prefixSumGrumpy[i] - prefixSumGrumpy[i - X]);
            } else {
                additionPossible = prefixSum[i] - prefixSumGrumpy[i];
            }

            if (additionPossible > maxYet) {
                maxYet = additionPossible;
            }
        }
        return answer + maxYet;
    }

    public static void main(String[] args) {
        System.out.println(new Grumpy().maxSatisfied(new int[]{1,0,1,2,1,1,7,5}, new int[]{0,1,0,1,0,1,0,1}, 3));
    }
}
