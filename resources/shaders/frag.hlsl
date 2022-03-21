#version 330 core

in vec2 TexCoord;
out vec4 FragColor;

uniform mat4 texCoordTransform;
uniform sampler2D aTexture;


void main()
{

	vec4 transformedTexCoord = texCoordTransform * vec4(TexCoord,0,1);
	
	FragColor = texture(aTexture, vec2(transformedTexCoord.x, transformedTexCoord.y));

}
