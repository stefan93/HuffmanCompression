/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hofman;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Stack;

/**
 *
 * @author step
 */
public final class Tree {

    private Node root;
    private LinkedList<Node> freqs = new LinkedList();
    private final String text;
    private HashMap<Character, String> code = new HashMap<>();
    Stack<Character> stack = new Stack();

    public Tree(String t) {
        this.text = t;
        init();
    }

    private void init() {
        calcFreqs();
        makeTree();
        getCode();
    }

    private void makeTree() {
        while (freqs.size() > 1) {
            for (int i = 0; i < freqs.size() - 1; i++) {
                Node first = freqs.get(i);
                Node second = freqs.get(i + 1);
                Node n = new Node(first.getCount() + second.getCount(), first, second);
                freqs.add(freqs.indexOf(first), n);
                freqs.remove(first);
                freqs.remove(second);
            }
        }
        this.root = freqs.getFirst();
    }

    private void calcFreqs() {
        for (Character ch : this.text.toCharArray()) {
            Node dummy = new Node(1, ch);
            if (!freqs.contains(dummy)) {
                orderedAdd(dummy);
            } else {
                Node target = freqs.get(freqs.indexOf(dummy));
                dummy.setCount(target.getCount() + 1);
                freqs.remove(target);
                orderedAdd(dummy);
            }
        }
    }

    public BitSet compress() {
        BitSet compressed = new BitSet();
        int cnt = 0;
        for (Character ch : text.toCharArray()) {
            String charCode = code.get(ch);
            for (Character bit : charCode.toCharArray()) {
                compressed.set(cnt, (bit != '0'));
                cnt++;
            }
        }
        
        return compressed;
    }

    public String compressDebug() {
        String compressed = "";
        for (Character ch : text.toCharArray()) {
            compressed += code.get(ch);
        }

        return compressed;
    }

    public String decompress(String compText) {
        String decompText = "";
        Node temp = new Node(root);
        for (Character ch : compText.toCharArray()) {
            if (ch == '0') {
                temp = temp.getLeft();
            } else if (ch == '1') {
                temp = temp.getRight();
            }
            if (temp.isLeaf()) {
                decompText += temp.getCh();
                temp = new Node(root);
            }
        }
        return decompText;
    }

    public String decompress(ArrayList<Byte> bytes) {
        Node temp = new Node(root);
        String decompText = "";
        for (Byte aByte : bytes) {
            for (int i = 0; i < 8; i++) {
                int bit = aByte & (1 << i);
                if (bit == 0) {
                    temp = temp.getLeft();
                } else if (bit > 0) {
                    temp = temp.getRight();
                }
                if (temp.isLeaf()) {
                    decompText += temp.getCh();
                    temp = new Node(root);
                }
            }
        }
        return decompText;
    }

    private void orderedAdd(Node dummy) {
        if (freqs.isEmpty()) {
            freqs.add(dummy);
        } else if (freqs.get(0).getCount() > dummy.getCount()) {
            freqs.add(0, dummy);
        } else if (freqs.get(freqs.size() - 1).getCount() < dummy.getCount()) {
            freqs.add(freqs.size(), dummy);
        } else {
            int i = 0;
            while (freqs.get(i).getCount() < dummy.getCount()) {
                i++;
            }
            freqs.add(i, dummy);
        }
    }

    public void printFreqs() {
        for (Node freq : freqs) {
            System.out.println(freq.getCh() + ":" + freq.getCount());
        }
    }

    private void _printPreOrder(Node root) {
        if (root.isLeaf()) {
            System.out.println("Leaf: " + root.getCh());
        } else {
            _printPreOrder(root.getLeft());
            System.out.println("Node: " + root.getCount());
            _printPreOrder(root.getRight());
        }
    }

    void printTree() {
        System.out.println(root.getCount());
        _printTree(root, 0, 0);
    }

    void printPreOrder() {
        _printPreOrder(this.root);
    }

    private void _printTree(Node root, int d, int n) {
        if (root.isLeaf()) {
            return;
        }
        System.out.println(root.getLeft().getCount());
        System.out.println(root.getRight().getCount());
        _printTree(root.getLeft(), d, n);
        _printTree(root.getRight(), d, n);
    }

    private void getCode() {
        recGetCode(root);
    }

    private void recGetCode(Node root) {
        if (root.isLeaf()) {
            code.put(root.getCh(), getStringStack(stack));

            return;
        }
        stack.push('0');
        recGetCode(root.getLeft());
        stack.pop();
        stack.push('1');
        recGetCode(root.getRight());
        stack.pop();

    }

    private String getStringStack(Stack<Character> stack) {
        String s = "";
        for (Character character : stack) {
            s += character;
        }
        return s;
    }
}
