/*
 * 题目：最少用几条直线可以覆盖所有上车点
 *
 * 思路（贪心）：
 * 1. 先对坐标去重（重复的点不需要额外的线）。
 * 2. 每一轮找出"经过最多剩余点"的那条直线：
 *    - 枚举每个点 p 作为基准点，计算其它点相对 p 的斜率；
 *    - 斜率用约分后的 (dx, dy) 表示，避免浮点误差；
 *    - 同一斜率出现次数最多的那一组 + p 本身 = 过 p 的最佳直线上的点数。
 * 3. 把这条线上的点全部删掉，答案 +1，继续下一轮。
 * 4. 优化：如果当前最佳直线只能覆盖 <= 2 个点，说明任意三点都不共线，
 *    剩下的点两两连一条线即可，直接加 ceil(剩余点数 / 2)。
 *
 * 复杂度：每轮 O(n^2)，n <= 1000，能通过。
 * 注：这个问题在一般情况下是 NP 难的，这类考试的标准答案就是上面的贪心。
 *
 * 提交时把下面的 class Solution 整段粘贴进去即可。
 */

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

class Solution
{
    public static void main(String args[]) throws Exception
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) sb.append(line).append(' ');
        StringTokenizer st = new StringTokenizer(sb.toString());
        if (!st.hasMoreTokens()) { System.out.println(0); return; }

        int n = Integer.parseInt(st.nextToken());
        if (st.hasMoreTokens()) st.nextToken(); // numCoord，固定为 2

        // 读入并去重
        Set<Long> seen = new HashSet<>();
        List<int[]> pts = new ArrayList<>();
        for (int i = 0; i < n && st.hasMoreTokens(); i++) {
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            long key = (long) x * 100000L + y;
            if (seen.add(key)) pts.add(new int[]{x, y});
        }

        System.out.println(minLines(pts));
    }

    static int minLines(List<int[]> pts) {
        int ans = 0;
        while (!pts.isEmpty()) {
            int size = pts.size();
            if (size <= 2) { ans++; break; }

            int best = 0, bestIdx = 0, bestDx = 0, bestDy = 0;
            for (int i = 0; i < size; i++) {
                Map<Long, Integer> cnt = new HashMap<>();
                int[] p = pts.get(i);
                for (int j = 0; j < size; j++) {
                    if (i == j) continue;
                    int[] s = slope(p, pts.get(j));
                    long key = (long) s[0] * 10000L + s[1];
                    int c = cnt.merge(key, 1, Integer::sum);
                    if (c + 1 > best) {
                        best = c + 1;
                        bestIdx = i;
                        bestDx = s[0];
                        bestDy = s[1];
                    }
                }
            }

            // 没有三点共线了：剩下的点两两一条线
            if (best <= 2) {
                ans += (size + 1) / 2;
                break;
            }

            // 删除最佳直线上的所有点
            int[] p = pts.get(bestIdx);
            List<int[]> rest = new ArrayList<>();
            for (int[] q : pts) {
                if (q == p) continue;
                int[] s = slope(p, q);
                if (s[0] != bestDx || s[1] != bestDy) rest.add(q);
            }
            pts = rest;
            ans++;
        }
        return ans;
    }

    // 返回约分并统一符号后的方向 (dx, dy)
    static int[] slope(int[] a, int[] b) {
        int dx = b[0] - a[0];
        int dy = b[1] - a[1];
        if (dx == 0) return new int[]{0, 1};   // 竖线
        if (dy == 0) return new int[]{1, 0};   // 横线
        int g = gcd(Math.abs(dx), Math.abs(dy));
        dx /= g;
        dy /= g;
        if (dx < 0) { dx = -dx; dy = -dy; }    // 统一让 dx > 0
        return new int[]{dx, dy};
    }

    static int gcd(int a, int b) {
        return b == 0 ? a : gcd(b, a % b);
    }
}
