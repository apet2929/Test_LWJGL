package com.apet2929.core.physics;

import com.apet2929.core.entity.Entity;
import org.joml.Vector2f;

public class Box2D {

    public Vector2f origin;
    public float width, height;

    public Box2D(float x, float y, float width, float height) {
//        TODO : Implement rotation
        this.origin = new Vector2f(x - width/2.0f, y - height/2.0f);
        this.width = width;
        this.height = height;

    }

    public static Box2D FromEntity(Entity entity) {
//        Assumes the model is a 1x1x1 cube
        return new Box2D(entity.getPos().x, entity.getPos().z, entity.getScale().x, entity.getScale().z);
    }

    public boolean checkCollision(Box2D other) {
        float halfWidth = other.width / 2.0f;
        float halfHeight = other.height / 2.0f;
        Vector2f l1 = new Vector2f(other.origin.x - halfWidth, other.origin.y - halfHeight);
        Vector2f l2 = new Vector2f(other.origin.x + halfWidth, other.origin.y + halfHeight);

        halfWidth = this.width / 2.0f;
        halfHeight = this.height / 2.0f;
        Vector2f r1 = new Vector2f(this.origin.x - halfWidth, this.origin.y - halfHeight);
        Vector2f r2 = new Vector2f(this.origin.x + halfWidth, this.origin.y + halfHeight);
        // If one rectangle is on left side of other
//        if (RectA.X1 < RectB.X2 && RectA.X2 > RectB.X1 &&
//                RectA.Y1 < RectB.Y2 && RectA.Y2 > RectB.Y1)

        return (l1.x < r2.x && l2.x > r1.x && l1.y < r2.y && l2.y > r1.y);
    }

}
