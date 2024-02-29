import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

class TrieNode {
    Map<Character, TrieNode> children;
    TrieNode fail;
    List<Integer> patterns;

    TrieNode() {
        children = new HashMap<>();
        fail = null;
        patterns = new ArrayList<>();
    }
}

public class AhoCorasick {
    private TrieNode root;
    private List<String> patterns;
    private List<List<Integer>> patternOccurrences;

    public AhoCorasick() {
        root = new TrieNode();
        patterns = new ArrayList<>();
        patternOccurrences = new ArrayList<>();
    }

    public void addPattern(String pattern, int patternIndex) {
        TrieNode node = root;

        for (char c : pattern.toCharArray()) {
            node.children.putIfAbsent(c, new TrieNode());
            node = node.children.get(c);
        }

        node.patterns.add(patternIndex);
    }

    public void buildFailureLinks() {
        Queue<TrieNode> queue = new LinkedList<>();
        for (TrieNode child : root.children.values()) {
            queue.offer(child);
            child.fail = root;
        }

        while (!queue.isEmpty()) {
            TrieNode node = queue.poll();

            for (char c : node.children.keySet()) {
                TrieNode child = node.children.get(c);
                queue.offer(child);

                TrieNode failNode = node.fail;
                while (failNode != null && !failNode.children.containsKey(c)) {
                    failNode = failNode.fail;
                }

                if (failNode != null) {
                    child.fail = failNode.children.get(c);
                } else {
                    child.fail = root;
                }
            }
        }
    }

    public void searchPatterns(String text) {
        TrieNode node = root;

        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);

            while (node != root && !node.children.containsKey(c)) {
                node = node.fail;
            }

            if (node.children.containsKey(c)) {
                node = node.children.get(c);
            }

            for (int patternIndex : node.patterns) {
                patternOccurrences.get(patternIndex).add(i - patterns.get(patternIndex).length() + 1);
            }
        }
    }

    public void printPatternOccurrences() {
        for (int i = 0; i < patterns.size(); i++) {
            System.out.print("Pattern " + (i + 1) + " occurs at positions: ");
            if (patternOccurrences.get(i).isEmpty()) {
                System.out.print("No occurrences");
            } else {
                for (int pos : patternOccurrences.get(i)) {
                    System.out.print((pos + 1) + " "); // Increment position by 1
                }
            }
            System.out.println();
        }
    }



    public static void main(String[] args) {
        AhoCorasick aho = new AhoCorasick();
        int patternCount = 0;
        String sourceFilePath = "C:\\Users\\Ciprian\\Desktop\\GitHub_UVT\\Code_Works\\ADS - seminary\\LabWork_2\\javaPart\\Homework\\src\\source.txt";

        try {
            BufferedReader br = new BufferedReader(new FileReader(sourceFilePath));
            StringBuilder textBuilder = new StringBuilder();
            String line;

            // Read text
            String text = br.readLine();

            // Read pattern count
            patternCount = Integer.parseInt(br.readLine());

            // Read patterns
            for (int i = 0; i < patternCount; i++) {
                String pattern = br.readLine();
                aho.addPattern(pattern, i);
                aho.patterns.add(pattern);
                aho.patternOccurrences.add(new ArrayList<>());
            }

            // Read the remaining text
            while ((line = br.readLine()) != null) {
                textBuilder.append(line);
                textBuilder.append("\n");
            }

            text += textBuilder.toString();

            aho.buildFailureLinks();
            aho.searchPatterns(text);
            aho.printPatternOccurrences();

            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
