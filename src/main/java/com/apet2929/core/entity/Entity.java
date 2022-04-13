package com.apet2929.core.entity;

import com.apet2929.core.physics.Box2D;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class Entity {

    private Model model;
    private Box2D collisionRect;
    private Vector3f pos, rotation, scale;

    public Entity(Model model, Vector3f pos, Vector3f rotation, Vector3f scale) {
        this.model = model;
        this.pos = pos;
        this.rotation = rotation;
        this.scale = scale;
        this.collisionRect = Box2D.FromEntity(this);
    }

    public Entity(Model model) {
        this.model = model;
        this.pos = new Vector3f(0.0f,0.0f,0.0f);
        this.rotation = new Vector3f(0.0f,0.0f,0.0f);
        this.scale = new Vector3f(1.0f,1.0f,1.0f);
        this.collisionRect = Box2D.FromEntity(this);
    }

    public void inc_pos(float x, float y, float z){
        this.pos.x += x;
        this.pos.y += y;
        this.pos.z += z;
        updateCollisionRect();
    }

    public void setPos(float x, float y, float z){
        this.pos.x = x;
        this.pos.y = y;
        this.pos.z = z;
        updateCollisionRect();
    }

    public void setPos(Vector3f pos) {
        this.pos.x = pos.x;
        this.pos.y = pos.y;
        this.pos.z = pos.z;
        updateCollisionRect();
    }

    public void incRotation(float x, float y, float z){
        this.rotation.x += x;
        this.rotation.y += y;
        this.rotation.z += z;
        updateCollisionRect();
    }

    public void setRotation(float x, float y, float z){
        this.rotation.x = x;
        this.rotation.y = y;
        this.rotation.z = z;
        updateCollisionRect();
    }

    public void setScale(Vector3f scale) {
        this.scale = scale;
        updateCollisionRect();
    }
    public void setScale(float x, float y, float z){
        this.scale.x = x;
        this.scale.y = y;
        this.scale.z = z;
        updateCollisionRect();
    }

    public Model getModel() {
        return model;
    }

    public Vector3f getPos() {
        return pos;
    }

    public Vector3f getRotation() {
        return rotation;
    }

    public Vector3f getScale() {
        return scale;
    }

    public Box2D getCollisionRect() {
        return collisionRect;
    }

    private void updateCollisionRect(){
        this.collisionRect.origin = new Vector2f(this.pos.x, this.pos.z);
        this.collisionRect.width = this.scale.x;
        this.collisionRect.height = this.scale.z;
    }
}
