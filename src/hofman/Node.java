/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hofman;

/**
 *
 * @author step
 */
public final class Node {
    private Integer count;
    private Character ch;
    private Node left;
    private Node right;
    public Node(Integer n, Character c) {
        setCount(n);
        setCh(c);
    }
    public Node(Integer n, Node l, Node r) {
        setCount(n);
        setLeft(l);
        setRight(r);
    }
    public Node(Integer n, Character c, Node l, Node r) {
        setCount(n);
        setCh(c);
        setLeft(l);
        setRight(r);
    }
    public Node(Node n) {
        setCh(n.getCh());
        setCount(n.getCount());
        setLeft(n.getLeft());
        setRight(n.getRight());
    }
    public boolean isLeaf() {
        return this.ch != null;
    }
    @Override
    public int hashCode() {
        return isLeaf() ? getCh().hashCode() : super.hashCode();
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Node other = (Node) obj;
        return isLeaf() && other.isLeaf() ? other.getCh().equals(this.getCh()) : super.equals(obj);
    }

    public Integer getCount() {
        return count;
    }

    public Character getCh() {
        return ch;
    }

    public Node getLeft() {
        return left;
    }

    public Node getRight() {
        return right;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public void setCh(Character ch) {
        this.ch = ch;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public void setRight(Node right) {
        this.right = right;
    }
}
