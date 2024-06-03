import java.util.Comparator;

public class CoordinateCompare implements Comparator<Coordinate>{
    // Comparator for Priority Queue
    @Override
    public int compare(Coordinate c1, Coordinate c2){
        return c1.getPriority() - c2.getPriority();
    }
}
