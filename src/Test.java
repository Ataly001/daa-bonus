import java.util.*;
import java.io.*;

public class Test {
    static Random rnd = new Random(42);

    static String random(int n) {
        StringBuilder b = new StringBuilder();

        for (int i = 0; i < n; i++)
            b.append((char) ('a' + rnd.nextInt(4)));

        return b.toString();
    }

    static void check() {
        List<String> p = Arrays.asList("he", "she", "his", "hers", "he");
        String s = "ahishershe";

        Aho a = new Aho(p);

        if (a.search(s) != Kmp.searchAll(s, p))
            throw new AssertionError("Count mismatch");

        List<String> found = a.find("ushers");

        if (!found.contains("she at 1") ||
                !found.contains("he at 2") ||
                !found.contains("hers at 2"))
            throw new AssertionError("Positions mismatch");

        for (int k = 0; k < 30; k++) {
            String t = random(80);
            List<String> ps = new ArrayList<>();

            for (int j = 0; j < 12; j++)
                ps.add(random(1 + rnd.nextInt(7)));

            if (new Aho(ps).search(t) != Kmp.searchAll(t, ps))
                throw new AssertionError("Random mismatch");
        }

        System.out.println("Correctness tests passed");
    }

    static void run() throws IOException {
        int[] ns = {1000, 10000, 100000};
        int[] ks = {10, 100, 1000};

        try (PrintWriter w = new PrintWriter("results.csv")) {
            w.println("patterns,text_length,aho_build_ms,aho_search_ms,aho_total_ms,kmp_total_ms,matches");

            System.out.println("patterns,text,aho_build_ms,aho_search_ms,aho_total_ms,kmp_total_ms,matches");

            for (int k : ks) {
                for (int n : ns) {
                    String s = random(n);
                    List<String> ps = new ArrayList<>();

                    for (int j = 0; j < k; j++)
                        ps.add(random(4 + rnd.nextInt(5)));

                    new Aho(ps).search(s);
                    Kmp.searchAll(s, ps);

                    long build = 0;
                    long search = 0;
                    long kmp = 0;
                    long matches = 0;

                    for (int r = 0; r < 5; r++) {
                        long t1 = System.nanoTime();
                        Aho a = new Aho(ps);
                        long t2 = System.nanoTime();

                        long x = a.search(s);
                        long t3 = System.nanoTime();

                        long y = Kmp.searchAll(s, ps);
                        long t4 = System.nanoTime();

                        if (x != y)
                            throw new AssertionError("Mismatch for " + k + ", " + n);

                        build += t2 - t1;
                        search += t3 - t2;
                        kmp += t4 - t3;
                        matches = x;
                    }

                    String line = String.format(
                            Locale.US,
                            "%d,%d,%.4f,%.4f,%.4f,%.4f,%d",
                            k, n,
                            build / 5e6,
                            search / 5e6,
                            (build + search) / 5e6,
                            kmp / 5e6,
                            matches
                    );

                    w.println(line);
                    System.out.println(line);
                }
            }
        }
    }
}
