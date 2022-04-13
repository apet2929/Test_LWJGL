package com.apet2929.core.physics;

import com.apet2929.core.utils.Consts;
import org.joml.Vector2f;

import java.util.ArrayList;

public class PhysicsBody {
    private Box2D collisionRect;
    private Vector2f rotation;
    private Vector2f velocity;
    private Vector2f rotVel;
    private Vector2f acceleration;
    private ArrayList<Vector2f> forces;
    private final float mass;

    public PhysicsBody(Box2D collisionRect, float mass) {
        this.collisionRect = collisionRect;
        this.rotation = new Vector2f(0,0);
        this.rotVel = new Vector2f(0,0);
        this.mass = mass;
        this.velocity = new Vector2f(0f,0f);
        this.acceleration = new Vector2f(0f,0f);
        this.forces = new ArrayList<>();

    }

    public void update(float delta, Box2D walls){
        applyFriction();
        collide(walls);
        Vector2f netForce = new Vector2f(0,0);
        for(Vector2f force : this.forces){
            netForce = netForce.add(force);
        }
        this.acceleration.set(netForce.div(mass));
        this.forces.clear();

        this.velocity.add(this.acceleration.x * delta, this.acceleration.y * delta);
        this.rotVel.set(this.velocity);

        this.rotation.add(-this.rotVel.y * delta, this.rotVel.x * delta);
        this.collisionRect.origin.add(this.velocity.x * delta, this.velocity.y * delta);
    }

    private void collide(Box2D other) {
        boolean collided = other.checkCollision(this.collisionRect);
        if(collided) {
            if(Consts.DEBUG){
                System.out.println("Collided!");
            }
            this.velocity.mul(-1);
        }
    }

    private void applyFriction(){
        this.applyForceCenter(new Vector2f(-this.velocity.x * 0.05f, -this.velocity.y * 0.05f));
    }

    public void applyForceCenter(Vector2f force) {
//        F = ma
        this.forces.add(force);
    }

    public Vector2f getPosition() {
        return this.collisionRect.origin;
    }

    public void setPosition(Vector2f position) {
        this.collisionRect.origin = position;
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
