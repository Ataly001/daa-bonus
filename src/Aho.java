
import java.util.*;

public class Aho {
    static class Node {
        Map<Character, Integer> next = new HashMap<>();
        int fail = 0;
        List<Integer> out = new ArrayList<>();
    }

    List<Node> trie = new ArrayList<>();
    List<String> pats;

    public Aho(List<String> pats) {
        this.pats = pats;
        trie.add(new Node());

        for (int i = 0; i < pats.size(); i++) {
            String p = pats.get(i);
            if (p.isEmpty()) throw new IllegalArgumentException("Empty pattern");

            int v = 0;
            for (char c : p.toCharArray()) {
                Integer u = trie.get(v).next.get(c);

                if (u == null) {
                    u = trie.size();
                    trie.get(v).next.put(c, u);
                    trie.add(new Node());
                }
                v = u;
            }
            trie.get(v).out.add(i);
        }

        Queue<Integer> q = new ArrayDeque<>();
        for (int u : trie.get(0).next.values()) q.add(u);

        while (!q.isEmpty()) {
            int v = q.remove();

            for (Map.Entry<Character, Integer> e : trie.get(v).next.entrySet()) {
                char c = e.getKey();
                int u = e.getValue();
                int f = trie.get(v).fail;

                while (f != 0 && !trie.get(f).next.containsKey(c))
                    f = trie.get(f).fail;

                f = trie.get(f).next.getOrDefault(c, 0);
                trie.get(u).fail = f;
                trie.get(u).out.addAll(trie.get(f).out);
                q.add(u);
            }
        }
    }

    public long search(String s) {
        long count = 0;
        int v = 0;

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            while (v != 0 && !trie.get(v).next.containsKey(c))
                v = trie.get(v).fail;

            v = trie.get(v).next.getOrDefault(c, 0);
            count += trie.get(v).out.size();
        }
        return count;
    }

    public List<String> find(String s) {
        List<String> ans = new ArrayList<>();
        int v = 0;

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            while (v != 0 && !trie.get(v).next.containsKey(c))
                v = trie.get(v).fail;

            v = trie.get(v).next.getOrDefault(c, 0);

            for (int id : trie.get(v).out) {
                ans.add(pats.get(id) + " at " +
                        (i - pats.get(id).length() + 1));
            }
        }
        return ans;
    }
}
