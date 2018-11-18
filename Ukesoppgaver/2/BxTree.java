/** 2.7 B-tree */
public class BxTree<Key extends Comparable<? super Key>, Value> {
    /** Pointer to the root node. It may be a leaf or an inner node, but it is never null. */
    private Node root;
    /** the maximum number of keys in the leaf node, M must be > 0 */
    private final int M;
    /** the maximum number of keys in inner node, the number of pointer is N+1, N must be > 2 */
    private final int N;

    /** Create a new empty tree. */
    public BxTree(int n) {
	      this(n, n);
    }

    public BxTree(int m, int n) {
        M = m;
        N = n;
        root = new LNode();
    }

    public void insert(Key key, Value value) {
	     System.out.println("insert key=" + key);
	      Split result = root.insert(key, value);
        if (result != null) {
	    // The old root was split into two parts.
	    // We have to create a new root pointing to them
            INode _root = new INode();
            _root.num = 1;
            _root.keys[0] = result.key;
            _root.children[0] = result.left;
            _root.children[1] = result.right;
            root = _root;
        }
    }

    /** 
     * Looks for the given key. If it is not found, it returns null.
     * If it is found, it returns the associated value.
     */
    public Value find(Key key) {
        Node node = root;
        while (node instanceof BxTree.INode) { // need to traverse down to the leaf
	    INode inner = (INode) node;
            int idx = inner.getLoc(key);
            node = inner.children[idx];
        }

        //We are @ leaf after while loop
        LNode leaf = (LNode) node;
        int idx = leaf.getLoc(key);
        if (idx < leaf.num && leaf.keys[idx].equals(key)) {
	         return leaf.values[idx];
        } else {
	        return null;
        }
    }

    public void dump() {
	    root.dump();
    }

    abstract class Node {
    	protected int num; //number of keys
    	protected Key[] keys;

    	abstract public int getLoc(Key key);
    	// returns null if no split, otherwise returns split info
    	abstract public Split insert(Key key, Value value);
    	abstract public void dump();
    }

    class LNode extends Node {
	// In some sense, the following casts are almost always illegal
	// (if Value was replaced with a real type other than Object,
	// the cast would fail); but they make our code simpler
	// by allowing us to pretend we have arrays of certain types.
	// They work because type erasure will erase the type variables.
	// It will break if we return it and other people try to use it.
	final Value[] values = (Value[]) new Object[M];
	{ keys = (Key[]) new Comparable[M]; }

	/**
	 * Returns the position where 'key' should be inserted in a leaf node
	 * that has the given keys.
	 */
	public int getLoc(Key key) {
	    // Simple linear search. Faster for small values of N or M, binary search would be faster for larger M / N
	    for (int i = 0; i < num; i++) {
		if (keys[i].compareTo(key) >= 0) {
		    return i;
		}
	    }
	    return num;
	}

	public Split insert(Key key, Value value) {
	    // Simple linear search
	    int i = getLoc(key);
	    if (this.num == M) { // The node was full. We must split it
		int mid = (M+1)/2;
		int sNum = this.num - mid;
		LNode sibling = new LNode();
		sibling.num = sNum;
		System.arraycopy(this.keys, mid, sibling.keys, 0, sNum);
		System.arraycopy(this.values, mid, sibling.values, 0, sNum);
		this.num = mid;
		if (i < mid) {
		    // Inserted element goes to left sibling
		    this.insertNonfull(key, value, i);
		} else {
		    // Inserted element goes to right sibling
		    sibling.insertNonfull(key, value, i-mid);
		}
		// Notify the parent about the split
		Split result = new Split(sibling.keys[0], //make the right's key >= result.key
					 this,
					 sibling);
		return result;
	    } else {
		// The node was not full
		this.insertNonfull(key, value, i);
		return null;
	    }
	}

	private void insertNonfull(Key key, Value value, int idx) {
	    //if (idx < M && keys[idx].equals(key)) {
	    if (idx < num && keys[idx].equals(key)) {
		// We are inserting a duplicate value, simply overwrite the old one
		values[idx] = value;
	    } else {
		// The key we are inserting is unique
		System.arraycopy(keys, idx, keys, idx+1, num-idx);
		System.arraycopy(values, idx, values, idx+1, num-idx);

		keys[idx] = key;
		values[idx] = value;
		num++;
	    }
	}

	public void dump() {
	    System.out.println("lNode h==0");
	    for (int i = 0; i < num; i++){
		    System.out.println(keys[i]);
	    }
	}
}
