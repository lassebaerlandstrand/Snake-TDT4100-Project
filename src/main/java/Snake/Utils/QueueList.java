package Snake.Utils;

import java.util.ArrayList;
import java.util.List;

// This this is a queue (first-in-last-out), meaning that the first item added is the first item removed
// This datatype is required to always have at least one element, and will therefore never be empty
// This is used to store the direction of the snake
public class QueueList<T> extends ArrayList<T> {

    // Super constructor needs to be called first, but this does not matter, since the constructor allows empty lists
    // We check after if the list is empty, and throw an exception if it is, since this datatype does not allow empty lists
    public QueueList(List<T> list) {
        super(list);
        if (this.size() == 0)
            throw new IllegalStateException("List must have at least one element");
    }

    public T peek() {
        return this.get(0);
    }

    public T peekLast() {
        return this.get(this.size() - 1);
    }

    // Only removes if there is more than one element
    // This ensures that the this will never be empty
    public T getRemoveFirst() {
        T item = this.get(0);
        if (this.size() > 1)
            this.remove(0);
        return item;
    }
}
