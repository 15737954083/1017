/*
 * 题目：找出能建最大房子的两栋相邻房屋编号
 *
 * 思路：
 * 1. 所有房子在一条直线上，"空地"就是按位置排序后相邻两栋房子之间的距离。
 * 2. 把房子按位置排序，求相邻两栋的最大间距即可。
 * 3. 位置 P 互不相同且 0 <= P < 10^6，所以不用 sort，直接开一个
 *    长度 10^6 的数组 house[P] = H（计数排序思想），从小到大扫描即 O(N + 10^6)。
 * 4. 并列时要求"离参考点最近"，从小位置往大扫描、只在严格更大时更新，
 *    保留的就是位置最小的那一对。
 * 5. 输出的两个房屋编号要升序：min(H1,H2) max(H1,H2)。
 *
 * N 最大 10^6，用 Scanner 会很慢，这里用 DataInputStream 手写快速读入。
 *
 * 提交时把下面的 class Solution 整段粘贴进去即可。
 */

import java.io.DataInputStream;
import java.io.IOException;
import java.util.Arrays;

class Solution
{
    public static void main(String args[] )
    {
        FastReader in = new FastReader();
        int n = in.nextInt();
        in.nextInt(); // val，固定为 2

        final int MAX_P = 1000000;
        int[] house = new int[MAX_P];
        Arrays.fill(house, -1);         // -1 表示该位置没有房子
        for (int i = 0; i < n; i++) {
            int h = in.nextInt();
            int p = in.nextInt();
            house[p] = h;
        }

        int prevPos = -1, prevHouse = -1;
        int bestGap = -1, ansA = 0, ansB = 0;
        for (int p = 0; p < MAX_P; p++) {
            if (house[p] == -1) continue;
            if (prevPos != -1) {
                int gap = p - prevPos;
                if (gap > bestGap) {    // 严格大于：并列时保留离参考点最近的
                    bestGap = gap;
                    ansA = prevHouse;
                    ansB = house[p];
                }
            }
            prevPos = p;
            prevHouse = house[p];
        }

        System.out.println(Math.min(ansA, ansB) + " " + Math.max(ansA, ansB));
    }

    // 快速读入整数
    static class FastReader {
        private final DataInputStream in = new DataInputStream(System.in);
        private final byte[] buf = new byte[1 << 16];
        private int len = 0, ptr = 0;

        // 内部捕获 IOException，调用方（main）不需要写 throws
        private int read() {
            if (ptr == len) {
                try {
                    len = in.read(buf, 0, buf.length);
                } catch (IOException e) {
                    len = -1;
                }
                ptr = 0;
                if (len <= 0) return -1;
            }
            return buf[ptr++];
        }

        int nextInt() {
            int c = read();
            while (c != -1 && c != '-' && (c < '0' || c > '9')) c = read();
            boolean neg = false;
            if (c == '-') { neg = true; c = read(); }
            int r = 0;
            while (c >= '0' && c <= '9') {
                r = r * 10 + (c - '0');
                c = read();
            }
            return neg ? -r : r;
        }
    }
}
