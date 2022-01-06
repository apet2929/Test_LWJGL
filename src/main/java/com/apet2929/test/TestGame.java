package com.apet2929.test;

import com.apet2929.core.*;
import com.apet2929.core.entity.*;
import com.apet2929.core.mouse.MouseInput;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.CallbackI;

import java.awt.*;

import static com.apet2929.core.utils.Consts.CAMERA_STEP;
import static com.apet2929.core.utils.Consts.MOUSE_SENSITIVITY;

public class TestGame implements ILogic{



    private final RenderManager renderer;
    private final ModelLoader loader;
    private final WindowManager window;

    private Entity complexModelEntity;
    private Camera camera;
    private PointLight light;

    Vector3f cameraInc;
    Vector3f ambientLight;

    public TestGame(){
        renderer = new RenderManager();
        window = Launcher.getWindow();
        loader = new ModelLoader();
        camera = new Camera();
        cameraInc = new Vector3f(0,0,0);
    }

    @Override
    public void init() throws Exception {
        renderer.init();

        final Texture dirt = new Texture(loader.loadTexture("textures/dirt.png"));
        Material material = new Material(dirt, 5.0f);
        Model complexModel = loader.createModelFromFile("textures/pp.obj");
        complexModel.setMaterial(material);
        complexModelEntity = new Entity(complexModel);

        PointLight.Attenuation att = new PointLight.Attenuation(1.0f, 0.5f, 0.5f);
        light = new PointLight(new Vector3f(10.0f, 0, 0), new Vector3f(3.0f, 3.0f, 0.0f), 0.5f, att);
        ambientLight = new Vector3f(0.3f, 0.3f, 0.3f);
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

        //  TODO : Figure out how I broke the camera movement

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
            renderer.setLighting(light, ambientLight, camera);
            renderer.renderEntity(complexModelEntity, camera);
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
