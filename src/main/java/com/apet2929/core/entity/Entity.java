package com.apet2929.core.entity;

import org.joml.Vector3f;

public class Entity {

    private Model model;
    private Vector3f pos, rotation, scale;


    public Entity(Model model, Vector3f pos, Vector3f rotation, Vector3f scale) {
        this.model = model;
        this.pos = pos;
        this.rotation = rotation;
        this.scale = scale;
    }

    public Entity(Model model) {
        this.model = model;
        this.pos = new Vector3f(0.0f,0.0f,0.0f);
        this.rotation = new Vector3f(0.0f,0.0f,0.0f);
        this.scale = new Vector3f(1.0f,1.0f,1.0f);;
    }

    public void inc_pos(float x, float y, float z){
        this.pos.x += x;
        this.pos.y += y;
        this.pos.z += z;
    }

    public void setPos(float x, float y, float z){
        this.pos.x = x;
        this.pos.y = y;
        this.pos.z = z;
    }

    public void setPos(Vector3f pos) {
        this.pos.x = pos.x;
        this.pos.y = pos.y;
        this.pos.z = pos.z;
    }

    public void incRotation(float x, float y, float z){
        this.rotation.x += x;
        this.rotation.y += y;
        this.rotation.z += z;
    }

    public void setRotation(float x, float y, float z){
        this.rotation.x = x;
        this.rotation.y = y;
        this.rotation.z = z;
    }

    public void setScale(Vector3f scale) {
        this.scale = scale;
    }
    public void setScale(float x, float y, float z){
        this.scale.x = x;
        this.scale.y = y;
        this.scale.z = z;
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
}
