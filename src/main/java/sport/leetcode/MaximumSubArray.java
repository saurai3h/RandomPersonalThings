package sport.leetcode;

/**
 * MaximumSubArray tries to solve problem "Maximum SubArray" on leetcode. https://leetcode.com/problems/maximum-subarray
 */
public class MaximumSubArray {

    private static int maxSubArray(int[] nums) {
        int answer = Integer.MIN_VALUE;
        int runningMax = 0;
        for (int num : nums) {
            runningMax = Math.max(num, num + runningMax);
            answer = Math.max(answer, runningMax);
        }
        return answer;
    }

    public static void main(String[] args) {
        System.out.println(maxSubArray(new int[]{-2, 1, -3, 4, -1, 2, 1, -5, 4}));
    }
}
