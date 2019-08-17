package creation.dataworks;

public class Pair implements Comparable<Pair> {
    final int index;
    public final int value;

    Pair(int index, int value) {
        this.index = index;
        this.value = value;
    }

    @Override
    public int compareTo(Pair other) {
        return Integer.compare(this.value, other.value);
    }
}