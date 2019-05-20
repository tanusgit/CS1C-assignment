package lazyTrees;
import java.util.*;

    public class LazySearchTree<E extends Comparable< ? super E > >
            implements Cloneable {
        protected int mSize;
        protected LazySTNode mRoot;

        /* a new int mSizeHard attribute to LazySearchTree class
        which tracks the number of hard nodes in it, i.e., both
        deleted and undeleted.Meanwhile, mSize is still there
        and will only reflect the number of undeleted nodes.
        Normally, the client will not need to know about
        mSizeHard, but we want it for debugging purposes.
        */
        int mSizeHard = 0;

        public LazySearchTree() {
            clear();
        }

        public boolean empty() {
            return (mSize == 0);
        }

        public int size() {
            return mSize;
        }

        public void clear() {
            mSize = 0;
            mRoot = null;
        }

        public int showHeight() {
            return findHeight(mRoot, -1);
        }

        public E findMin() {
            if (mRoot == null)
                throw new NoSuchElementException();
            return findMin(mRoot).data;
        }

        public E findMax() {
            if (mRoot == null)
                throw new NoSuchElementException();
            return findMax(mRoot).data;
        }

        public E find(E x) {
            LazySTNode resultNode;
            resultNode = find(mRoot, x);
            if (resultNode == null)
                throw new NoSuchElementException();
            return resultNode.data;
        }

        public boolean contains(E x) {
            return find(mRoot, x) != null;
        }

        public boolean insert(E x) {
            int oldSize = mSize;
            mRoot = insert(mRoot, x);
            return (mSize != oldSize);
        }

        public boolean remove(E x) {
            int oldSize = mSize;
            mRoot = remove(mRoot, x);
            return (mSize != oldSize);
        }

        public <F extends Traverser<? super E>>
        void traverseHard(F func) {
            traverseHard(func, mRoot);
        }

        /*
        Add a public/protected pair of traverseSoft()
        methods such that it traverses nodes that have not
        been marked as deleted in the LazySearchTree.You may
        find it helpful to use the public/protected
        parameterized method pairs traverseHard() as a
        template.
         */
        public <F extends Traverser<? super E>>
        void traverseSoft(F func) {
            traverseSoft(func, mRoot);
        }

        /*
        an accessor method called sizeHard(), so the
        client can test the class by displaying both the soft
        size (the number the client normally wants) and the
        hard size.
         */
        public int sizeHard() {
            return mSizeHard;
        }


        public Object clone() throws CloneNotSupportedException {
            LazySearchTree<E> newObject = (LazySearchTree<E>) super.clone();
            newObject.clear();  // can't point to other's data

            newObject.mRoot = cloneSubtree(mRoot);
            newObject.mSize = mSize;

            return newObject;
        }

        /*
         Modify the protected methods findMin() and findMax()
         to implement lazy deletion. These should start
         searching from the root and return the minimum and
         maximum node that has NOT been deleted.
         */
        protected LazySTNode findMin(LazySTNode root) {
            if (root == null)
                return null;
            LazySTNode lftChild = findMin(root.lftChild);
            if (lftChild != null)
            {
                return lftChild;
            }
            if (root.deleted)
            {
                return findMin(root.rtChild);
            } else
            {
                return root;
            }
        }

        protected LazySTNode findMinHard(LazySTNode root)
        {
            if (root == null)
                return null;

            if (root.lftChild == null) {
                return root;
            }
            return findMinHard(root.lftChild);
        }

        /*
        Modify the protected methods findMin() and findMax()
        to implement lazy deletion. These should start
        searching from the root and return the minimum and
        maximum node that has NOT been deleted.
         */
        protected LazySTNode findMax(LazySTNode root) {
            if (root == null)
                return null;
            LazySTNode rtChild = findMax(root.rtChild);
            if (rtChild != null)
            {
                return rtChild;
            }
            if (root.deleted)
            {
                return findMax(root.lftChild);
            } else
            {
                return root;
            }
        }

        protected LazySTNode findMaxHard(LazySTNode root) {
            if (root == null)
                return null;
            if (root.rtChild == null) {
                return root;
            }
            return findMaxHard(root.rtChild);
        }


        /*
        Adjust insert() and any other methods that might need
        revision to work with this new deletion technique.
        Note: The only exceptions to this are the
        height-related attributes and methods. You can ignore
        any height-related code you find in the file.
        */
        protected LazySTNode insert(LazySTNode root, E x) {
            int compareResult;  // avoid multiple calls to compareTo()

            if (root == null) {
                mSize++;
                mSizeHard++;
                return new LazySTNode(x, null, null);
            }

            compareResult = x.compareTo(root.data);
            // Found the node, there may be deleted node here.
            if (compareResult == 0) {
                root.deleted = false;
                root.data = x;
                return root;
            }
            if (compareResult < 0)
                root.lftChild = insert(root.lftChild, x);
            else if (compareResult > 0)
                root.rtChild = insert(root.rtChild, x);

            return root;
        }


        /*
        Revise remove() (both the public and protected/recursive
        version) to implement lazy deletion.
        The private version can now have a void return type.
         */
        public LazySTNode remove(LazySTNode root, E x) {
            int compareResult;  // avoid multiple calls to compareTo()

            if (root == null)
                return null;

            compareResult = x.compareTo(root.data);
            if (compareResult < 0)
                return remove(root.lftChild, x);
            else if (compareResult > 0)
                return remove(root.rtChild, x);

            root.deleted = true;
            mSize--;
            return root;
        }

        public void garbageCollect() {

        }
        protected LazySTNode removeHard(LazySTNode root, E x) {
            int compareResult;  // avoid multiple calls to compareTo()

            if (root == null)
                return null;

            compareResult = x.compareTo(root.data);
            if ( compareResult < 0 )
                root.lftChild = remove(root.lftChild, x);
            else if ( compareResult > 0 )
                root.rtChild = remove(root.rtChild, x);

            // found the node
            else if (root.lftChild != null && root.rtChild != null)
            {
                root.data = findMin(root.rtChild).data;
                root.rtChild = remove(root.rtChild, root.data);
            }
            else
            {
                root =
                        (root.lftChild != null)? root.lftChild : root.rtChild;
                mSize--;
                mSizeHard--;
            }
            return root;
        }

        /*
        Rename the public/protected pair traverse() to
        traverseHard(). We will use these to traverse all
        nodes in the LazySearchTree, which means both "deleted"
        and non-deleted nodes.Recall that the public facing
        method of traverseHard() receive a generic argument
        for the functor to use while traversing the current
        instance of LazySearchTree.
         */
        protected <F extends Traverser<? super E>>
        void traverseSoft(F func, LazySTNode treeNode) {
            if (treeNode == null)
                return;

            traverseSoft(func, treeNode.lftChild);
            if (!treeNode.deleted) {
                func.visit(treeNode.data);
            }
            traverseSoft(func, treeNode.rtChild);
        }

        protected <F extends Traverser<? super E>>
        void traverseHard(F func, LazySTNode treeNode) {
            if (treeNode == null)
                return;

            traverseHard(func, treeNode.lftChild);
            func.visit(treeNode.data);
            traverseHard(func, treeNode.rtChild);
        }


        /*
        Modify the protected method find() to implement lazy
        deletion such that it will ignore any nodes that are
        marked as "deleted".
         */
        protected LazySTNode find(LazySTNode root, E x) {
            int compareResult;  // avoid multiple calls to compareTo()

            if (root == null)
                return null;

            compareResult = x.compareTo(root.data);
            if (compareResult < 0)
                return find(root.lftChild, x);
            if (compareResult > 0)
                return find(root.rtChild, x);
            return root;   // found
        }


        protected LazySTNode cloneSubtree(LazySTNode root) {
            LazySTNode newNode;
            if (root == null)
                return null;

            // does not set myRoot which must be done by caller
            newNode = new LazySTNode(root.data,
                            cloneSubtree(root.lftChild),
                            cloneSubtree(root.rtChild)
                    );
            return newNode;
        }


        protected int findHeight(LazySTNode treeNode, int height) {
            int leftHeight, rightHeight;
            if (treeNode == null)
                return height;
            height++;
            leftHeight = findHeight(treeNode.lftChild, height);
            rightHeight = findHeight(treeNode.rtChild, height);
            return (leftHeight > rightHeight) ? leftHeight : rightHeight;
        }

        // inner class
        private class LazySTNode {
            // use public access so the tree or other classes can access members
            public LazySTNode lftChild, rtChild;
            public E data;
            //a boolean deleted attribute to LazySTNode.
            public boolean deleted;
            public LazySTNode myRoot;  // needed to test for certain error

            public LazySTNode(E d, LazySTNode lft, LazySTNode rt) {
                lftChild = lft;
                rtChild = rt;
                data = d;
            }

            public LazySTNode() {
                this(null, null, null);
            }
        }
    }