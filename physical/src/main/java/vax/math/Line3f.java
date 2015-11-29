package vax.math;

public class Line3f {
    protected Vector3f origin;
    protected Vector3f direction;

    public Line3f () {
        origin = new Vector3f();
        direction = new Vector3f();
    }

    public Line3f ( Vector3f origin, Vector3f direction ) {
        this.origin = origin;
        this.direction = direction;
    }

    public Line3f ( Vector3f point1, Vector3f point2, Void isTwoPoints ) {
        this.origin = point1;
        this.direction = new Vector3f( point2.getX() - point1.getX(), point2.getY() - point1.getY(), point2.getZ() - point1.getZ() );
    }
    
    public Vector3f getOrigin () {
        return origin;
    }
    
    public Vector3f getDirection () {
        return direction;
    }
    
    public void setOrigin ( Vector3f origin ) {
        this.origin = origin;
    }
    
    public void setDirection ( Vector3f direction ) {
        this.direction = direction;
    }
}
