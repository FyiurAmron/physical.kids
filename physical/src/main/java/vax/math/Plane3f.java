package vax.math;

public class Plane3f {
    public Vector3f normal;
    public float distanceToOrigin;

    public Plane3f () {
        //this( new Vector3f( /* 0, 1, 0 */), 0 );
    }

    /**
     @param normal getNormal of this plane, <b>stored as a reference</b>. Pass a copy to retain only the values.
     @param distanceToOrigin
     */
    public Plane3f ( Vector3f normal, float distanceToOrigin ) {
        this.normal = normal;
        this.distanceToOrigin = distanceToOrigin;
    }

    public Plane3f ( Vector3f point1, Vector3f point2, Vector3f point3 ) {
        normal = new Vector3f();
        _set( point1, point2, point3 );
    }

    /*
     public Plane3f ( Triangle3f triangle ) {
     this( triangle.getPoint1(), triangle.getPoint2(), triangle.getPoint3() );
     }
     */
    /**
     Note: to change (mutate) the existing normal, use <code>getNormal().set(normal)</code> instead.

     @param normal
     */
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

    public float calcDistance ( Vector3f point ) {
        return normal.dot( point ) + distanceToOrigin;
    }

    public float calcDistance ( Matrix4f transform ) {
        return normal.dot( transform.getTranslationX(), transform.getTranslationY(), transform.getTranslationZ() ) + distanceToOrigin;
    }

    public void set ( Vector3f point1, Vector3f point2, Vector3f point3 ) {
        _set( point1, point2, point3 );
    }

    public void set ( Plane3f plane3f ) {
        normal.set( plane3f.normal );
        distanceToOrigin = plane3f.distanceToOrigin;
    }

    private void _set ( Vector3f point1, Vector3f point2, Vector3f point3 ) {
        normal.setToNormal( point1, point2, point3 );
        distanceToOrigin = -point1.dot( normal );
    }

    public boolean contains ( Vector3f point ) {
        return Plane3f.this.calcDistance( point ) == 0;
    }

    public boolean contains ( Line3f line ) {
        return contains( line.origin ) && line.getDirection().dot( normal ) == 0;
    }

    public boolean contains ( LineSegment3f lineSegment ) {
        return contains( lineSegment.getPoint1() ) && contains( lineSegment.getPoint2() );
    }

    public Line3f intersect ( Plane3f plane ) {
        Vector3f direction = new Vector3f( normal, plane.normal );
        Vector3f point = new Vector3f();
        //point = intersection( this, plane, new Plane3f( direction, 0 ) ); // ??
        throw new UnsupportedOperationException( "not yet implemented" ); // TODO implement
        //return new Line3f( point, direction );
    }

    /*
     public Vector3f intersection ( Plane3f plane1, Plane3f plane2, Plane3f plane3 ) {
     // FIXME: cala alg. lin. (liczenie det itd) do Matrix4f etc. i won stad; alg. do poprawy!
    // http://stackoverflow.com/a/32410473/719662 do lektury i implementacji
     float x1= plane1.createNormal().getX();
     float y1 = plane1.createNormal().getY();
     float z1 = plane1.createNormal().getZ();
     float d1 = plane1.getDistanceToOrigin();

     float x2 = plane2.createNormal().getX();
     float y2 = plane2.createNormal().getY();
     float z2 = plane2.createNormal().getZ();
     float d2 = plane2.getDistanceToOrigin();

     float x3 = plane3.createNormal().getX();
     float y3 = plane3.createNormal().getY();
     float z3 = plane3.createNormal().getZ();
     float d3 = plane3.getDistanceToOrigin();

     float a23 = y2 * z3 - y3 * z2;
     float b23 = z2 * x3 - z3 * x2;
     float c23 = x2 * y3 - x3 * y2;
     float det = x1 * a23 + y1 * b23 + z1 * c23;

     //if ( Math.abs( det ) < 1.0e-6 ) { // ??
     //return null;
     //}
     // a co, jak wartosci w macierzy beda rzedu 1E-6?? plus, co nam zasadniczo przeszkadza maly, ale wciaz niezerowy det?
    // inna rzecz, ze dzieki IEEE mozemy przeciez pozwolic dosyc spokojnie nawet na det = 0! dostaniemy infy, ktore oznaczaja "no i-section"

     float r = 1.0f / det;
     // od kiedy intersection czterech plane'ow to wektor?!
     return new Vector3f(
     ( -a23 * d1 - ( z1 * y3 - z3 * y1 ) * d2 - ( z2 * y1 - z1 * y2 ) * d3 ) * r,
     ( -b23 * d1 - ( z3 * x1 - z1 * x3 ) * d2 - ( z1 * x2 - z2 * x1 ) * d3 ) * r,
     ( -c23 * d1 - ( y1 * x3 - y3 * x1 ) * d2 - ( y2 * x1 - y1 * x2 ) * d3 ) * r );
     }
     */
}
