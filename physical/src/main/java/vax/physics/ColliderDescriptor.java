package vax.physics;

import vax.util.Pair;

public class ColliderDescriptor extends Pair<Class<?>> {

    public ColliderDescriptor ( Class<?> bodyType1, Class<?> bodyType2 ) {
        super( bodyType1, bodyType2 );
    }

    public boolean checkTypes ( Body body1, Body body2 ) {
        return ( value1.equals( body1.getClass() ) && value2.equals( body2.getClass() ) );
    }
}
