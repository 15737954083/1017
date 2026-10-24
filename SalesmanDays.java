/*
 * 题目：推销员最多能工作几天（相邻两天不能选同一个城镇，城镇 i 最多选 c[i] 次）
 *
 * 思路（贪心 / 数学）：
 * 设所有次数之和为 sum，最大的次数为 max，其余城镇次数之和为 rest = sum - max。
 * 1. 如果 max <= rest + 1：
 *    可以把次数最多的城镇和其它城镇交替排开，所有次数都能用完，答案 = sum。
 * 2. 如果 max > rest + 1：
 *    次数最多的城镇太多，必须用其它城镇把它隔开。
 *    rest 个"隔板"最多隔出 rest + 1 个位置放最多的城镇，
 *    排成 M x M x ... x M，答案 = rest + (rest + 1) = 2 * rest + 1。
 *
 * 例：7 2 3，max = 7，rest = 5，7 > 6，答案 = 2 * 5 + 1 = 11。
 * 例：2 2 2 2，max = 2，rest = 6，2 <= 7，答案 = 8。
 *
 * 复杂度 O(N)。
 *
 * 提交时把整个文件（包括 import）粘贴进去，替换编辑器里的全部内容。
 */

import java.util.Scanner;

class Solution
{
    public static void main(String args[] )
    {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        long sum = 0, max = 0;
        for (int i = 0; i < n; i++) {
            long c = sc.nextLong();
            sum += c;
            max = Math.max(max, c);
        }
        long rest = sum - max;
        long ans = (max <= rest + 1) ? sum : 2 * rest + 1;
        System.out.println(ans);
    }
}
