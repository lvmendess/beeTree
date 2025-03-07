public class BTree {
    private Node root;
    private int t;

    public BTree(int t) {
        this.t = t;
        root = null;
    }

    public Node search(double key) {
        return (root == null) ? null : root.search(key);
    }

    public void insert(double key) {
        if (root == null) {
            root = new Node(t, true);
            root.keys[0] = key;
            root.n = 1;
        } else {
            if (root.n == 2 * t - 1) {
                Node newRoot = new Node(t, false);
                newRoot.children[0] = root;
                newRoot.splitChild(0, root);

                int i = 0;

                if (newRoot.keys[0] < key)
                    i++;

                newRoot.children[i].insertNonFull(key);
                root = newRoot;
            } else {
                root.insertNonFull(key);
            }
        }
    }

    public void printBTree() {
        if (root != null)
            root.printInOrder();

        System.out.println();
    }
}
