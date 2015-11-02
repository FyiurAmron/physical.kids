package vax.physics;

import vax.math.*;

public class SphereBody extends CenteredBody {
    protected float rotationAngle;
    protected float rotationSpeed;
    protected int rotationAxis;
    protected float rotationVelocityThreshold;

    public SphereBody(float mass, float radius) {
        super(mass, radius);
        restitution =  0.75f;
        rotationVelocityThreshold = 0.15f;
    }

    public Matrix4f createRotationMatrix4() {
        switch (rotationAxis) {
            case Vector3f.OX:
                return Matrix4f.createRotationX(rotationAngle);
            case Vector3f.OY:
                return Matrix4f.createRotationY(rotationAngle);
            case Vector3f.OZ:
                return Matrix4f.createRotationZ(rotationAngle);
        }
        throw new IllegalArgumentException();
    }


    @Override
    public void timeStep(float deltaT) {
        super.timeStep(deltaT);
        float v = velocity.length();
        if (v < rotationVelocityThreshold)
            return;
        //Console.WriteLine( Velocity.length() );
            /*
            rotationAngle += v * deltaT * rotationSpeed;
            transform.setScaleAndRotation( createRotationMatrix4() );
            */
    }

    public float getRotationAngle() {
        return rotationAngle;
    }

    public void setRotationAngle(float rotationAngle) {
        this.rotationAngle = rotationAngle;
    }

    public float getRotationSpeed() {
        return rotationSpeed;
    }

    public void setRotationSpeed(float rotationSpeed) {
        this.rotationSpeed = rotationSpeed;
    }

    public int getRotationAxis() {
        return rotationAxis;
    }

    public void setRotationAxis(int rotationAxis) {
        this.rotationAxis = rotationAxis;
    }

    public float getRotationVelocityThreshold() {
        return rotationVelocityThreshold;
    }

    public void setRotationVelocityThreshold(float rotationVelocityThreshold) {
        this.rotationVelocityThreshold = rotationVelocityThreshold;
    }
}

