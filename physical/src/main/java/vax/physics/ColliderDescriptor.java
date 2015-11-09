package vax.physics;

/**
 * Created by Kuba on 2015-10-28.
 */
public class ColliderDescriptor extends Tuple<Type,Type> {

public ColliderDescriptor ( Class bodyType1, Class bodyType2 ) {
        this( bodyType1, bodyType2 );
        }

public bool checkTypes ( Body body1, Body body2 ) {
        return Item1.IsInstanceOfType( body1 ) && Item2.IsInstanceOfType( body2 );
        }
        }

