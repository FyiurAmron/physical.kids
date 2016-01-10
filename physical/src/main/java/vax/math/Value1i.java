package vax.math;

public class Value1i {
    private int value;

    public Value1i () {
    }

    public Value1i ( int value ) {
        this.value = value;
    }

    public void setValue ( int val ) {
        this.value = val;
    }

    public int getValue () {
        return this.value;
    }

    @Override
    public String toString () {
        return Float.toString( value );
    }
}
