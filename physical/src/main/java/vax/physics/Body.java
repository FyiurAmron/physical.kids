package vax.physics;

import java.util.*;
import vax.math.*;
import vax.util.*;

public class Body {
    public static final float KINEMATIC_EPSILON_SQ = 1E-2f;

    public float mass;
    protected Vector3f velocity = new Vector3f();
    protected Vector3f acceleration = new Vector3f();

    protected float restitution;
    protected float friction;
    protected Matrix4f transform;

    List<Action<Body>> constraints = new ArrayList<>();
    List<Action<Body>> forces = new ArrayList<>();

    //        Vector3f oldPosition = new Vector3f();
//        Vector3f position;
//        public Vector3f position { get { return position; } set { position.set( value ); } }
//        public bool FixedPosition { get; set; } // use mass = float.PositiveInfinity instead

    public Body ( float mass ) {
        this.mass = mass;
        transform = new Matrix4f( true );
        restitution = 1.0f;
    }

    public Body () {
        this(Float.POSITIVE_INFINITY);
    }
        /*
        public Body ( float mass, Vector3f initialPosition ) : this( mass ) {
            position = new Vector3f( initialPosition );
        }*/

    public Body ( float mass, Matrix4f initialTransform ) {
     this( mass );
        transform = new Matrix4f( initialTransform );
    }


    public void applyForce ( float forceX, float forceY, float forceZ ) {
        float scale = 1f / mass;
        acceleration.add( forceX * scale, forceY * scale, forceZ * scale );
    }

    public void applyForce ( Vector3f force ) {
        acceleration.add( force.getScaled( 1 / mass ) );
    }

    public void applyImpulse ( float velocityX, float velocityY, float velocityZ ) {
        float scale = 1f / mass;
        velocity.add( velocityX * scale, velocityY * scale, velocityZ * scale );
    }

    public void applyImpulse ( Vector3f impulse ) {
        velocity.add( impulse.getScaled( 1 / mass ) );
    }

    Vector3f getForce () {
        Vector3f v3f = new Vector3f( acceleration );
        v3f.scale( mass );
        return v3f;
    }

    public void timeStep ( float deltaT ) {
        for ( Action<Body> force : forces ) {
            force.exec( this );
        }

        //oldPosition.set( position );
        velocity.add( acceleration.getScaled( deltaT ) );
        //position.add( velocity.getScaled( deltaT ) );
        if ( velocity.lengthSq() < KINEMATIC_EPSILON_SQ )
            velocity.setToZero();
        else
            transform.addTranslation( velocity.getScaled( deltaT ) );
        acceleration.setToZero();

        for ( Action<Body> constraint : constraints ) {
            constraint.exec( this );
        }
    }

    public Vector3f getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector3f velocity) {
        this.velocity = velocity;
    }

    public Vector3f getAcceleration() {
        return acceleration;
    }

    public void setAcceleration(Vector3f acceleration) {
        this.acceleration = acceleration;
    }

    public float getRestitution() {
        return restitution;
    }

    public void setRestitution(float restitution) {
        this.restitution = restitution;
    }

    public float getFriction() {
        return friction;
    }

    public void setFriction(float friction) {
        this.friction = friction;
    }

    public Matrix4f getTransform() {
        return transform;
    }

    public void setTransform(Matrix4f transform) {
        this.transform = transform;
    }

    public List<Action<Body>> getConstraints() {
        return constraints;
    }

    public void setConstraints(List<Action<Body>> constraints) {
        this.constraints = constraints;
    }

    public List<Action<Body>> getForces() {
        return forces;
    }

    public void setForces(List<Action<Body>> forces) {
        this.forces = forces;
    }

    public float getMass() {
        return mass;
    }

    public void setMass(float mass) {
        this.mass = mass;
    }
}

