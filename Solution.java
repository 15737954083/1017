// Sample code to read input and write output:

/*
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Solution
{
	public static void main(String args[] )
	{
		// Use either of these methods for input

		//BufferedReader
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String name1 = br.readLine();			// Read input from STDIN
		System.out.println("Hello " + name1);	// Write output to STDOUT

		//Scanner
		Scanner s = new Scanner(System.in);
		String name2 = s.nextLine();			// Read input from STDIN
		System.out.println("Hello " + name2);	// Write output to STDOUT
	}
}
*/

// Warning: Printing unwanted or ill-formatted data to output will cause the test cases to fail

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;
class Solution
{
	public static void main(String args[] )
	{
		// Write your code here
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			java.io.StreamTokenizer st = new java.io.StreamTokenizer(br);
			st.resetSyntax();
			st.wordChars('0', '9');
			st.wordChars('-', '-');
			st.whitespaceChars(0, ' ');

			st.nextToken();
			int n = Integer.parseInt(st.sval);
			int[] state = new int[n];
			for (int i = 0; i < n; i++) {
				st.nextToken();
				state[i] = Integer.parseInt(st.sval);
			}
			st.nextToken();
			int m = Integer.parseInt(st.sval);
			long[] dist = new long[m];
			for (int i = 0; i < m; i++) {
				st.nextToken();
				dist[i] = Long.parseLong(st.sval);
			}

			int len = Math.min(n, m);
			int first = -1, last = -1;
			for (int i = 0; i < len; i++) {
				if (state[i] == 1) {
					if (first == -1) first = i;
					last = i;
				}
			}
			if (first == -1) {
				System.out.println(0);
				return;
			}

			long total = 0;
			// OFF systems before the first ON system: chain them to it
			total += dist[first] - dist[0];
			// OFF systems after the last ON system: chain them to it
			total += dist[len - 1] - dist[last];

			// OFF systems between two ON systems: connect the whole span
			// and drop the single largest gap (split point between both sides)
			int prev = first;
			for (int i = first + 1; i <= last; i++) {
				if (state[i] == 1) {
					if (i - prev > 1) {
						long maxGap = 0;
						for (int j = prev; j < i; j++) {
							maxGap = Math.max(maxGap, dist[j + 1] - dist[j]);
						}
						total += (dist[i] - dist[prev]) - maxGap;
					}
					prev = i;
				}
			}

			System.out.println(total);
		} catch (Exception e) {
			System.out.println(0);
		}
	}
}
