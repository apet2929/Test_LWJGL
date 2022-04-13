package com.apet2929.core.mouse;

import com.apet2929.test.Launcher;
import org.joml.*;
import org.lwjgl.glfw.GLFW;

public class MouseInput {
    private final Vector2d previousPos, currentPos;
    private final Vector2f displVec;
    private boolean inWindow = false, leftButtonPressed = false, rightButtonPressed = false;

    public MouseInput() {
        previousPos = new Vector2d(-1,-1);
        currentPos = new Vector2d(0,0);
        displVec = new Vector2f();
    }

    public void init() {
        GLFW.glfwSetCursorPosCallback(Launcher.getWindow().getWindow(), ((window, xpos, ypos) -> {
            currentPos.x = xpos;
            currentPos.y = ypos;

        }));
        GLFW.glfwSetCursorEnterCallback(Launcher.getWindow().getWindow(), ((window, entered) -> {
            inWindow = entered;
        }));
        GLFW.glfwSetMouseButtonCallback(Launcher.getWindow().getWindow(), ((window, button, action, mods) -> {
            leftButtonPressed = button == GLFW.GLFW_MOUSE_BUTTON_1 && action == GLFW.GLFW_PRESS;
            rightButtonPressed = button == GLFW.GLFW_MOUSE_BUTTON_2 && action == GLFW.GLFW_PRESS;
        }));



    }

    public void input() {
        displVec.x = 0;
        displVec.y = 0;
        if(previousPos.x > 0 && previousPos.y > 0 && inWindow) {
            double x = currentPos.x - previousPos.x;
            double y = currentPos.y - previousPos.y;

            boolean rotX = x != 0;
            boolean rotY = y != 0;
            if(rotX)
                displVec.y = (float) x;
            if(rotY)
                displVec.x = (float) y;
        }
        previousPos.x = currentPos.x;
        previousPos.y = currentPos.y;

    }



    public Vector2f getDisplVec() {
        return displVec;
    }

    public boolean isLeftButtonPressed() {
        return leftButtonPressed;
    }

    public boolean isRightButtonPressed() {
        return rightButtonPressed;
    }

    public Vector3f getNormalizedMousePos(int windowWidth, int windowHeight) {
        float x = (float)(2.0f * currentPos.x) / (float) windowWidth - 1.0f;
        float y = 1.0f - (float)(2 * currentPos.y) / (float) windowHeight;
        float z = 1.0f;
        return new Vector3f(x, y, z);
    }

    public Vector3f mouseToWorldCoordinates(int windowWidth, int windowHeight, Matrix4f projectionMatrix, Matrix4f viewMatrix){
//        https://antongerdelan.net/opengl/raycasting.html
        Vector3f nds = this.getNormalizedMousePos(windowWidth, windowHeight);
        Vector4f ray_clip = new Vector4f(nds.x, nds.y, -1.0f, 1.0f);
        Vector4f ray_eye = ray_clip.mul(projectionMatrix.invert());
        ray_eye = new Vector4f(ray_eye.x, ray_eye.y, -1.0f, 0.0f);
//        vec3 ray_wor = (inverse(view_matrix) * ray_eye).xyz;
//// don't forget to normalise the vector at some point
//ray_wor = normalise(ray_wor);
        Vector4f world = ray_eye.mul(viewMatrix.invert());
//        world.normalize();
        return new Vector3f(world.x, world.y, world.z);
    }
}
