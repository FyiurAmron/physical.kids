package vax.math;

public class Plane3f {

    public Vector3f normal;

    public void setNormal ( Vector3f normal ) {
        this.normal = normal;
    };

    public Vector3f getVector () {
        return normal;
    }

    public float distanceToOrigin;
    
    public void setDistanceToOrigin ( float distance ) {
        this.distanceToOrigin = distance;
    }
    
    public float getDistanceToOrigin () {
        return distanceToOrigin;
    }
    
    public Plane3f () {
        this( new Vector3f( 0, 1, 0 ), 0 );
    }
    
    public Plane3f ( Vector3f normal, float distanceToOrigin ) {
        this.normal = normal;
        this.distanceToOrigin = distanceToOrigin;
    }
    
    public float getDistance ( Vector3f point ) {
        return normal.dot( point ) + distanceToOrigin;
    }
    
    public float getDistance ( Matrix4f transform ) {
        return normal.dot( transform.getTranslationX(), transform.getTranslationY(), transform.getTranslationZ() ) + distanceToOrigin;
    }
}
