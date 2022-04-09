package com.apet2929.core.light;

import org.joml.Vector3f;

import java.awt.*;

public class SpotLight {

    private PointLight pointLight;

    private Vector3f coneDirection;

    private float cutoff;

    public SpotLight(PointLight pointLight, Vector3f coneDirection, float cutoff) {
        this.pointLight = pointLight;
        this.coneDirection = coneDirection;
        this.cutoff = cutoff;
    }


    public SpotLight(SpotLight spotLight) {
        this(new PointLight(spotLight.getPointLight()), new Vector3f(spotLight.getConeDirection()), 0);
        setCutoff(spotLight.getCutoff());
    }

    /**
     * @param angle The cutoff angle in degrees
     */
    public void setCutoffAngle(float angle) {
        setCutoff((float)Math.cos(Math.toRadians(angle)));
    }

    public void setCutoff(float cutoff) {
        this.cutoff = cutoff;
    }

    public PointLight getPointLight() {
        return pointLight;
    }

    public Vector3f getConeDirection() {
        return coneDirection;
    }

    public float getCutoff() {
        return cutoff;
    }

    public void setPointLight(PointLight pointLight) {
        this.pointLight = pointLight;
    }

    public void setConeDirection(Vector3f coneDirection) {
        this.coneDirection = coneDirection;
    }
}
