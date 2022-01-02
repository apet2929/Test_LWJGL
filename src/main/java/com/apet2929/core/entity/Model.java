package com.apet2929.core.entity;

public class Model {

    private int id;
    private int vertexCount;
    private Texture texture;

    public Model(int id, int vertexCount){
        this.id = id;
        this.vertexCount = vertexCount;
    }

    public Model(int id, int vertexCount, Texture texture){
        this.id = id;
        this.vertexCount = vertexCount;
        this.texture = texture;
    }

    public Model(Model model, Texture texture){
        this.id = model.getId();
        this.vertexCount = model.getVertexCount();
        this.texture = texture;
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public int getId() {
        return id;
    }

    public int getVertexCount() {
        return vertexCount;
    }
}
