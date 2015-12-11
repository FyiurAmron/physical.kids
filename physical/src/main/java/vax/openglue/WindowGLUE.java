package vax.openglue;

import vax.math.Vector2i;

/**

 @author toor
 */
public interface WindowGLUE {
    public static class Settings {
        public String title;
        public Vector2i windowSize;
        public Vector2i windowPosition;
        public boolean undecorated;
        public boolean alwaysOnTop;
        public boolean fullscreen;
        public boolean mouseVisible;
        public boolean mouseConfined;

        public Settings ( String title, Vector2i windowSize ) {
            this.title = title;
            this.windowSize = windowSize;
            this.windowPosition = new Vector2i();
            mouseVisible = true;
        }

        public Settings ( String title, int sizeX, int sizeY ) {
            this( title, new Vector2i( sizeX, sizeY ) );
        }

        public Settings ( String title, Vector2i windowSize, Vector2i windowPosition,
                boolean undecorated, boolean alwaysOnTop, boolean fullscreen, boolean mouseVisible, boolean mouseConfined ) {
            this.title = title;
            this.windowSize = windowSize;
            this.windowPosition = windowPosition;
            this.undecorated = undecorated;
            this.alwaysOnTop = alwaysOnTop;
            this.fullscreen = fullscreen;
            this.mouseVisible = mouseVisible;
            this.mouseConfined = mouseConfined;
        }

        public Settings ( String title, int sizeX, int sizeY, int posX, int posY,
                boolean undecorated, boolean alwaysOnTop, boolean fullscreen, boolean mouseVisible, boolean mouseConfined ) {
            this.title = title;
            this.windowSize = new Vector2i( sizeX, sizeY );
            this.windowPosition = new Vector2i( posX, posY );
            this.undecorated = undecorated;
            this.alwaysOnTop = alwaysOnTop;
            this.fullscreen = fullscreen;
            this.mouseVisible = mouseVisible;
            this.mouseConfined = mouseConfined;
        }

        /**
         @return <code>( (float) windowSize.getX() ) / windowSize.getY()</code>
         */
        public float getAspectRatio () {
            return ( (float) windowSize.getX() ) / windowSize.getY();
        }

    }

    void dispose ();

    /**
     Note: the GL context returned here may be unsuitable for instant use (outdated, disposed etc.).
     Rely on the listener implementation to always get valid, fresh context.

     @return
     */
    OpenGLUE getOpenGLUE ();
}
