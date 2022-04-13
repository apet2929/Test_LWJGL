package com.apet2929.core.physics;

import org.joml.Vector2f;

import java.util.ArrayList;

public class PhysicsBody {
    private Vector2f position;
    private Vector2f rotation;
    private Vector2f velocity;
    private Vector2f rotVel;
    private Vector2f acceleration;
    private ArrayList<Vector2f> forces;
    private final float mass;

    public PhysicsBody(Vector2f position, float mass) {
        this.position = position;
        this.rotation = new Vector2f(0,0);
        this.rotVel = new Vector2f(0,0);
        this.mass = mass;
        this.velocity = new Vector2f(0f,0f);
        this.acceleration = new Vector2f(0f,0f);
        this.forces = new ArrayList<>();

    }

    public void update(float delta){
        Vector2f netForce = new Vector2f(0,0);

        for(Vector2f force : this.forces){
            netForce = netForce.add(force);
        }
        this.acceleration.set(netForce.div(mass));
        this.forces.clear();

        this.velocity.add(this.acceleration.x * delta, this.acceleration.y * delta);
        this.rotVel.set(this.velocity);

        this.rotation.add(this.rotVel.x * delta, this.rotVel.y * delta);
        this.position.add(this.velocity.x * delta, this.velocity.y * delta);
    }

    private void applyFriction(){
        this.applyForceCenter(new Vector2f(-this.velocity.x));
    }

    public void applyForceCenter(Vector2f force) {
//        F = ma
        this.forces.add(force);
    }

    public Vector2f getPosition() {
        return position;
    }

    public void setPosition(Vector2f position) {
        this.position = position;
    }

    public Vector2f getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector2f velocity) {
        this.velocity = velocity;
    }

    public Vector2f getAcceleration() {
        return acceleration;
    }

    public float getMass() {
        return mass;
    }

    public Vector2f getRotation() {
        return rotation;
    }
}
