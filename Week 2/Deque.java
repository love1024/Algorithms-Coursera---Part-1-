/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private Node first;
    private Node last;
    private int size = 0;

    private class Node {
        private Item item;
        private Node next;
        private Node previous;
    }

    private class DequeIterator implements Iterator<Item> {
        private Node current = first;

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public boolean hasNext() {
            return current != null;
        }

        public Item next() {
            if (current == null) {
                throw new NoSuchElementException();
            }
            Item item = this.current.item;
            this.current = this.current.next;

            return item;
        }
    }

    // construct an empty deque
    public Deque() {

    }

    // is the deque empty?
    public boolean isEmpty() {
        return first == null;
    }

    // return the number of items on the deque
    public int size() {
        return this.size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        Node newItem = new Node();
        newItem.item = item;
        if (this.first == null) {
            this.first = newItem;
            this.last = this.first;
        } else {
            this.first.previous = newItem;
            newItem.next = this.first;
            this.first = this.first.previous;
        }
        this.size++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        Node newItem = new Node();
        newItem.item = item;
        if (this.first == null) {
            this.first = newItem;
            this.last = this.first;
        } else {
            newItem.previous = this.last;
            this.last.next = newItem;
            this.last = this.last.next;
        }
        this.size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (this.first == null) {
            throw new NoSuchElementException();
        }

        Item item = this.first.item;
        this.first = this.first.next;
        if (this.first != null) {
            this.first.previous = null;
        } else {
            this.last = null;
        }

        this.size--;

        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (this.last == null) {
            throw new NoSuchElementException();
        }

        Item item = this.last.item;
        this.last = this.last.previous;
        if (this.last != null) {
            this.last.next = null;
        } else {
            this.first = null;
        }

        this.size--;

        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<>();
        deque.addFirst(1);
        deque.addLast(2);
        System.out.println(deque.size());

        for (Integer i: deque) {
            System.out.println(i);
        }

        System.out.println(deque.removeLast());
        System.out.println(deque.removeFirst());

        System.out.println(deque.size());
    }
}
