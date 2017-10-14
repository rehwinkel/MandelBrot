package mandelbrot.shaders;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

public class StaticShader extends ShaderProgram{

	private static final String VERT_FILE = "src/mandelbrot/shaders/shader.vsh";
	private static final String FRAG_FILE = "src/mandelbrot/shaders/mandelbrot.fsh";

	private int loc_screen;
	private int loc_offset;
	private int loc_julia;
	
	public StaticShader() {
		super(VERT_FILE, FRAG_FILE);
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
	}

	@Override
	protected void getAllUniforms() {
		loc_screen = super.getUniform("screen");
		loc_offset = super.getUniform("offset");
		loc_julia = super.getUniform("julia");
	}

	public void loadScreenSizes(float width, float height){
		super.loadVec2(loc_screen, new Vector2f(width, height));
	}
	
	public void loadOffsetAndZoom(float x, float y, float z){
		super.loadVec3(loc_offset, new Vector3f(x, y, z));
	}
	
	public void loadJulia(float x, float y, boolean isJulia){
		super.loadVec3(loc_julia, new Vector3f(x, y, isJulia ? 0 : 1));
	}
}
