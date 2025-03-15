import java.util.Arrays;

public class btree2 {
    private Node2 root;
    private LeitorTxt reader;
    private int t;
    private final int pageSize;

    public btree2(int t, String fileDir) {
        pageSize = 1000;
        this.t = t;
        root = null;
        reader = new LeitorTxt(fileDir);
        buildTree();
    }

    private void buildTree() {
        double[] linha;
        int contador = 0;
        while((linha = reader.proximaLinha())[0] > -1){
            if (contador % pageSize == 0) {
                System.out.println("Inserindo na arvore a tupla " + Arrays.toString(linha));
                insert(linha);
            }
            contador++;
        }
    }

    public Node2 search(double key) {
        Node2 node = null;
        long offset = -1;
        if (root != null) {
            node = root.search(key);
            System.out.println("nó retornado:" + node);
            if (node != null) {
                int i = 0;
                int n = node.n;
                double[][] keys = node.keys;
                System.out.println("chave pesquisada: "+ key);
                while (i < n && key > keys[i][0]) {
                    i++;
                    if (i < n && keys[i][0] == key) {
                        offset = (long) keys[i][1];
                        System.out.println("chave encontrada: " + keys[i][0] + " offset: " + offset);
                    } else if (i < n && keys[i][0] > key) {
                        offset = (long) keys[i-1][1];
                        System.out.println("chave encontrada: " + keys[i-1][0] + " offset: " + offset);
                    }
                }
                if (searchInFile(key, offset) == -1) {
                    node = null;
                }
            }
        }
        return node;
    }

    private double searchInFile(double key, long offset) {
        reader.seek(offset);
        double linha = reader.proximaLinha()[0];
        int contador = 1;
        System.out.println("procurando "+key);
        do {
            if (contador % pageSize != 0) {
                if (linha == key) {
                    System.out.println("achou "+linha);
                    break;
                }
            } else {
                linha = -1;
                break;
            }
            contador++;
        } while ((linha = reader.proximaLinha()[0]) > -1);
        return linha;
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
