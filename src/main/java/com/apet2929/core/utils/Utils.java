package com.apet2929.core.utils;

import org.joml.Vector2f;
import org.lwjgl.system.MemoryUtil;

import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Utils {

    private static final Vector2f HORIZONTAL = new Vector2f(1, 0);

    public static FloatBuffer storeDataInFloatBuffer(float[] data) {
        FloatBuffer buffer = MemoryUtil.memAllocFloat(data.length);
        buffer.put(data).flip();
        return buffer;
    }
    public static IntBuffer storeDataInIntBuffer(int[] data) {
        IntBuffer buffer = MemoryUtil.memAllocInt(data.length);
        buffer.put(data).flip();
        return buffer;
    }

    public static float[] concatWithArrayCopy(float[] vertices, float[] vertices1) {
        float[] result = Arrays.copyOf(vertices, vertices.length + vertices1.length);
        System.arraycopy(vertices1, 0, result, vertices.length, vertices1.length);
        return result;
    }

    public static String loadResource(String filename) throws Exception {
        String result;
        try(
            InputStream in = Utils.class.getResourceAsStream(filename);
            Scanner scanner = new Scanner(in, StandardCharsets.UTF_8.name())) {
            result = scanner.useDelimiter("\\A").next();
        }
        return result;

    }

    public static List<String> readAllLines(String filename) throws Exception {
        List<String> lines = new ArrayList<>();
        File file = new File(filename);
        Scanner scanner = new Scanner(file);
        while(scanner.hasNextLine()) {
            lines.add(scanner.nextLine());
        }
        scanner.close();
        return lines;
    }

    public static float getAngle(Vector2f in) {
        return in.angle(HORIZONTAL);
    }

    public static Vector2f rotate(Vector2f in, float radians) {
        double sin = Math.sin(radians);
        double cos = Math.cos(radians);
        float length = in.length();

        return new Vector2f((float) (length * cos), (float)(length * sin));
    }
    public static Vector2f incRotation(Vector2f in, float radians) {
        double sin = Math.sin(radians);
        double cos = Math.cos(radians);
//newX = oldX * cos(angle) - oldY * sin(angle)
//newY = oldX * sin(angle) + oldY * cos(angle)

        double x = (in.x * cos) - (in.y * sin);
        double y = (in.x * sin) + (in.y * cos);

        return new Vector2f((float)x, (float)y);
    }

    public static Vector2f setLength(Vector2f in, float length) {
        float angle = getAngle(in);

        double sin = Math.sin(angle);
        double cos = Math.cos(angle);

        return new Vector2f((float)(cos * length), (float)(sin * length));
    }

}
