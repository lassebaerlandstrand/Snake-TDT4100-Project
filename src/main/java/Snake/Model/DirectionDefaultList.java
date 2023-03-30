package Snake.Model;

import java.util.Arrays;

import Snake.Utils.DefaultList;

// QueueList with the specified type int[], this allows us to compare elements in the array
public class DirectionDefaultList extends DefaultList<int[]> {

    // The AI needs to be able to add to duplicate directions to the queue
    private boolean allowDuplicates = false;

    public DirectionDefaultList(int[] defaultValue) {
        super(defaultValue);
    }

    public DirectionDefaultList(int[] defaultValue, boolean allowDuplicates) {
        super(defaultValue);
        this.allowDuplicates = allowDuplicates;
    }

    @Override
    public boolean add(int[] arg0) {
        if (!allowDuplicates && Arrays.equals(arg0, peekLast())) {
            return false;
        }
        return super.add(arg0);
    }

}
