public class btree2 {
    private Node2 root;
    private int t;

    public btree2(int t) {
        this.t = t;
        root = null;
    }

    public Node2 search(double key) {
        return (root == null) ? null : root.search(key);
    }

    public void insert(double[] keyArray) {
        if (root == null) {
            root = new Node2(t, true);
            root.keys[0][0] = keyArray[0];
            root.keys[0][1] = keyArray[1];
            root.n = 1;
        } else {
            if (root.n == 2 * t - 1) {
                Node2 newRoot = new Node2(t, false);
                newRoot.children[0] = root;
                newRoot.splitChild(0, root);

                int i = 0;

                if (newRoot.keys[0][0] < keyArray[0])
                    i++;

                newRoot.children[i].insertNonFull(keyArray);
                root = newRoot;
            } else {
                root.insertNonFull(keyArray);
            }
        }
    }

    public void printBTree() {
        if (root != null)
            root.printInOrder();

        System.out.println();
    }
    
    public void remove(double key) {
        if (root == null) {
            System.out.println("The tree is empty");
            return;
        }
        
        // Call the recursive remove function
        root.remove(key);
        
        // If the root Node2 has 0 keys after removal
        if (root.n == 0) {
            // If the tree is not empty
            if (!root.isLeaf()) {
                // Make the first child the new root
                Node2 temp = root;
                root = root.children[0];
                // Free the old root
                temp = null;
            } else {
                // If the tree is now empty
                root = null;
            }
        }
    }
}
