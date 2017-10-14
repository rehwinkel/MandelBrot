package mandelbrot.run;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;

import mandelbrot.main.Display;
import mandelbrot.main.Loader;
import mandelbrot.main.RawModel;
import mandelbrot.main.Renderer;
import mandelbrot.shaders.StaticShader;

public class Run {
	private static boolean screen;

	public static void main(String[] args){
		Display.createDisplay(1000, 1000, "Mandelbrot");
		
		Loader loader = new Loader();
		Renderer renderer = new Renderer();
		StaticShader shader = new StaticShader();
		
		float[] vertices = {
			-1, 1, 0,
			-1, -1, 0,
			1, -1, 0,
			
			1, -1, 0,
			1, 1, 0,
			-1, 1, 0
		};
		
		RawModel model = loader.loadToVAO(vertices);
		
		Vector3f offset = new Vector3f(0, 0, 1.6F);
		
		//float a = 0;
		
		while(!org.lwjgl.opengl.Display.isCloseRequested()){
			move(offset);
			
			renderer.prepare();
			
			shader.start();
			shader.loadScreenSizes(Display.getWidth(), Display.getHeight());
			shader.loadOffsetAndZoom(offset.x, offset.y, offset.z);
			
			//float r = 0.7885f;
			//a += 0.003F;

			//shader.loadJulia((float) (r*Math.cos(a * Math.PI)), (float) (r*Math.sin(a * Math.PI)), true);
			shader.loadJulia(-0.835f, -0.2321f, false);
			
			renderer.render(model);
			shader.stop();
			
			Display.updateDisplay(60);
			
			if(screen){
				int width = Display.getWidth();
				int height = Display.getHeight();
				int bpp = 4;
				
				ByteBuffer buffer = BufferUtils.createByteBuffer(width * height * bpp);
				
				GL11.glReadPixels(0, 0, width, height, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buffer);

				BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
				   
				for(int x = 0; x < width; x++) {
				    for(int y = 0; y < height; y++) {
				        int i = (x + (width * y)) * bpp;
				        int red = buffer.get(i) & 0xFF;
				        int grn = buffer.get(i + 1) & 0xFF;
				        int blu = buffer.get(i + 2) & 0xFF;
				        image.setRGB(x, height - (y + 1), (0xFF << 24) | (red << 16) | (grn << 8) | blu);
				    }
				}
				
				File file = new File("C:\\Users\\Ian\\Desktop\\screen.png");

				try {
					file.createNewFile();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				
				try {
				    ImageIO.write(image, "PNG", file);
				} catch (IOException e) {
					e.printStackTrace(); 
				}
				screen = false;
			}
		}
		
		shader.cleanUp();
		loader.cleanUp();
		Display.closeDisplay();
	}

	private static void move(Vector3f offset) {
		if(Keyboard.isKeyDown(Keyboard.KEY_W)){
			offset.y = offset.y + 0.02f * offset.z;
			
			if(offset.y > 1.23){
				offset.y = 1.23f;
			}
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_S)){
			offset.y = offset.y - 0.02f * offset.z;
			
			if(offset.y < -1.23){
				offset.y = -1.23f;
			}
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_D)){
			offset.x = offset.x + 0.02f * offset.z;
			
			if(offset.x > 2F){
				offset.x = 2F;
			}
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_A)){
			offset.x = offset.x - 0.02f * offset.z;
			
			if(offset.x < -2F){
				offset.x = -2F;
			}
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_SPACE)){
			offset.z /= 1.01f;
			
			if(offset.z < 1.5E-10f){
				offset.z = 1.5E-10f;
			}
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)){
			offset.z *= 1.01F;
			
			if(offset.z > 1.6f){
				offset.z = 1.6f;
			}
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_P)){
			System.out.println(offset.x + "/" + offset.y + "/" + offset.z);
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_F2)){
			screen = true;
		}
	}
}
