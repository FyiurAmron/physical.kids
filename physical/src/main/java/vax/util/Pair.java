package vax.util;

public class Pair<T> {
    public T value1, value2;

    public Pair (){
    }

    public Pair ( T t1, T t2 ) {
        _set( t1, t2 );
    }

    public void set ( T t1, T t2 ) {
        _set( t1, t2 );
    }

    private void _set ( T t1, T t2 ) {
        this.value1 = t1;
        this.value2 = t2;
    }

    @Override
    public boolean equals ( Object obj ) {
        if ( obj == null ) {
            return false;
        }
        if ( obj == this ) {
            return true;
        }
        if ( !( obj instanceof Pair ) ) {
            return false;
        }
        Pair<?> p = (Pair) obj;
        return p.value1 == value1 && p.value2 == value2;
    }

    @Override
    public int hashCode () {
        return value1.hashCode() * 31 + value2.hashCode();
    }
}
