package keyFunctions;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import java.awt.*;




import static keyFunctions.RBGListener.*;
import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {
    private final int width;
    private final int height;
    private final String title;
    private long glfwWindow;





    private float r,g,b,a;
    private boolean randomRBG = false;

    private static Window window = null;

    private Window() {
        this.width = 800;
        this.height = 600;
        this.title = "Jump King";
        r = 1;
        g = 1;
        b = 1;
        a = 1;
    }
    public static Window get(){
        if (window == null) {
            Window.window = new Window();
        }
        return Window.window;
    }
    public void run() throws AWTException {
        System.out.println("Window is running");
        init();
        loop();

        //Free the memory
        glfwFreeCallbacks(glfwWindow);
        glfwDestroyWindow(glfwWindow);

        //Terminate GLFW
        glfwTerminate();
        Objects.requireNonNull(glfwSetErrorCallback(null)).free();
    }
    private void init(){
        //Set up an error callback
        GLFWErrorCallback.createPrint(System.err).set();

        //Initialize GLFW
        if(!glfwInit()){
            throw new IllegalStateException("Failed to initialize GLFW");
        }
        //Configure GLFW
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        glfwWindowHint(GLFW_MAXIMIZED, GLFW_FALSE);

        //Create the window
        glfwWindow = glfwCreateWindow(this.width, this.height, this.title, NULL, NULL);
        if(glfwWindow == NULL){
            throw new RuntimeException("Failed to create window");
        }

        glfwSetCursorPosCallback(glfwWindow, MouseListener::mousePosCallback);
        glfwSetMouseButtonCallback(glfwWindow, MouseListener::mouseButtonCallback);
        glfwSetScrollCallback(glfwWindow, MouseListener::mouseScrollCallback);
        glfwSetKeyCallback(glfwWindow, KeyListener::keyCallback);


        //Make the OpenGL context current
        glfwMakeContextCurrent(glfwWindow);

        // Enable v-sync
        glfwSwapInterval(60);

        //Make the window visible
        glfwShowWindow(glfwWindow);

        GL.createCapabilities();
    }
    private void loop() throws AWTException {
        System.out.println("Window is looping");
        while(!glfwWindowShouldClose(glfwWindow)){
            //Poll for window events
            glfwPollEvents();

            glClearColor(r, g, b, a);
            glClear(GL_COLOR_BUFFER_BIT);

            if(randomRBG){
                r = getPixelRGB((int) MouseListener.getX(),(int) MouseListener.getY())[0];// Returns R-value
                g = getPixelRGB((int) MouseListener.getX(),(int) MouseListener.getY())[1];// Returns G-value
                b = getPixelRGB((int) MouseListener.getX(),(int) MouseListener.getY())[2];// Returns B-value
                a = getPixelRGB((int) MouseListener.getX(),(int) MouseListener.getY())[3];// Returns A-value
                System.out.println("RGB: " + r + " " + g + " " + b);
                randomRBG = false;
            }


             if(KeyListener.get().isKeyPressed(GLFW_KEY_ESCAPE)){
                getColorAt((int) MouseListener.getX() , (int) MouseListener.getY());
                randomRBG = true;

            }

            glfwSwapBuffers(glfwWindow);
        }
    }

}