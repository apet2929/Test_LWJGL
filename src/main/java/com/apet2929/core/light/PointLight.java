package com.apet2929.core.light;


import org.joml.Vector3f;

public class PointLight {

    public static class Attenuation {
        private final float constant;
        private final float linear;
        private final float exponent;

        public Attenuation(float constant, float linear, float exponent) {
            this.constant = constant;
            this.linear = linear;
            this.exponent = exponent;
        }

        public float getConstant() {
            return constant;
        }

        public float getLinear() {
            return linear;
        }

        public float getExponent() {
            return exponent;
        }
    }

    private final Vector3f color;
    private final Vector3f position;
    private final float intensity;
    private final Attenuation attenuation;

    public PointLight(Vector3f color, Vector3f position, float intensity, Attenuation attenuation) {
        this.color = color;
        this.position = position;
        this.intensity = intensity;
        this.attenuation = attenuation;
    }

    public PointLight(PointLight light) {
        this.attenuation = new Attenuation(light.getAttenuation().getConstant(), light.getAttenuation().getLinear(), light.getAttenuation().getExponent());
        this.color = new Vector3f(light.color);
        this.intensity = light.getIntensity();
        this.position = new Vector3f(light.position);
    }

    public Vector3f getColor() {
        return color;
    }

    public Vector3f getPosition() {
        return position;
    }

    public float getIntensity() {
        return intensity;
    }

    public Attenuation getAttenuation() {
        return attenuation;
    }

}
