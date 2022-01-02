package com.apet2929.core.entity;

public class Triangle {
    public float[] pa;
    public float[] pb;
    public float[] pc;

    public Triangle(float[] pa, float[] pb, float[] pc) {
        this.pa = pa;
        this.pb = pb;
        this.pc = pc;
    }

    public float[] getPoints(){
        return new float[]{
                pa[0], pa[1], pa[2],
                pb[0], pb[1], pb[2],
                pc[0], pc[1], pc[2]
        };
    }



}


