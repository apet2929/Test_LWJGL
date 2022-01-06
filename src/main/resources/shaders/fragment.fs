#version 400 core

struct Attenuation
{
    float constant;
    float linear;
    float exponent;
};

struct PointLight
{
    vec3 color;
    vec3 position;  //  Assuming position in view coordinates
    float intensity;
    Attenuation att;
};

struct Material
{
    vec4 ambient;
    vec4 diffuse;
    vec4 specular;
    int hasTexture;
    float reflectance;
};


in vec2 fragTextureCoord;
in vec3 mvVertexNormal;
in vec3 mvVertexPos;

out vec4 fragColor;

uniform sampler2D textureSampler;
uniform vec3 ambientLight;
uniform float specularPower;
uniform Material material;
uniform PointLight pointLight;
uniform vec3 camera_pos;

vec4 ambientC;
vec4 diffuseC;
vec4 speculrC;

void setupColors(Material material, vec2 textCoord) {
    if(material.hasTexture == 1) {
        ambientC = texture(textureSampler, textCoord);
        diffuseC = ambientC;
        speculrC = ambientC;
    }
    else {
        ambientC = material.ambient;
        diffuseC = material.diffuse;
        speculrC = material.specular;
    }
}

vec4 calcPointLight(PointLight light, vec3 position, vec3 normal) {
    vec4 diffuseColor = vec4(0,0,0,0);
    vec4 specColor = vec4(0,0,0,0);

    // Diffuse Light
    vec3 light_direction = light.position - position;
    vec3 to_light_source = normalize(light_direction);
    float diffuseFactor = max(dot(normal, to_light_source), 0.0);
    diffuseColor = diffuseC * vec4(light.color, 1.0) * light.intensity * diffuseFactor;

    // Specular Light
    vec3 camera_direction = normalize(-position);   // normalize(camera_pos - position); but camera_pos is 0 in view space
    vec3 from_light_source = -to_light_source;
    vec3 reflected_light = normalize(reflect(from_light_source, normal));
    float specularFactor = max(dot(camera_direction, reflected_light), 0.0);
    specularFactor = pow(specularFactor, specularPower);
    specColor = speculrC * specularFactor * material.reflectance * vec4(light.color, 1.0);

    // Attenuation
    float distance = length(light_direction);
    float attenuationInv = light.att.constant + light.att.linear * distance + light.att.exponent * distance * distance;
    return (diffuseColor + specColor) / attenuationInv;
}

void main() {
    setupColors(material, fragTextureCoord);
    vec4 diffuseSpecularComp = calcPointLight(pointLight, mvVertexPos, mvVertexNormal);
    fragColor = ambientC * vec4(ambientLight, 1) + diffuseSpecularComp;
}