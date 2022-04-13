package com.apet2929.core.entity;

import com.apet2929.core.physics.PhysicsBody;
import org.joml.Vector2f;

public class PoolBall {
    public Entity entity;
    public PhysicsBody physicsBody;
    float radius;

    public PoolBall(Entity entity) {
        this.entity = entity;
        Vector2f pos = new Vector2f(entity.getPos().x, entity.getPos().y);
        this.physicsBody = new PhysicsBody(pos, 1.0f);
        radius = 20.0f;
    }

    public void update(float delta){
        this.physicsBody.update(delta);
        this.entity.setPos(this.physicsBody.getPosition().y, this.entity.getPos().y, -this.physicsBody.getPosition().x);
        this.entity.setRotation(-this.physicsBody.getRotation().x * radius, this.entity.getRotation().y, -this.physicsBody.getRotation().y * radius);
    }

}
