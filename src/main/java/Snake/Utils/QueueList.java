package Snake.Utils;

import java.util.ArrayList;
import java.util.List;

// This list is a queue (first-in-last-out), meaning that the first item added is the first item removed
// If this list has elements, it will always have at least one element, never become empty
// This is used to store the direction of the snake
public class QueueList<T> extends ArrayList<T> {

    protected List<T> list = new ArrayList<T>();

    public QueueList(List<T> list) {
        this.list = list;
    }

    @Override
    public boolean add(T arg0) {
        if (arg0.equals(peek()))
            return false;
        return super.add(arg0);
    }

    public T peek() {
        if (list.size() == 0)
            return null;
        return list.get(0);
    }

    public T peekLast() {
        if (list.size() == 0)
            return null;
        System.out.println(((int[]) list.get(list.size() - 1))[0] + " : " + ((int[]) list.get(list.size() - 1))[1]);
        return list.get(list.size() - 1);
    }

    // Only removes if there is more than one element
    // This ensures that the list will never be empty
    public T getRemoveFirst() {
        if (list.size() == 0)
            return null;
        T item = list.get(0);
        if (list.size() > 1)
            list.remove(0);
        return item;
    }
}
