package Snake;

public class Pair<T> {

    private final T first;
    private final T last;

    public Pair(T first, T last) {
        this.first = first;
        this.last = last;
    }

    public T first() {
        return this.first;
    }

    public T last() {
        return this.last;
    }

}
