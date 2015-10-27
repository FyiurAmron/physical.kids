package vax.math;

public class Plane3f {
    public Vector3f normal;
    
    public void setNormal( Vector3f vector ) {
        this.normal = vector;
    }
    
    public Vector3f getNormal() {
        return this.normal;
    }

    public float distanceToOrigin;
    
    public void setDistanceToOrigin( float dist ) {
        this.distanceToOrigin = dist;
    }
    
    public float getDistanceToOrigin() {
        return this.distanceToOrigin;
    }

    public Plane3f() {
        this(new Vector3f( 0, 1, 0 ), 0 );
    }

    public Plane3f ( Vector3f vector, float dist ) {
        normal = vector;
        distanceToOrigin = dist;
    }

    public float getDistance ( Vector3f point ) {
        return normal.dot( point ) + distanceToOrigin;
    }

    public float getDistance ( Matrix4f transform ) {
        return normal.dot( transform.TranslationX, transform.TranslationY, transform.TranslationZ ) + distanceToOrigin;
    }
}

