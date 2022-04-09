package com.apet2929.core;

import com.apet2929.core.entity.Entity;
import com.apet2929.core.entity.Transformation;
import com.apet2929.core.light.DirectionalLight;
import com.apet2929.core.light.PointLight;
import com.apet2929.core.utils.Utils;
import com.apet2929.test.Launcher;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.opengl.*;

public class RenderManager {
    private final WindowManager window;
    private ShaderManager shader;

    private float specularPower;

    public RenderManager(){
        window = Launcher.getWindow();
        specularPower = 10f;
    }

    public void init() throws Exception {
        shader = new ShaderManager();
        shader.createVertexShader(Utils.loadResource("/shaders/vertex.vs"));
        shader.createFragmentShader(Utils.loadResource("/shaders/fragment.fs"));
        shader.link();
        shader.createUniform("textureSampler");
        shader.createUniform("transformationMatrix");
        shader.createUniform("projectionMatrix");
        shader.createUniform("viewMatrix");
//        Materials
        shader.createMaterialUniform("material");

//        Lighting
        shader.createUniform("specularPower");
        shader.createUniform("ambientLight");
        shader.createPointLightUniform("pointLight");
        shader.createDirectionalLightUniform("directionalLight");
    }

    public void renderEntity(Entity entity, Camera camera) throws Exception {
        if(canRender()) {
            setEntityCameraUniforms(entity, camera);
            bindEntityVertices(entity);
            bindEntityTexture(entity);
            drawEntity(entity);
            cleanupRender();
        }
        else {
            throwRenderNotStartedError();
        }

    }

    public void setLighting(PointLight pointLight, DirectionalLight directionalLight, Vector3f ambientLight, Camera camera) throws Exception{
        if(canRender()) {
            setLightUniforms(pointLight, directionalLight, ambientLight, Transformation.getViewMatrix(camera));
        } else {
            throwRenderNotStartedError();
        }
    }

    public void render(Entity entity, Camera camera){
        clear();
        shader.bind();
        shader.setUniform("textureSampler", 0);
        shader.setUniform("transformationMatrix", Transformation.createTransformationMatrix(entity));
        shader.setUniform("projectionMatrix", window.updateProjectionMatrix());
        shader.setUniform("viewMatrix", Transformation.getViewMatrix(camera));
        GL30.glBindVertexArray(entity.getModel().getId());
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, entity.getModel().getTexture().getId());
        GL11.glDrawElements(GL11.GL_TRIANGLES, entity.getModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL30.glBindVertexArray(0);
        shader.unbind();

    }

    public void beginRender() {
        shader.bind();

    }

    public void endRender() {
        shader.unbind();
    }

    private void setEntityCameraUniforms(Entity entity, Camera camera) {
        shader.setUniform("textureSampler", 0);
        shader.setUniform("transformationMatrix", Transformation.createTransformationMatrix(entity));
        shader.setUniform("projectionMatrix", window.updateProjectionMatrix());
        shader.setUniform("viewMatrix", Transformation.getViewMatrix(camera));
        shader.setUniform("material", entity.getModel().getMaterial());
    }

    private void setLightUniforms(PointLight pointLight, DirectionalLight directionalLight, Vector3f ambientLight, Matrix4f viewMatrix) {
        shader.setUniform("ambientLight", ambientLight);
        shader.setUniform("directionalLight", toViewCoordinates(directionalLight, viewMatrix));
        shader.setUniform("specularPower", specularPower);
        shader.setUniform("pointLight", toViewCoordinates(pointLight, viewMatrix));

    }

    private DirectionalLight toViewCoordinates(DirectionalLight dirLight, Matrix4f viewMatrix) {
        DirectionalLight currDirLight = new DirectionalLight(dirLight);
        Vector4f dir = new Vector4f(currDirLight.getDirection(), 0);
        dir.mul(viewMatrix);
        currDirLight.setDirection(new Vector3f(dir.x, dir.y, dir.z));
        return currDirLight;
    }

    public PointLight toViewCoordinates(PointLight light, Matrix4f viewMatrix) {
        PointLight c = new PointLight(light);
        Vector3f lightPos = c.getPosition();
        Vector4f aux = new Vector4f(c.getPosition(), 1);
        aux.mul(viewMatrix);
        lightPos.x = aux.x;
        lightPos.y = aux.y;
        lightPos.z = aux.z;
        return c;
    }

    private void bindEntityVertices(Entity entity) {
        GL30.glBindVertexArray(entity.getModel().getId());
        GL20.glEnableVertexAttribArray(0);  //  Vertices
        GL20.glEnableVertexAttribArray(1);  //  Texture coordinates
        GL20.glEnableVertexAttribArray(2);  //  Normals
    }

    private void bindEntityTexture(Entity entity) {
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, entity.getModel().getTexture().getId());
    }
    private void drawEntity(Entity entity) {
        GL11.glDrawElements(GL11.GL_TRIANGLES, entity.getModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
    }

    private void cleanupRender() {
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL30.glBindVertexArray(0);
        GL30.glBindTexture(GL11.GL_TEXTURE_2D, 0);
    }

    private boolean canRender() {
        return shader.isBound();
    }

    private void throwRenderNotStartedError() throws Exception {
        throw new Exception("beginRender() not called before renderEntity()!");
    }

    public void clear(){
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
    }

    public void cleanup() {
        shader.cleanup();
    }
}
