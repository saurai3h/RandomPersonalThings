package sport.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FourSum {
    public List<List<Integer>> fourSum(int[] nums, int target) {
        List<List<Integer>> answer = new ArrayList<>();
        if (nums.length < 4) {
            return answer;
        }

        // a + b + c + d = target. distinct numbers.
        Arrays.sort(nums);
        int n = nums.length;
        for (int i = 0; i < n; ++i) {
            if (i > 0 && nums[i] == nums[i-1]) {
                // remove duplicates.
                continue;
            }
            for (int j = i + 1; j < n ; ++j) {
                if (j - 1 > i && nums[j] == nums[j-1]) {
                    // remove duplicates.
                    continue;
                }
                int k = j + 1;
                int l = n - 1;
                while (k < l) {
                    int s = nums[i] + nums[j] + nums[k] + nums[l];
                    if (s > target) {
                        --l;
                    } else if (s < target) {
                        ++k;
                    } else {
                        List<Integer> newSolution = new ArrayList<>();
                        newSolution.add(nums[i]);
                        newSolution.add(nums[j]);
                        newSolution.add(nums[k]);
                        newSolution.add(nums[l]);
                        answer.add(newSolution);
                        ++k;
                        --l;
                        while (k < l && nums[k] == nums[k-1]) {
                            ++k;
                        }
                        while (k < l && nums[l] == nums[l+1]) {
                            --l;
                        }
                    }
                }
            }
        }
        return answer;
    }

    public static void main(String[] args) {
        // -2, -1, 0, 0, 1, 2
        System.out.println(new FourSum().fourSum(new int[]{0,0,0,0}, 0));
    }
}
