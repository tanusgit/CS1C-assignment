package cs1c;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Queue<T> implements Iterable<T>{
    private static class Node<T>{
        private Node<T> Next;
        T data;
        public Node(T d, Node<T> n){
            data = d;
            Next = n;
        }
        public T getData(){
            return data;
        }
        public Node<T> getNext(){
            return Next;
        }
        public void setNext(Node<T> m){
            Next = m;
        }
    }
    private int size = 0;
    /*A variable called name of type String for the name
    of this instance. We we will use this for testing and debugging purposes.
     */
    String name;
    /*
    A variable called head, which points to the front of the queue.
     */
    private Node<T> head = null;
    /*
    A variable called tail​, which points to the end of the queue.
     */
    private Node<T> tail = null;

    public String getName() {
        return name;
    }

    public boolean isEmpty(){
        return size ==0;
    }

    public int size(){
        return size;
    }

    public String toString(){
        String result = name + "\n[";
        Iterator<T> i = iterator();
        while (i.hasNext()) {
            T d = i.next();
            result = result + d;
            if (i.hasNext()) {
                result = result + ";\n";
            }
        }
        result = result + "]\n";
        return result;
    }

    /*
    A constructor that takes in a user assigned
    name and initializes the class attributes.
    */
    public Queue(String name, Node<T> tail, Node<T>head){
        this.name = name;
        this.tail = tail;
        this.head = head;
    }
    /*A method called enqueue() which takes a generic item as the
    argument and adds the item to the end of the queue.
    */

    public void enqueue(T e){
        Node<T> n = new Node<>(e, null);
        if(isEmpty()){
            head = n;
            tail = n;
        }else{
            tail.setNext(n);
            tail = n;
        }
        size++;
    }
    /*
    A method called dequeue() which receives no arguments and removes
    the item from the front of the queue. This method should return
    the generic item dequeue-ed.
    This method should throw an NoSuchElementException if the queue is empty.
     */
    public T dequeue(){
            if (isEmpty()) {
                throw new NoSuchElementException();
            }
        T result = head.getData();
        head = head.getNext();
        size--;
        if(size ==0){
            tail = null;
        }
        return result;

    }
    /*
    A method called peek which looks at the least recently added item
    of the queue and returns an object of the generic type for the data seen at the front of the queue. The item should not be removed from the front of the queue.
    NOTE: If the queue is empty, returns null.
     */
    public T peek(){
        if(isEmpty()){
            return null;
        }
        return head.getData();

    }

    public Iterator<T> iterator(){
        return new QueueIterator(head);
    }

class QueueIterator implements Iterator<T>{
    private Node<T> current;
    public QueueIterator(Node<T> t){
        current = t;
    }

    public boolean hasNext(){
        return current != null;
    }

    public T next(){
        if (current == null){
            throw new java.util.NoSuchElementException();
        }
        T t = current.getData();
        current = current.getNext();
        return t;
    }
}
}