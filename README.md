# Aho-Corasick Algorithm

This is my DAA bonus assignment written in Java. The main goal is to implement the Aho-Corasick algorithm for searching multiple patterns in a text and compare its performance with KMP.

I also tested both algorithms with different numbers of patterns and text lengths to see how their execution times change.

## 1. Project Structure

```text
daa-bonus/
├── src/
│   ├── Aho.java
│   ├── Kmp.java
│   ├── Test.java
│   └── Main.java
├── results.csv
├── README.md
└── .gitignore
```

- **Aho.java** — builds the Trie, creates failure links using BFS and searches for patterns.
- **Kmp.java** — implements KMP and searches for each pattern separately.
- **Test.java** — checks correctness and measures execution time.
- **Main.java** — runs the tests and experiments.
- **results.csv** — contains the experimental results.

## 2. How Aho-Corasick Works

Aho-Corasick is used to find multiple patterns in one text.

First, the algorithm builds a Trie. A Trie is a tree where every node represents a character. If several patterns have the same prefix, they share the same nodes.

After that, the algorithm creates failure links using BFS. These links help the algorithm find another possible matching path when the current character does not match.

Each node also stores information about patterns that end at that node. My implementation copies the output lists from failure nodes, so it can find patterns that are suffixes of other patterns.

During searching, the algorithm reads the text character by character. When it reaches a node with matching patterns, it counts them. The `find()` method can also return the patterns and their starting positions.

The main advantage is that Aho-Corasick does not need to scan the whole text separately for every pattern.

## 3. KMP Algorithm

KMP is another string searching algorithm. It uses an LPS array to avoid checking the same characters again after a mismatch.

KMP works well when we need to find one pattern in a text.

However, if we have many patterns, KMP needs to search the text separately for each one.

In this project, I used KMP to compare its execution time with Aho-Corasick.

## 4. Testing

I tested both algorithms using:

**Number of patterns:**
- 10
- 100
- 1000

**Text lengths:**
- 1000
- 10000
- 100000 characters

This gives 9 different test cases.

For every test case, I ran the experiment 5 times and calculated the average execution time using `System.nanoTime()`.

The test data was generated randomly using characters from `a` to `d`. Pattern lengths were between 4 and 8 characters.

I used the same text and patterns for both algorithms to make the comparison fair.

Before the experiments, the program also checks whether Aho-Corasick and KMP find the same number of matches. It includes tests with known patterns and random strings.

All correctness tests passed.

## 5. Experimental Results

These are the results from my computer. All times are measured in milliseconds (ms).

| Patterns | Text Length | Aho Build | Aho Search | Aho Total | KMP Total |
|---|---|---|---|---|---|
| 10 | 1,000 | 0.0563 | 0.2921 | 0.3484 | 0.1955 |
| 10 | 10,000 | 0.0548 | 1.1228 | 1.1776 | 0.5183 |
| 10 | 100,000 | 0.0635 | 2.2583 | 2.3218 | 4.4031 |
| 100 | 1,000 | 0.3558 | 0.0221 | 0.3779 | 0.4033 |
| 100 | 10,000 | 0.3726 | 0.2240 | 0.5966 | 4.3973 |
| 100 | 100,000 | 0.3486 | 2.1210 | 2.4696 | 44.4940 |
| 1000 | 1,000 | 1.5601 | 0.0302 | 1.5902 | 4.0236 |
| 1000 | 10,000 | 0.5467 | 0.2873 | 0.8340 | 42.5230 |
| 1000 | 100,000 | 0.8053 | 2.7763 | 3.5815 | 427.6355 |

**What the columns mean:**

- **Aho Build:** Time needed to build the Trie and failure links.
- **Aho Search:** Time needed to search the text.
- **Aho Total:** Building time + searching time.
- **KMP Total:** Time needed to search all patterns separately, including LPS construction.

The full results, including the number of matches, are saved in `results.csv`.

## 6. Performance Comparison

From the results, I noticed that Aho-Corasick becomes much faster when the number of patterns increases.

**10 patterns:**

For small inputs, KMP was faster in my tests. For example, with 10,000 characters, KMP took 0.5183 ms, while Aho-Corasick took 1.1776 ms.

This happens because Aho-Corasick needs extra time to build the Trie and failure links.

**100 patterns:**

When the text length was 100,000 characters, Aho-Corasick took 2.4696 ms, while KMP took 44.4940 ms.

Aho-Corasick was about 18 times faster.

**1000 patterns:**

The difference became even bigger.

For 100,000 characters:
- Aho-Corasick: 3.5815 ms
- KMP: 427.6355 ms

Aho-Corasick was about 119 times faster in this experiment.

KMP becomes slower because it scans the text separately for every pattern. Aho-Corasick builds one Trie and searches for all patterns together.

The execution times are not perfectly consistent because Java performance can change depending on JVM optimization and computer load.

## 7. Time and Space Complexity

Let:

- **N** = length of the text
- **P** = total length of all patterns
- **K** = number of patterns
- **Z** = total number of matches

### Aho-Corasick

For the standard algorithm with efficient output links and a fixed alphabet:

- Trie and failure link construction: **O(P)**
- Searching and reporting matches: **O(N + Z)**
- Space complexity: **O(P)**

My implementation uses output lists copied from failure nodes. Because of this, building the structure and storing outputs can take up to **O(P × K)** time and space in the worst case.

The search still processes the text using the Trie and failure links, and reporting all matches takes O(N + Z) time.

### KMP

For one pattern:

- Time complexity: **O(N + M)**, where M is the pattern length.
- Space complexity: **O(M)** for the LPS array.

For K patterns:

- Total time complexity: **O(K × N + P)**
- Auxiliary space: **O(Mmax)** when patterns are processed separately.

KMP is efficient for one pattern, but it has to repeat the search for multiple patterns.

## 8. Advantages and Limitations

### Aho-Corasick

Advantages:
- Searches multiple patterns in one pass.
- Shares common prefixes using a Trie.
- Failure links help avoid restarting the search.
- Works well when there are many patterns.

Limitations:
- More complicated than KMP.
- Needs extra memory for the Trie and links.
- Building the Trie takes additional time.

### KMP

Advantages:
- Simple to implement and understand.
- Efficient for searching one pattern.
- Uses less memory.

Limitations:
- Searches the text separately for each pattern.
- Can become much slower when the number of patterns increases.

## 9. Conclusion

In this assignment, I implemented Aho-Corasick and KMP in Java and compared their performance using 9 test cases.

The results showed that KMP can be faster for a small number of patterns, but Aho-Corasick performs much better when we search for hundreds or thousands of patterns.

In the largest experiment, Aho-Corasick was about 119 times faster than KMP.

I learned how a Trie stores multiple patterns, how failure links work, and why Aho-Corasick is useful for multiple-pattern searching.

## 10. How to Run

I used Java in IntelliJ IDEA.

To run the project from the terminal:

```bash
javac src/*.java
java -cp src Main
```

The program will run correctness tests, perform all 9 experiments, print the results and save them to `results.csv`.

The results may be different on another computer because execution time depends on hardware and JVM performance.
