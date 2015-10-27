package vax.math;

public class Value1f {
    public float value;

    public final void setValue(float val) {
        this.value = val;
    }

    public float getValue() {
        return this.value;
    }

    public Value1f () {
    }

    public Value1f ( float val ) {
        setValue( val );
    }
}


