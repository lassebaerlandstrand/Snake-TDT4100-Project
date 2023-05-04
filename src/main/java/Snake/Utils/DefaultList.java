package Snake.Utils;

import java.util.ArrayList;

/** 
 * This is a queue (first-in-last-out).
 * This is stored as a list, so that we can quickly get the last element without looping through the entire list.
 * This datatype is required to always have at least one element, and will therefore never be empty (it will return defaultValue if list is empty).
 * This is used to store the direction of the snake
 */
public class DefaultList<T> extends ArrayList<T> {

    private T defaultValue;

    public DefaultList(T initialValue) {
        super(); // Creates empty list
        this.defaultValue = initialValue;
    }

    public T peek() {
        return this.size() > 0 ? this.get(0) : defaultValue;
    }

    public T peekLast() {
        return this.size() > 0 ? this.get(this.size() - 1) : defaultValue;
    }

    public T getRemoveFirst() {
        T item = peek();
        if (this.size() > 0)
            this.remove(0);
        defaultValue = item;
        return item;
    }
}
