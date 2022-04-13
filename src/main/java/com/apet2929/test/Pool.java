package com.apet2929.test;

import com.apet2929.core.*;
import com.apet2929.core.entity.*;
import com.apet2929.core.light.DirectionalLight;
import com.apet2929.core.light.PointLight;
import com.apet2929.core.mouse.MouseInput;
import com.apet2929.core.utils.Utils;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;

import static com.apet2929.core.utils.Consts.CAMERA_STEP;
import static com.apet2929.core.utils.Consts.MOUSE_SENSITIVITY;

public class Pool implements ILogic {
    private final RenderManager renderer;
    private final ModelLoader loader;
    private final WindowManager window;

    PoolBall poolBall;
    Entity table;
    Entity arrow;
    Camera camera;

    PointLight pointLight;
    Vector3f ambientLight;
    float lightAngle;
    DirectionalLight directionalLight;

    Vector3f cameraInc;
    Vector2f aim;

    public Pool() {
        renderer = new RenderManager();
        window = Launcher.getWindow();
        loader = new ModelLoader();
        cameraInc = new Vector3f(0,0,0);
        aim = new Vector2f(1, 0);
    }

    @Override
    public void init() throws Exception {
        initEntities();
        renderer.init();
        camera = new Camera();
        camera.setPosition(0f, 40.0f, -15f);
        camera.setRotation(90.52f, -90f, 0f);

        PointLight.Attenuation att = new PointLight.Attenuation(1.0f, 0.5f, 0.5f);
        pointLight = new PointLight(new Vector3f(0.0f, 0, 0), new Vector3f(3.0f, 3.0f, 0.0f), 0.5f, att);
        ambientLight = new Vector3f(0.3f, 0.3f, 0.3f);

        Vector3f dirLightColor = new Vector3f(1.0f, 1.0f, 1.0f);
        Vector3f dirLightDirection = new Vector3f(0.0f, 0.0f, 0.0f);
        float dirLightIntensity = 0.5f;
        lightAngle = -20.0f;
        directionalLight = new DirectionalLight(dirLightColor, dirLightDirection, dirLightIntensity);
        updateDirectionalLighting();
    }

    @Override
    public void input() {
        cameraInc.set(0,0,0);

        if(window.isKeyPressed(GLFW.GLFW_KEY_W))
            aim = Utils.setLength(aim, aim.length() + 0.05f);
        if(window.isKeyPressed(GLFW.GLFW_KEY_S))
            aim = Utils.setLength(aim, aim.length() - 0.05f);

        if(window.isKeyPressed(GLFW.GLFW_KEY_A))
            aim = Utils.incRotation(aim, -0.015f);
        if(window.isKeyPressed(GLFW.GLFW_KEY_D))
            aim = Utils.incRotation(aim, 0.015f);

        if(window.isKeyPressed(GLFW.GLFW_KEY_P))
            poolBall.physicsBody.applyForceCenter(aim);
        if(window.isKeyPressed(GLFW.GLFW_KEY_O))
            poolBall.physicsBody.setPosition(new Vector2f(0,0));

        if(window.isKeyPressed(GLFW.GLFW_KEY_ENTER)) {
//            System.out.println("poolBall vel = " + poolBall.physicsBody.getVelocity());
//            System.out.println("poolBall acc = " + poolBall.physicsBody.getAcceleration());
//            System.out.println("poolBall rot = " + poolBall.entity.getRotation());
//            System.out.println("lightAngle = " + lightAngle);
//            System.out.println("camera.getRotation() = " + camera.getRotation());
//            System.out.println("camera.getPosition() = " + camera.getPosition());
            System.out.println("arrow.getPos() = " + arrow.getPos());
            System.out.println("aim = " + aim);
            System.out.println("Utils.getAngle(aim) = " + Utils.getAngle(aim));

            Vector2f angled = new Vector2f(-3,1);
            System.out.println("Utils.getAngle(angled) = " + Math.toDegrees(Utils.getAngle(angled)));

        }

    }

    @Override
    public void update(MouseInput mouseInput) {
        float delta = 0.05f;
        poolBall.update(delta);
        camera.movePosition(cameraInc.x * CAMERA_STEP, cameraInc.y * CAMERA_STEP, cameraInc.z * CAMERA_STEP);
        updateArrow();

    }

    @Override
    public void render() {
        renderer.clear();
        renderer.beginRender();
        try {
            renderer.setLighting(pointLight, directionalLight, ambientLight, camera);
            renderer.renderEntity(poolBall.entity, camera);
            renderer.renderEntity(table, camera);
            renderer.renderEntity(arrow, camera);
        } catch (Exception e) {
            System.out.println("e = " + e);
        }
        renderer.endRender();

    }

    @Override
    public void cleanup() {

    }

    private void updateDirectionalLighting() {

        if (lightAngle > 90) {
            directionalLight.setIntensity(0);
            if (lightAngle >= 360) {
                lightAngle = -90;
            }
        } else if (lightAngle <= -80 || lightAngle >= 80) {
            float factor = 1 - (float)(Math.abs(lightAngle) - 80)/ 10.0f;
            directionalLight.setIntensity(factor);
            directionalLight.getColor().y = Math.max(factor, 0.9f);
            directionalLight.getColor().z = Math.max(factor, 0.5f);
        } else {
            directionalLight.setIntensity(1);
            directionalLight.getColor().x = 1;
            directionalLight.getColor().y = 1;
            directionalLight.getColor().z = 1;
        }
        double angRad = Math.toRadians(lightAngle);
        directionalLight.getDirection().x = (float) Math.sin(angRad);
        directionalLight.getDirection().y = (float) Math.cos(angRad);
    }

    private void initEntities() throws Exception{
        Model ball_model = loader.createModelFromFile("textures/pool_ball.obj");
        final Texture ball_tex = new Texture(loader.loadTexture("textures/tree.png"));
        Material material = new Material(ball_tex, 5.0f);
        ball_model.setMaterial(material);
        Entity poolBallEntity = new Entity(ball_model);
        poolBallEntity.setPos(0,0,-10);
        poolBall = new PoolBall(poolBallEntity);

        Model tableModel = loader.createModelFromFile("textures/table.obj");
        final Texture table_tex = new Texture(loader.loadTexture("textures/dirt.png"));
        material = new Material(table_tex, 0.2f);
        tableModel.setMaterial(material);
        table = new Entity(tableModel);
        table.setPos(0,-2.88f,-10);
        table.setScale(new Vector3f(10, 0.5f, 10f));

        Model arrowModel = loader.createModelFromFile("textures/arrow.obj");
        material = new Material(table_tex, 0.5f);
        arrowModel.setMaterial(material);
        arrow = new Entity(arrowModel);


    }

    private void updateArrow(){
        arrow.setPos(poolBall.entity.getPos());
        arrow.setScale(arrow.getScale().x, arrow.getScale().y, aim.length());
        arrow.setRotation(0, (float) Math.toDegrees(Utils.getAngle(aim)), 0);
    }
}
