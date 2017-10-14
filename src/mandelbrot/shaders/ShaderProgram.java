package mandelbrot.shaders;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

public abstract class ShaderProgram {
	private int programID;
	private int vertShaderID;
	private int fragShaderID;
	
	public ShaderProgram(String vertPath, String fragPath){
		vertShaderID = loadShader(vertPath, GL20.GL_VERTEX_SHADER);
		fragShaderID = loadShader(fragPath, GL20.GL_FRAGMENT_SHADER);
		programID = GL20.glCreateProgram();
		GL20.glAttachShader(programID, vertShaderID);
		GL20.glAttachShader(programID, fragShaderID);
		bindAttributes();
		GL20.glLinkProgram(programID);
		GL20.glValidateProgram(programID);
		getAllUniforms();
	}
	
	private static int loadShader(String file, int type){
		StringBuilder shaderSource = new StringBuilder();
        try{
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while((line = reader.readLine())!=null){
                shaderSource.append(line).append("//\n");
            }
            reader.close();
        }catch(IOException e){
            e.printStackTrace();
            System.exit(-1);
        }
        int shaderID = GL20.glCreateShader(type);
        GL20.glShaderSource(shaderID, shaderSource);
        GL20.glCompileShader(shaderID);
        if(GL20.glGetShaderi(shaderID, GL20.GL_COMPILE_STATUS )== GL11.GL_FALSE){
            System.out.println(GL20.glGetShaderInfoLog(shaderID, 500));
            System.err.println("Could not compile shader!");
            System.exit(-1);
        }
        return shaderID;
	}
	
	protected abstract void bindAttributes();
	
	protected void bindAttribute(int attribute, String shader_var_name){
		GL20.glBindAttribLocation(programID, attribute, shader_var_name);
	}
	
	protected int getUniform(String uniform_name){
		return GL20.glGetUniformLocation(programID, uniform_name);
	}
	
	protected abstract void getAllUniforms();
	
	protected void loadFloat(int location, float value){
		GL20.glUniform1f(location, value);
	}

	protected void loadVec2(int location, Vector2f vector){
		GL20.glUniform2f(location, vector.x, vector.y);
	}

	protected void loadVec3(int location, Vector3f vector){
		GL20.glUniform3f(location, vector.x, vector.y, vector.z);
	}
	
	public void start(){
		GL20.glUseProgram(programID);
	}
	
	public void stop(){
		GL20.glUseProgram(0);
	}
	
	public void cleanUp(){
		stop();
		GL20.glDetachShader(programID, vertShaderID);
		GL20.glDetachShader(programID, fragShaderID);
		GL20.glDeleteShader(vertShaderID);
		GL20.glDeleteShader(fragShaderID);
		GL20.glDeleteProgram(programID);
	}
}
