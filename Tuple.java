public class Tuple<A, B> {

    public final A a;
    public final B b;
//This is the constructor to intialize the key value pair in the given tuple.
    public Tuple(A a, B b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tuple<?, ?> tuple = (Tuple<?, ?>) o;
        if (!a.equals(tuple.a)) return false;
        return b.equals(tuple.b);
    }

    @Override
    public int hashCode() {
        int result = a.hashCode();
        result = 31 * result + b.hashCode();
        return result;
    }
//getter to get Value b
    public B getValue(){
    	return b;
    }
// getter to get Key a
    public A getKey(){
    	return a;
    }
}
