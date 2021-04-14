package sport.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/** Solution to https://leetcode.com/problems/3sum/ */
public class ThreeSum {

    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> solution = new ArrayList<>();

        Arrays.sort(nums);

        int n = nums.length;
        for (int i = 0 ; i < n ; ++i) {
            if (i > 0 && nums[i] == nums[i-1]) {
                // Will get duplicate solutions so ignore.
                continue;
            }
            int j = i + 1;
            int k = n - 1;
            while (j < k) {
                if (nums[i] + nums[j] + nums[k] > 0) {
                    --k;
                } else if (nums[i] + nums[j] + nums[k] < 0) {
                    ++j;
                } else {
                    // found a solution.
                    List<Integer> oneAnswer = new ArrayList<>();
                    oneAnswer.add(nums[i]);
                    oneAnswer.add(nums[j]);
                    oneAnswer.add(nums[k]);
                    solution.add(oneAnswer);

                    ++j;
                    --k;

                    // Pass through duplicates.
                    while(j < k && nums[j] == nums[j - 1]) {
                        ++j;
                    }
                    // Pass through duplicates.
                    while(j < k && nums[k] == nums[k + 1]) {
                        --k;
                    }
                }
            }
        }
        return solution;
    }

    public static void main(String[] args) {
        System.out.println(new ThreeSum().threeSum(new int[]{-1,0,1,2,-1,-4}));
        // -4, -1, -1, 0, 1, 2
    }
}
