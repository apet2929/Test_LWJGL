package com.apet2929.core.entity;

import java.util.Arrays;

public class Square {
    /*
    I wrote this class
    Do not use with the tutorial!
     */
    private Triangle ta, tb;
    private float[] vertices;
    public static final int[] indices = {
            0, 1, 3,
            3, 1, 2
    };

    public Square(float[] top_left, float[] top_right, float[] bottom_left, float[] bottom_right) {
//        -0.5f, 0.0f, 0f,    //  Top left vertex
//                -0.5f, -0.2f, 0f,   //  Bottom left vertex
//                0.5f, -0.2f, 0f,    //  Bottom right vertex
//                0.5f, -0.2f, 0f,    //  Bottom right vertex
//                0.5f, 0.0f, 0f,     //  Top right
//                -0.5f, 0.0f, 0f     //  Top left

        ta = new Triangle(top_left, bottom_left, bottom_right);
        tb = new Triangle(bottom_right, top_right, top_left);
        vertices = getVertices();
    }

    public Square(float x, float y, float width, float height){
        float[] top_left = {x, y+height, 0.0f};
        float[] top_right = {x+width, y+height, 0.0f};
        float[] bottom_left = {x, y, 0.0f};
        float[] bottom_right = {x+width, y, 0.0f};

        ta = new Triangle(top_left, bottom_left, bottom_right);
        tb = new Triangle(bottom_right, top_right, top_left);
        vertices = getVertices();
    }

    public float[] getVertices(){
        float[] tap = ta.getPoints();
        float[] tbp = tb.getPoints();

        float[] result = Arrays.copyOf(tap, tap.length+ tbp.length);
        System.arraycopy(tbp, 0, result, tap.length, tbp.length);
        return result;
    }
}
