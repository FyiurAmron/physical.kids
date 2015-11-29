package vax.math;

public class Plane3f {
    public Vector3f normal;
    public float distanceToOrigin;

    public Plane3f () {
        this( new Vector3f( 0, 1, 0 ), 0 );
    }

    public Plane3f ( Vector3f normal, float distanceToOrigin ) {
        this.normal = normal;
        this.distanceToOrigin = distanceToOrigin;
    }

    public Plane3f ( Vector3f point1, Vector3f point2, Vector3f point3 ) {
        set( point1, point2, point3 );
    }

    public void setNormal ( Vector3f normal ) {
        this.normal = normal;
    }

    public Vector3f getNormal () {
        return normal;
    }

    public void setDistanceToOrigin ( float distance ) {
        this.distanceToOrigin = distance;
    }

    public float getDistanceToOrigin () {
        return distanceToOrigin;
    }

    public float getDistance ( Vector3f point ) {
        return normal.dot( point ) + distanceToOrigin;
    }

    public float getDistance ( Matrix4f transform ) {
        return normal.dot( transform.getTranslationX(), transform.getTranslationY(), transform.getTranslationZ() ) + distanceToOrigin;
    }

    public void set ( Vector3f point1, Vector3f point2, Vector3f point3 ) {
        normal.set( point1 );
        normal.subtract( point2 );
        normal.cross( new Vector3f( point2.getX() - point3.getX(), point2.getY() - point3.getY(), point2.getZ() - point3.getZ() ) );
        normal.normalize();
        distanceToOrigin = -point1.dot( normal );
    }

//    public Line3f intersect ( Plane3f plane ) {
//        
//        
//        return new Line3f();
//    }

}
