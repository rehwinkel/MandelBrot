package mandelbrot.main;

public class RawModel {
	private int vaoID;
	private int vertexCount;

	public RawModel(int vaoID, int vertexCount) {
		super();
		this.vaoID = vaoID;
		this.vertexCount = vertexCount;
	}
	
	public int getVaoID() {
		return vaoID;
	}
	public int getVertexCount() {
		return vertexCount;
	}
	
}
