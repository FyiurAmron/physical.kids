package vax.openglue.constants;

/**

 @author toor
 */
public enum TextureTarget implements OpenGlConstantWrapper {
    TODO(0);

    private final int value;

    private TextureTarget ( int value ) {
        this.value = value;
    }

    @Override
    public int getValue () {
        return value;
    }
}
