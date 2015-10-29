package vax.physics;

import vax.util.Tuple;

public class ColliderDescriptor extends Tuple<Class> {

    public ColliderDescriptor ( Class bodyType1, Class bodyType2 ) {
        super(bodyType1, bodyType2);
    }

    public boolean checkTypes ( Body body1, Body body2 ) {
        return (left.equals( body1.getClass() ) && right.equals( body2.getClass() ));
    }
}

