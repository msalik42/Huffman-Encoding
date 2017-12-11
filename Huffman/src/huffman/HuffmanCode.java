package huffman;

import static huffman.HuffmanCode.counter;
import java.util.*;
import javax.swing.table.DefaultTableModel;
 
abstract class HuffmanTree implements Comparable<HuffmanTree> {
    public final int frequency; 
    public HuffmanTree(int freq) { frequency = freq; }
 
    public int compareTo(HuffmanTree tree) {
        return frequency - tree.frequency;
    }
}
 
class HuffmanLeaf extends HuffmanTree {
    public final char value;
    public HuffmanLeaf(int freq, char val) {
        super(freq);
        value = val;
    }
}
 
class HuffmanNode extends HuffmanTree {
    public final HuffmanTree left, right;
 
    public HuffmanNode(HuffmanTree l, HuffmanTree r) {
        super(l.frequency + r.frequency);
        left = l;
        right = r;
    }
}
 
public class HuffmanCode {
    private static Object table1;
    public static HuffmanTree buildTree(int[] charFreqs) {
        PriorityQueue<HuffmanTree> trees = new PriorityQueue<HuffmanTree>();
        for (int i = 0; i < charFreqs.length; i++)
            if (charFreqs[i] > 0)
                trees.offer(new HuffmanLeaf(charFreqs[i], (char)i));
 
        assert trees.size() > 0;
        
        while (trees.size() > 1) {
            
            HuffmanTree a = trees.poll();
            HuffmanTree b = trees.poll();
 
           
            trees.offer(new HuffmanNode(a, b));
        }
        return trees.poll();
    }
    public static char value[] = new char[500];
    public static int frequency[] = new int[500];
    public static String pref[] = new String[500];
    public static int counter = 0;
    public static void printCodes(HuffmanTree tree, StringBuffer prefix) {
        assert tree != null;
        if (tree instanceof HuffmanLeaf) {
            HuffmanLeaf leaf = (HuffmanLeaf)tree;
           
            System.out.println(leaf.value + "\t" + leaf.frequency + "\t" + prefix);
            value[counter] = leaf.value;
            frequency [counter] = leaf.frequency;
            pref[counter] = prefix.toString();
            counter ++;
            
 
        } else if (tree instanceof HuffmanNode) {
            HuffmanNode node = (HuffmanNode)tree;
 
            
            prefix.append('0');
            printCodes(node.left, prefix);
            prefix.deleteCharAt(prefix.length()-1);
 
          
            prefix.append('1');
            printCodes(node.right, prefix);
            prefix.deleteCharAt(prefix.length()-1);
      
        }
    }
    public static void main(String[] args) {
        String test = "this is an example for huffman encoding";
 
      
        int[] charFreqs = new int[256];
        // read each character and record the frequencies
        for (char c : test.toCharArray())
            charFreqs[c]++;
 
      
        HuffmanTree tree = buildTree(charFreqs);
 
        System.out.println("SYMBOL\tWEIGHT\tHUFFMAN CODE");
        printCodes(tree, new StringBuffer());
    }
}