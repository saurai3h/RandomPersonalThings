package sport.leetcode;

import java.util.Arrays;

/** Solution to https://leetcode.com/problems/3sum-closest/ */
public class ThreeSumClosest {

    public int threeSumClosest(int[] nums, int target) {
        Arrays.sort(nums);
        int minimumDifference = Integer.MAX_VALUE;
        int answer = 0;
        int n = nums.length;
        for (int i = 0; i < n ; ++i) {
            int j = i + 1;
            int k = n - 1;
            while (j < k) {
                int sum = nums[i] + nums[j] + nums[k];
                if (sum >= target) {
                    if (sum - target <= minimumDifference) {
                        minimumDifference = sum - target;
                        answer = sum;
                    }
                    --k;
                } else {
                    if (target - sum <= minimumDifference) {
                        minimumDifference = target - sum;
                        answer = sum;
                    }
                    ++j;
                }
            }
        }
        return answer;
    }

    public static void main(String[] args) {
        System.out.println(new ThreeSumClosest().threeSumClosest(new int[]{-1,2,1,-4}, 1));
    }
}
