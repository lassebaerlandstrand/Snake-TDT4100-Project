package Snake.Model;

import java.util.Arrays;

import Snake.Utils.DefaultList;

// QueueList with the specified type int[], this allows us to compare elements in the array
public class DirectionDefaultList extends DefaultList<int[]> {

    public DirectionDefaultList(int[] defaultValue) {
        super(defaultValue);
    }

    @Override
    public boolean add(int[] arg0) {
        if (size() > 0 && Arrays.equals(arg0, peekLast()))
            return false;
        return super.add(arg0);
    }

}
