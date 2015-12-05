package vax.math;

public class Value1f {
    private float value;

    public Value1f () {
    }

    public Value1f ( float value ) {
        this.value = value;
    }

    public void setValue ( float val ) {
        this.value = val;
    }

    public float getValue () {
        return this.value;
    }

    @Override
    public String toString () {
        return Float.toString( value );
    }
}
