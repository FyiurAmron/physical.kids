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

    public Line3f intersect ( Plane3f plane ) {
        Vector3f direction = normal.cross( plane.getNormal() );
        Vector3f point = intersection( this, plane, new Plane3f( direction, 0 ) );
        return new Line3f();
    }

    public Vector3f intersection ( Plane3f plane1, Plane3f plane2, Plane3f plane3 ) {
        float a1 = plane1.getNormal().getX();
        float b1 = plane1.getNormal().getY();
        float c1 = plane1.getNormal().getZ();
        float d1 = plane1.getDistanceToOrigin();

        float a2 = plane2.getNormal().getX();
        float b2 = plane2.getNormal().getY();
        float c2 = plane2.getNormal().getZ();
        float d2 = plane2.getDistanceToOrigin();

        float a3 = plane3.getNormal().getX();
        float b3 = plane3.getNormal().getY();
        float c3 = plane3.getNormal().getZ();
        float d3 = plane3.getDistanceToOrigin();

        float a23 = b2 * c3 - b3 * c2;
        float b23 = c2 * a3 - c3 * a2;
        float c23 = a2 * b3 - a3 * b2;
        float determinant = a1 * a23 + b1 * b23 + c1 * c23;
        if ( Math.abs( determinant ) < 1.0e-6 ) {
            return null;
        }

        float r = (float) (1.0 / determinant);
        return new Vector3f(
                (-a23 * d1 - (c1 * b3 - c3 * b1) * d2 - (c2 * b1 - c1 * b2) * d3) * r,
                (-b23 * d1 - (c3 * a1 - c1 * a3) * d2 - (c1 * a2 - c2 * a1) * d3) * r,
                (-c23 * d1 - (b1 * a3 - b3 * a1) * d2 - (b2 * a1 - b1 * a2) * d3) * r );
    }

}
