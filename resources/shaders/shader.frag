#version 330 core

in vec2 TexCoord;
//in vec4 Color;
out vec4 FragColor;

uniform sampler2D aTexture;

void main()
{
	FragColor = texture(aTexture, TexCoord);
	//FragColor = Color;
}