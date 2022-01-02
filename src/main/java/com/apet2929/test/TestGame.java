package com.apet2929.test;

import com.apet2929.core.ILogic;
import com.apet2929.core.ObjectLoader;
import com.apet2929.core.RenderManager;
import com.apet2929.core.WindowManager;
import com.apet2929.core.entity.Model;
import com.apet2929.core.entity.Texture;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

public class TestGame implements ILogic{

    private int direction = 0;
    private float color = 0.0f;

    private final RenderManager renderer;
    private final ObjectLoader loader;
    private final WindowManager window;

    private Model model;

    public TestGame(){
        renderer = new RenderManager();
        window = Launcher.getWindow();
        loader = new ObjectLoader();

    }

    @Override
    public void init() throws Exception {
        renderer.init();

        float[] vertices = {
                -0.5f, 0.5f, 0f,    //  Top left vertex
                -0.5f, -0.5f, 0f,   //  Bottom left vertex
                0.5f, -0.5f, 0f,    //  Bottom right vertex
                0.5f, 0.5f, 0f,     //  Top right
//                -0.5f, 0.5f, 0f     //  Top left
//                0.5f, -0.5f, 0f,    //  Bottom right vertex
        };



        int[] indices = {
                0,1,3,
                3,1,2
        };

        float[] textureCoords = {
                0,0,
                0,1,
                1,1,
                1,0
        };

        model = loader.loadModel(vertices, textureCoords, indices);
        model.setTexture(new Texture(loader.loadTexture("textures/tree.png")));

    }

    @Override
    public void input() {
        if(window.isKeyPressed(GLFW.GLFW_KEY_UP)){
            direction = 1;
        }

        if(window.isKeyPressed(GLFW.GLFW_KEY_DOWN))
            direction = -1;

        if(window.isKeyPressed(GLFW.GLFW_KEY_ENTER))
            System.out.println(direction);
    }

    @Override
    public void update() {
        color += direction * 0.001f;

        if(color > 1){
            color = 1.0f;
        }
        else if(color <= 0) {
            color = 0.0f;
        }
    }

    @Override
    public void render() {
        if(window.isResize()) {
            GL11.glViewport(0, 0, window.getWidth(), window.getHeight());
            window.setResize(true);
        }

        window.setClearColour(color, color, color, 0.0f);
        renderer.clear();
//        renderer.render(shaftModel);
        renderer.render(model);
    }

    @Override
    public void cleanup() {
        renderer.cleanup();
        loader.cleanup();
    }
}
