package ua.procamp;

/**
 * {@link LinkedList} is a list implementation that is based on singly linked generic nodes. A node is implemented as
 * inner static class {@link Node<T>}. In order to keep track on nodes, {@link LinkedList} keeps a reference to a head node.
 *
 * @param <T> generic type parameter
 */
public class LinkedList<T> implements List<T> {

    private Node<T> head;
    private Node<T> tail;
    private int size;

    /**
     * This method creates a list of provided elements
     *
     * @param elements elements to add
     * @param <T>      generic type
     * @return a new list of elements the were passed as method parameters
     */
    public static <T> List<T> of(T... elements) {
        List<T> list = new LinkedList<>();
        for (T el : elements) {
            list.add(el);
        }
        return list;
    }

    /**
     * Adds an element to the end of the list
     *
     * @param element element to add
     */
    @Override
    public void add(T element) {
        Node temp = new Node(element);
        if (head == null) {
            head = temp;
            tail = head;
        } else {
            tail.next = temp;
            tail = tail.next;
        }
        size++;
    }

    /**
     * Adds a new element to the specific position in the list. In case provided index in out of the list bounds it
     * throws {@link IndexOutOfBoundsException}
     *
     * @param index   an index of new element
     * @param element element to add
     */
    @Override
    public void add(int index, T element) {
        Node<T> newElement = new Node<>(element);

        if (index == 0 && size == 0) {
            add(element);
            return;
        } else if (index == size) {
            tail.next = newElement;
            tail = tail.next;
        } else if (index == 0) {
            newElement.next = head;
            head = newElement;
        } else {
            indexInSize(index);
            Node<T> temp = nodeInPosition(index - 1);
            newElement.next = temp.next;
            temp.next = newElement;
        }
        size++;
    }

    /**
     * Changes the value of an list element at specific position. In case provided index in out of the list bounds it
     * throws {@link IndexOutOfBoundsException}
     *
     * @param index   an position of element to change
     * @param element a new element value
     */
    @Override
    public void set(int index, T element) {
        indexInSize(index);
        Node<T> temp = nodeInPosition(index);
        temp.val = element;
    }

    /**
     * Retrieves an elements by its position index. In case provided index in out of the list bounds it
     * throws {@link IndexOutOfBoundsException}
     *
     * @param index element index
     * @return an element value
     */
    @Override
    public T get(int index) {
        indexInSize(index);
        Node<T> temp = nodeInPosition(index);
        return temp.val;
    }

    /**
     * Removes an elements by its position index. In case provided index in out of the list bounds it
     * throws {@link IndexOutOfBoundsException}
     *
     * @param index element index
     */
    @Override
    public void remove(int index) {
        indexInSize(index);

        if (size == 1) {
            clear();
        } else if (index == 0) {
            head = head.next;
            size--;
        } else if (index == size) {
            tail = nodeInPosition(index - 1);
            tail.next = null;
            size--;
        } else {
            Node<T> temp = nodeInPosition(index - 1);
            temp.next = temp.next.next;
            size--;
        }
    }

    /**
     * Checks if a specific exists in he list
     *
     * @return {@code true} if element exist, {@code false} otherwise
     */
    @Override
    public boolean contains(T element) {
        if (size == 0) return false;
        Node<T> temp = head;
        while (temp.next != null) {
            if (temp.val == element) return true;
            temp = temp.next;
        }
        return false;
    }

    /**
     * Checks if a list is empty
     *
     * @return {@code true} if list is empty, {@code false} otherwise
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns the number of elements in the list
     *
     * @return number of elements
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Removes all list elements
     */
    @Override
    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }

    private Node<T> nodeInPosition(int index) {
        Node<T> temp = head;
        for (int i = 0; i < index; i++) {
            temp = temp.next;
        }

        return temp;
    }

    private void indexInSize(int index) {
        if (index >= size || index < 0 || size == 0) {
            throw new IndexOutOfBoundsException();
        }
    }

    private class Node<T> {
        private Node<T> next;
        private T val;

        public Node(T val) {
            this.val = val;
        }
    }
}
