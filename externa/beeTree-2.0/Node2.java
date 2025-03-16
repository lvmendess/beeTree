public class Node2 {  
    double[][] keys; // Stores the keys as arrays [double value, position]
    int t; // Minimum degree (defines range of number of keys)
    Node2[] children; // Stores pointers to children
    int n; // Current number of keys
    boolean leaf; // true = Node2 is a leaf

    public Node2(int t, boolean leaf) {
        this.t = t;
        this.leaf = leaf;

        keys = new double[2 * t - 1][2]; // Each key is now a 2-element array
        children = new Node2[2 * t];
        n = 0;
    }

    public boolean isLeaf() {
        return leaf;
    }

    public Node2 search(double key) {
        int i = 0;

        while (i < n && key > keys[i][0])
            i++;

        if (i < n && keys[i][0] >= key)
            return this;

        if (leaf)
            return null;

        return children[i].search(key);
    }

    // Insert key array in sub-tree of this Node2
    public void insertNonFull(double[] keyArray) {
        int i = n - 1;
        double key = keyArray[0]; // The actual comparison key

        if (leaf) {
            while (i >= 0 && key < keys[i][0]) {
                keys[i + 1][0] = keys[i][0];
                keys[i + 1][1] = keys[i][1];
                i--;
            }
            keys[i + 1][0] = keyArray[0];
            keys[i + 1][1] = keyArray[1];
            n++;
        } else {
            while (i >= 0 && key < keys[i][0])
                i--;
            i++;

            if (children[i].n == 2 * t - 1) {
                splitChild(i, children[i]);
                if (key > keys[i][0])
                    i++;
            }
            children[i].insertNonFull(keyArray);
        }
    }

    // Split child Node2
    public void splitChild(int i, Node2 y) {
        Node2 z = new Node2(y.t, y.leaf);
        z.n = t - 1;

        for (int j = 0; j < t - 1; j++) {
            z.keys[j][0] = y.keys[j + t][0];
            z.keys[j][1] = y.keys[j + t][1];
        }

        if (!y.leaf) {
            for (int j = 0; j < t; j++)
                z.children[j] = y.children[j + t];
        }

        y.n = t - 1;

        for (int j = n; j >= i + 1; j--){
            children[j + 1] = children[j];
        }
        
        children[i + 1] = z;

        for (int j = n - 1; j >= i; j--){
            keys[j + 1][0] = keys[j][0];
            keys[j + 1][1] = keys[j][1];
        }

        keys[i][0] = y.keys[t - 1][0];
        keys[i][1] = y.keys[t - 1][1];
        n++;
    }

    public void printInOrder() {
        int i;

        for (i = 0; i < n; i++) {
            if (!leaf)
                children[i].printInOrder();
            System.out.print("[" + keys[i][0] + ", " + keys[i][1] + "] ");
        }

        if (!leaf)
            children[i].printInOrder();
    }

    void remove(double key) {
        int idx = findKeyIndex(key);
        
        // If the key is present in this Node2
        if (idx < n && keys[idx][0] == key) {
            // If this is a leaf Node2, remove from here
            if (isLeaf()) {
                removeFromLeaf(idx);
            } else {
                // If this is an internal Node2
                removeFromNonLeaf(idx);
            }
        } else {
            // If the key is not present in this Node2
            
            // If this is a leaf Node2, the key is not in the tree
            if (isLeaf()) {
                System.out.println("The key " + key + " does not exist in the tree");
                return;
            }
            
            // Flag to indicate whether the key is present in the last child of this Node2
            boolean flag = (idx == n);
            
            // If the child where the key should exist has less than t keys
            if (children[idx].n < t) {
                // Fill that child
                fill(idx);
            }
            
            // If the last child has been merged, it must have merged with the previous child
            // so we need to recurse on the (idx-1)th child. Else, we recurse on the (idx)th child
            // which now has at least t keys
            if (flag && idx > n) {
                children[idx - 1].remove(key);
            } else {
                children[idx].remove(key);
            }
        }
    }

    // A function to find index of key in the Node2
    int findKeyIndex(double key) {
        int idx = 0;
        // Find the first key greater than or equal to key
        while (idx < n && keys[idx][0] < key) {
            ++idx;
        }
        return idx;
    }

    // A function to remove the key at idx from a leaf Node2
    void removeFromLeaf(int idx) {
        // Move all keys after idx one place backward
        for (int i = idx + 1; i < n; ++i) {
            keys[i - 1][0] = keys[i][0];
            keys[i - 1][1] = keys[i][1];
        }
        
        // Reduce the count of keys
        n--;
    }

    // A function to remove the key at idx from a non-leaf Node2
    void removeFromNonLeaf(int idx) {
        double key = keys[idx][0];
        
        // If the child that precedes key (children[idx]) has at least t keys,
        // find the predecessor of key in the subtree rooted at children[idx].
        // Replace key by pred and recursively delete pred in children[idx]
        if (children[idx].n >= t) {
            double[] pred = getPredecessor(idx);
            keys[idx][0] = pred[0];
            keys[idx][1] = pred[1];
            children[idx].remove(pred[0]);
        }
        
        // If the child that succeeds key (children[idx+1]) has at least t keys,
        // find the successor of key in the subtree rooted at children[idx+1].
        // Replace key by succ and recursively delete succ in children[idx+1]
        else if (children[idx + 1].n >= t) {
            double[] succ = getSuccessor(idx);
            keys[idx][0] = succ[0];
            keys[idx][1] = succ[1];
            children[idx + 1].remove(succ[0]);
        }
        
        // If both children[idx] and children[idx+1] have less than t keys,
        // merge key and all of children[idx+1] into children[idx].
        // Then recursively delete key from children[idx]
        else {
            merge(idx);
            children[idx].remove(key);
        }
    }

    // A function to get the predecessor of the key at idx in this Node2
    double[] getPredecessor(int idx) {
        // Keep moving to the rightmost Node2 until we reach a leaf
        Node2 current = children[idx];
        while (!current.isLeaf()) {
            current = current.children[current.n];
        }
        
        // Return the last key of the leaf
        double[] predecessor = new double[2];
        predecessor[0] = current.keys[current.n - 1][0];
        predecessor[1] = current.keys[current.n - 1][1];
        return predecessor;
    }

    // A function to get the successor of the key at idx in this Node2
    double[] getSuccessor(int idx) {
        // Keep moving to the leftmost Node2 until we reach a leaf
        Node2 current = children[idx + 1];
        while (!current.isLeaf()) {
            current = current.children[0];
        }
        
        // Return the first key of the leaf
        double[] successor = new double[2];
        successor[0] = current.keys[0][0];
        successor[1] = current.keys[0][1];
        return successor;
    }

    // A function to fill child Node2 at idx which has less than t-1 keys
    void fill(int idx) {
        // If the previous child has at least t keys, borrow from it
        if (idx != 0 && children[idx - 1].n >= t) {
            borrowFromPrev(idx);
        }
        
        // If the next child has at least t keys, borrow from it
        else if (idx != n && children[idx + 1].n >= t) {
            borrowFromNext(idx);
        }
        
        // Merge child[idx] with its sibling
        // If child[idx] is the last child, merge it with its previous sibling
        // Otherwise merge it with its next sibling
        else {
            if (idx != n) {
                merge(idx);
            } else {
                merge(idx - 1);
            }
        }
    }

    // A function to borrow a key from children[idx-1] and insert it into children[idx]
    void borrowFromPrev(int idx) {
        Node2 child = children[idx];
        Node2 sibling = children[idx - 1];
        
        // The last key from children[idx-1] goes up to the parent and the key[idx-1]
        // from parent is inserted as the first key in children[idx]
        
        // Moving all keys in children[idx] one step ahead
        for (int i = child.n - 1; i >= 0; --i) {
            child.keys[i + 1][0] = child.keys[i][0];
            child.keys[i + 1][1] = child.keys[i][1];
        }
        
        // If children[idx] is not a leaf, move all its child pointers one step ahead
        if (!child.isLeaf()) {
            for (int i = child.n; i >= 0; --i) {
                child.children[i + 1] = child.children[i];
            }
        }
        
        // Setting children[idx]'s first key equal to keys[idx-1] from the current Node2
        child.keys[0][0] = keys[idx - 1][0];
        child.keys[0][1] = keys[idx - 1][1];
        
        // Moving sibling's last child as children[idx]'s first child
        if (!child.isLeaf()) {
            child.children[0] = sibling.children[sibling.n];
        }
        
        // Moving the key from the sibling to the parent
        keys[idx - 1][0] = sibling.keys[sibling.n - 1][0];
        keys[idx - 1][1] = sibling.keys[sibling.n - 1][1];
        
        // Increasing and decreasing the key count of child and sibling respectively
        child.n += 1;
        sibling.n -= 1;
    }

    // A function to borrow a key from children[idx+1] and place it in children[idx]
    void borrowFromNext(int idx) {
        Node2 child = children[idx];
        Node2 sibling = children[idx + 1];
        
        // keys[idx] is inserted as the last key in children[idx]
        child.keys[child.n][0] = keys[idx][0];
        child.keys[child.n][1] = keys[idx][1];
        
        // Sibling's first child is inserted as the last child of children[idx]
        if (!child.isLeaf()) {
            child.children[child.n + 1] = sibling.children[0];
        }
        
        // The first key from sibling is inserted into keys[idx]
        keys[idx][0] = sibling.keys[0][0];
        keys[idx][1] = sibling.keys[0][1];
        
        // Moving all keys in sibling one step back
        for (int i = 1; i < sibling.n; ++i) {
            sibling.keys[i - 1][0] = sibling.keys[i][0];
            sibling.keys[i - 1][1] = sibling.keys[i][1];
        }
        
        // Moving the child pointers one step back
        if (!sibling.isLeaf()) {
            for (int i = 1; i <= sibling.n; ++i) {
                sibling.children[i - 1] = sibling.children[i];
            }
        }
        
        // Increasing and decreasing the key count of child and sibling respectively
        child.n += 1;
        sibling.n -= 1;
    }

    // A function to merge children[idx] with children[idx+1]
    void merge(int idx) {
        Node2 child = children[idx];
        Node2 sibling = children[idx + 1];
        
        // Pulling a key from the current Node2 and inserting it into (t-1)th position of children[idx]
        child.keys[t - 1][0] = keys[idx][0];
        child.keys[t - 1][1] = keys[idx][1];
        
        // Copying the keys from children[idx+1] to children[idx]
        for (int i = 0; i < sibling.n; ++i) {
            child.keys[i + t][0] = sibling.keys[i][0];
            child.keys[i + t][1] = sibling.keys[i][1];
        }
        
        // Copying the child pointers from children[idx+1] to children[idx]
        if (!child.isLeaf()) {
            for (int i = 0; i <= sibling.n; ++i) {
                child.children[i + t] = sibling.children[i];
            }
        }
        
        // Moving all keys after idx in the current Node2 one step before
        for (int i = idx + 1; i < n; ++i) {
            keys[i - 1][0] = keys[i][0];
            keys[i - 1][1] = keys[i][1];
        }
        
        // Moving the child pointers after (idx+1) in the current Node2 one step before
        for (int i = idx + 2; i <= n; ++i) {
            children[i - 1] = children[i];
        }
        
        // Updating the key count of child and the current Node2
        child.n += sibling.n + 1;
        n--;
        
        // Freeing the memory occupied by sibling
        sibling = null;
    }
}