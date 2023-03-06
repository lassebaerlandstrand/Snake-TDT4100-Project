package Snake.Model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Snake.Utils.QueueList;

// QueueList with the specified type int[], this allows us to compare elements in the array
public class DirectionQueueList extends QueueList<int[]> {

    public DirectionQueueList(List<int[]> list) {
        super(new ArrayList<int[]>(list));
    }

    @Override
    public boolean add(int[] arg0) {
        if (size() > 0 && Arrays.equals(arg0, peekLast()))
            return false;
        return super.add(arg0);
    }

}
