class Node{
    int data;
    Node left;
    Node right;

    public Node(int data){
        this.data = data;
        right = null;
        left = null;
    }

    public void insert(int value){

        if(this == null){
            this.data = value;
        } else {
            if(value <= data){
                if(left == null){
                    left = new Node(value);
                } else {
                    left.insert(value);
                }
            } else {
                if (right == null) {
                    right = new Node(value);
                } else {
                    right.insert(value);
                }
            }
        }
    }

    public void printInOrder(){
        if(left != null){
            left.printInOrder();
        }
        System.out.print(" " + data);
        if(right != null){
            right.printInOrder();
        }
    }
}

public class BinaryTree {
    Node root;

    public void insert(int value){
        if (root == null) {
            root = new Node(value);
        }else {
            root.insert(value);
        }
    }

    public void printInOrder(){
        if(root != null){
            root.printInOrder();
        }
    }
    public static void main(String []args){
        BinaryTree bt = new BinaryTree();
        int[] a = {13, 5, 15, 1, 14, 10, 11, 12, 2, 3, 6, 4, 7, 8, 9};

        for(int i = 0; i < a.length; i++){
            bt.insert(a[i]);
        }
        bt.printInOrder();
    }
}