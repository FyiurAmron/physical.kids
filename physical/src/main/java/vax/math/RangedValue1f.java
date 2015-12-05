package vax.math;

/**

 @author toor
 */
public class RangedValue1f {
    public enum OverflowMode {
        ALLOW, ALLOW_IF_ALREADY, LIMIT, THROW_EXCEPTION
    }
    private float min, max, value;
    private boolean lockMinMax;
    private OverflowMode overflowMode;
    /*
     public final GettableSettableProperty<Float> property = new GettableSettableProperty<Float>() {
     @Override
     public Float get() {
     return value;
     }

     @Override
     public void set( Float value ) {
     setValue( value );
     }
     };
     */

    public RangedValue1f ( float min, float max, float current, OverflowMode overflowMode ) {
        this.min = min;
        this.max = max;
        this.value = current;
        this.overflowMode = overflowMode;
    }

    /**
     Creates a default [-1,1] <code>OverflowMode.ALLOW_IF_ALREADY</code> scroller with value set to middle (0).
     */
    public RangedValue1f () {
        this( -1.0f, 1.0f, 1.0f, OverflowMode.ALLOW_IF_ALREADY );
    }

    public float getMin () {
        return min;
    }

    public float getMax () {
        return max;
    }

    public float getLength () {
        return max - min;
    }

    public float getValue () {
        return value;
    }

    public void setOverscrollMode ( OverflowMode overflowMode ) {
        this.overflowMode = overflowMode;
    }

    public OverflowMode getOverscrollMode () {
        return overflowMode;
    }

    public boolean isInRange () {
        return value >= min && value <= max;
    }

    public boolean isAtLimit () {
        return value == min || value == max;
    }

    public float getOverflow () {
        return ( value < min ) ? value - min : ( ( value > max ) ? value - max : 0 );
    }

    public void setLockMinMax ( boolean lockMinMax ) {
        this.lockMinMax = lockMinMax;
    }

    public boolean isLockMinMax () {
        return lockMinMax;
    }

    private void checkMinMaxLock () {
        if ( lockMinMax ) {
            throw new IllegalStateException( "min & max values locked already" );
        }
    }

    // the warning below is raised due to a NetBeans bug - https://netbeans.org/bugzilla/show_bug.cgi?id=253300
    @SuppressWarnings( "UnusedAssignment" )
    public boolean setMin_internal ( float min, OverflowMode overflowMode ) {
        boolean valueUnchanged = true;
        if ( value < min ) {
            switch ( overflowMode ) {
                case LIMIT:
                    valueUnchanged = false;
                    value = min;
                    break;
                case THROW_EXCEPTION:
                    throw new IllegalArgumentException( "min > value" );
            }
        }
        this.min = min;
        return valueUnchanged;
    }

    // the warning below is raised due to a NetBeans bug - https://netbeans.org/bugzilla/show_bug.cgi?id=253300
    @SuppressWarnings( "UnusedAssignment" )
    public boolean setMax_internal ( float max, OverflowMode overflowMode ) {
        boolean valueUnchanged = true;
        if ( value > max ) {
            switch ( overflowMode ) {
                case LIMIT:
                    value = max;
                    valueUnchanged = false;
                    break;
                case THROW_EXCEPTION:
                    throw new IllegalArgumentException( "max < value" );
            }
        }
        this.max = max;
        return valueUnchanged;
    }

    public boolean setMin ( float min, OverflowMode overflowMode ) {
        checkMinMaxLock();
        if ( min > max ) {
            throw new IllegalArgumentException( "min > max" );
        }
        return setMin_internal( min, overflowMode );
    }

    public boolean setMax ( float max, OverflowMode overflowMode ) {
        checkMinMaxLock();
        if ( max < min ) {
            throw new IllegalArgumentException( "max < min" );
        }
        return setMax_internal( max, overflowMode );
    }

    public boolean setMinMax ( float min, float max ) {
        return setMinMax( min, max, overflowMode );
    }

    public boolean setMinMax ( float min, float max, OverflowMode overflowMode ) {
        checkMinMaxLock();
        if ( max < min ) {
            throw new IllegalArgumentException( "max < min" );
        }
        boolean valueUnchanged = true;
        if ( !setMin_internal( min, overflowMode ) ) {
            valueUnchanged = false;
        }
        if ( !setMax_internal( max, overflowMode ) ) {
            valueUnchanged = false;
        }
        return valueUnchanged;
    }

    /**
     Note: this is the main setter method for the RangedValue1f; it's also the only one you should override in subclasses.

     @param value
     @param overflowMode
     @return exact RangedValue1f value after the call
     */
    public float setValue ( float value, OverflowMode overflowMode ) {
        if ( ( overflowMode == OverflowMode.ALLOW_IF_ALREADY && isInRange() ) || overflowMode == OverflowMode.LIMIT ) {
            if ( value < min ) {
                this.value = min;
                return this.value;
            }
            if ( value > max ) {
                this.value = max;
                return this.value;
            }
        } else if ( overflowMode == OverflowMode.THROW_EXCEPTION && ( value > max || value < min ) ) {
            throw new IllegalArgumentException( "requested value [ " + value + " ] is out of scroll range [" + min + "," + max + "]" );
        }
        this.value = value;
        return this.value;
    }

    public final void setMax ( float max ) {
        setMax( max, overflowMode );
    }

    public final void setMin ( float min ) {
        setMin( min, overflowMode );
    }

    /**
     Equal to <code>setValue(value,true)</code>.

     @param value
     @return exact RangedValue1f value after the call
     @throws IllegalArgumentException if !clamp and factor is not in range [0,1]
     */
    public final float setValue ( float value ) {
        return setValue( value, overflowMode );
    }

    /**
     Equal to <code>addValue(factor,true)</code>.

     @param value
     @return exact RangedValue1f value after the call
     */
    public final float addValue ( float value ) {
        return addValue( value, overflowMode );
    }

    /**

     @param value
     @param overflowMode
     @return exact RangedValue1f value after the call
     @throws IllegalArgumentException if !clamp and factor is not in range [0,1]
     */
    public final float addValue ( float value, OverflowMode overflowMode ) {
        return setValue( this.value + value, overflowMode );
    }

    /**
     Equal to <code>setValueByFactor(factor,true)</code>.

     @param factor
     @return exact RangedValue1f value after the call
     */
    public final float setValueByFactor ( float factor ) {
        return setValueByFactor( factor, overflowMode );
    }

    /**
     @param factor normalized value ranging from 0 (minimum) to 1 (maximum)
     @param overflowMode
     @return exact RangedValue1f value after the call
     @throws IllegalArgumentException if !clamp and factor is not in range [0,1]
     */
    public final float setValueByFactor ( float factor, OverflowMode overflowMode ) {
        return setValue( min + factor * ( max - min ) );
    }

    @Override
    public String toString () {
        return "(" + min + "," + max + ") " + value;
    }
}
