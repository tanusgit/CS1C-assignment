package stacks;

import java.util.Iterator;


public class StackList<T> implements Iterable<T> {
    /*
    An inner class Node with attributes next of type Node and data
     of a generic type.
     */
   private static class Node<T>{
        private Node<T> next;
        private T data;

        public Node(T d, Node<T> n){
        next = n;
        data = d;
        }
        public T getData(){
            return data;
        }
        public Node<T> getNext(){
            return next;
        }
        public void setNext(Node<T> m){
            next = m;
        }
    }

    private Node<T> tail = null;
    private int size =0;

    public StackList(String s){
        name = s;
    }


    /*The isEmpty()
     method, which checks if the top of the stack is pointing to anything.
     */
    public boolean isEmpty(){
        return size == 0;
    }
    /*
    The size() method, which returns the number of elements in the stack.
     */
    public int size(){
        return size;
    }

    /*
     A method called peek() which looks at the top of the stack and
     returns a generic type for the data seen at the top of the stack.
     The item should not be removed from the top of the stack.
     NOTE: If the stack is empty, returns null.
      */
    public T peek() {
        if(isEmpty()){
            return null;
        }
        return top.getData();
    }

    /*returns the last element
     */
    public T last() {
        if(isEmpty()){
            return null;
        }
        return tail.getData();
    }

    /*A method called push() which takes a generic item as the argument and
    adds the item to the top of the stack.
    */
    public void push(T first){
        Node<T> n = new Node<>(first, top);
        top = n;
        if(size == 0){
            tail = top;
        }
        size++;
        //System.out.println("Added a top node with " + top.getData() +
               // " element");

    }

    public void addLast(T e){
        Node<T> newNode = new Node<> (e, null);
        if(isEmpty()){
            top = newNode;
        }else{
            tail.setNext(newNode);
            tail = newNode;
            size++;
            //System.out.println("Added a tail node with " + tail.getData() +
                    //" element");
        }
    }


    /*A method called pop() which receives no arguments and removes the
     item from the top of the stack. This method should return the
      generic item popped.
      */
    public T pop(){
        if(isEmpty()){
            return null;
        }
        T result = top.getData();
        top = top.getNext();
        size--;
        if (size == 0){
           tail = null;
        }
       // System.out.println("removed top node with " + result +
          //      " element");
        return result;
    }


    /*
    A variable called name of type String for the name of this instance.
    We will use this for testing and debugging purposes.
    */
    String name;

    /*
    An variable called top, which points to the top of the stack.
    Recall that elements can be added or removed from only one end of the
    stack.
     */
    Node<T> top; // Should be top

    /*
    A constructor that takes initializes the class attributes.
     */
    public StackList(String name, Node<T> top){
        this.name = name;
        this.top = top;
    }

    /*A method called clear() which discards all object
    references from the linked-list to "empty" this StackList instance.
     */
    public void clear(){
        while (!isEmpty()) {
            pop();
        }
    }
    /*
    The toString() method should include the name of the stack passed in
     by the Navigator class in addition to the number of links in the stack.
     */
    public String toString() {
        String s = name + " with " + size;
        if(size==1){
            s = s  + " link:\n[";
        } else {
            s = s  + " links:\n[";
        }
        Iterator<T> iterator = iterator();
        while (iterator.hasNext()) {
            T d = iterator.next();
            s = s + d;
            if (iterator.hasNext()) {
                s = s + ", ";
            }
        }
        s = s + "]\n";
        return s;
    }


    public Iterator<T> iterator()
    {
        return new StackIterator(top);
    }

    /*
    The inner StackIterator parameterized class which implements an
    the Iterator interface.
    This provides an iterator over the outer generic StackList.
     */
    class StackIterator implements Iterator<T>{
        private Node<T> current;

        public StackIterator(Node<T> t)
        {
            current = t;
        }

        public boolean hasNext() {
            return current != null;
        }

        public T next() {
            if (current == null)
            {
                throw new java.util.NoSuchElementException();
            }
            T t = current.getData();
            current = current.getNext();
            return t;
        }
    }
}
