package Custom;

public class Tuple3<u, v, w> {
    private u first;
    private v second;
    private w third;

    public Tuple3(u first, v second, w third) {
        this.first = first;
        this.second = second;
        this.third = third;
    }

    public u getFirst() {
        return first;
    }

    public v getSecond() {
        return second;
    }

    public w getThird() {
        return third;
    }

    public void setFirst(u first) {
        this.first = first;
    }

    public void setSecond(v second) {
        this.second = second;
    }

    public void setThird(w third) {
        this.third = third;
    }

    @Override
    public String toString() {
        return "Tuple3{" +
                "first=" + first +
                ", second=" + second +
                ", third=" + third +
                '}';
    }

}
