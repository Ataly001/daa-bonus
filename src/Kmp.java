
import java.util.*;
public class Kmp {
    public static long search(String s, String p) {
        int[] lps = new int[p.length()];

        for (int i = 1, j = 0; i < p.length();) {
            if (p.charAt(i) == p.charAt(j))
                lps[i++] = ++j;
            else if (j > 0)
                j = lps[j - 1];
            else
                i++;
        }

        long count = 0;

        for (int i = 0, j = 0; i < s.length();) {
            if (s.charAt(i) == p.charAt(j)) {
                i++;
                j++;
            } else if (j > 0) {
                j = lps[j - 1];
            } else {
                i++;
            }

            if (j == p.length()) {
                count++;
                j = lps[j - 1];
            }
        }
        return count;
    }

    public static long searchAll(String s, List<String> pats) {
        long count = 0;

        for (String p : pats)
            count += search(s, p);

        return count;
    }
}
