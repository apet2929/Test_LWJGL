package com.apet2929.test;

import com.apet2929.core.*;
import com.apet2929.core.entity.Cube;
import com.apet2929.core.entity.Entity;
import com.apet2929.core.entity.Model;
import com.apet2929.core.entity.Texture;
import com.apet2929.core.mouse.MouseInput;
import com.apet2929.core.utils.Consts;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

import static com.apet2929.core.utils.Consts.CAMERA_STEP;
import static com.apet2929.core.utils.Consts.MOUSE_SENSITIVITY;

public class TestGame implements ILogic{



    private final RenderManager renderer;
    private final ObjectLoader loader;
    private final WindowManager window;

    private Cube cube;
    private Cube cube2;
    private Camera camera;

    Vector3f cameraInc;

    public TestGame(){
        renderer = new RenderManager();
        window = Launcher.getWindow();
        loader = new ObjectLoader();
        camera = new Camera();
        cameraInc = new Vector3f(0,0,0);
    }

    @Override
    public void init() throws Exception {
        renderer.init();
        //  Cube
        cube = loader.loadCube();
        cube2 = loader.loadCube(new Vector3f(1.0f,0.0f,0.0f), new Vector3f(0.0f,0.0f,0.0f), 2);
        cube.getModel().setTexture(new Texture(loader.loadTexture("textures/tree.png")));
        cube2.getModel().setTexture(new Texture(loader.loadTexture("textures/dirt.png")));
    }

    @Override
    public void input() {
        cameraInc.set(0,0,0);

        if(window.isKeyPressed(GLFW.GLFW_KEY_W))
            cameraInc.z = -1;
        if(window.isKeyPressed(GLFW.GLFW_KEY_S))
            cameraInc.z = 1;

        if(window.isKeyPressed(GLFW.GLFW_KEY_A))
            cameraInc.x = -1;
        if(window.isKeyPressed(GLFW.GLFW_KEY_D))
            cameraInc.x = 1;

        if(window.isKeyPressed(GLFW.GLFW_KEY_Z))
            cameraInc.y = -1;
        if(window.isKeyPressed(GLFW.GLFW_KEY_X))
            cameraInc.y = 1;

        if(window.isKeyPressed(GLFW.GLFW_KEY_ENTER))
            System.out.println(cameraInc);

    }

    @Override
    public void update(MouseInput mouseInput) {
        camera.movePosition(cameraInc.x * CAMERA_STEP, cameraInc.y * CAMERA_STEP, cameraInc.z * CAMERA_STEP);

        if(mouseInput.isLeftButtonPressed()) {
            Vector2f rotVec = mouseInput.getDisplVec();
            camera.moveRotation(rotVec.x * MOUSE_SENSITIVITY, rotVec.y * MOUSE_SENSITIVITY, 0);
        }
    }

    @Override
    public void render() {
        if(window.isResize()) {
            GL11.glViewport(0, 0, window.getWidth(), window.getHeight());
            window.setResize(true);
        }

        window.setClearColour(0.0f, 0.0f, 0.0f, 0.0f);
        renderer.clear();
        renderer.beginRender();
        try {
            renderer.renderEntity(cube, camera);
            renderer.renderEntity(cube2, camera);
        } catch (Exception e) {
            e.printStackTrace();
        }
        renderer.endRender();

    }

    @Override
    public void cleanup() {
        renderer.cleanup();
        loader.cleanup();
    }
}
