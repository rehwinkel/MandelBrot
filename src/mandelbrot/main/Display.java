package mandelbrot.main;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.DisplayMode;

public class Display {
	private static int width_a;
	private static int height_a;
	
	public static void createDisplay(int width, int height, String title){
		try {
			org.lwjgl.opengl.Display.setDisplayMode(new DisplayMode(width, height));
			org.lwjgl.opengl.Display.setTitle(title);
			org.lwjgl.opengl.Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
		
		width_a = width;
		height_a = height;
	}
	
	public static void updateDisplay(int FPS){
		org.lwjgl.opengl.Display.update();
		org.lwjgl.opengl.Display.sync(FPS);
	}
	
	public static void closeDisplay(){
		org.lwjgl.opengl.Display.destroy();
		System.exit(0);
	}

	public static int getWidth() {
		return width_a;
	}

	public static int getHeight() {
		return height_a;
	}
	
	public static int getX(){
		return org.lwjgl.opengl.Display.getX();
	}
	
	public static int getY(){
		return org.lwjgl.opengl.Display.getY();
	}
}
