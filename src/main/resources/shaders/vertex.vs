#version 400 core

in vec3 position;
in vec2 textureCoord;
in vec3 vertexNormal;

out vec2 fragTextureCoord;
out vec3 mvVertexNormal;
out vec3 mvVertexPos;

uniform mat4 transformationMatrix;
uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;

void main() {
    vec4 mvPos = vec4(position, 1.0);
    gl_Position = projectionMatrix * viewMatrix * transformationMatrix * mvPos;   // Order is very important here
    fragTextureCoord = textureCoord;
    mvVertexNormal = normalize(viewMatrix * vec4(vertexNormal, 0.0)).xyz;
    mvVertexPos = mvPos.xyz;
}