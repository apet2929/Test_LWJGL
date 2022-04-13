package com.apet2929.core.entity;

import com.apet2929.core.physics.Box2D;
import com.apet2929.core.physics.PhysicsBody;

public class PoolBall {
    public Entity entity;
    public PhysicsBody physicsBody;
    float radius;

    public PoolBall(Entity entity) {
        this.entity = entity;
        Box2D box = Box2D.FromEntity(entity);
        box.width = 0.1f;
        box.height = 0.1f;
        this.physicsBody = new PhysicsBody(box, 1.0f);
        radius = 20.0f;
    }

    public void update(float delta, Entity walls){
        this.physicsBody.update(delta, walls.getCollisionRect());

        this.entity.setPos(this.physicsBody.getPosition().x, this.entity.getPos().y, this.physicsBody.getPosition().y);
        this.entity.setRotation(-this.physicsBody.getRotation().x * radius, this.entity.getRotation().y, -this.physicsBody.getRotation().y * radius);

    }



}
