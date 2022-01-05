package com.apet2929.core.utils;

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
}
