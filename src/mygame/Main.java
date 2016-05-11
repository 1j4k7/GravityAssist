package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.light.PointLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import com.sun.istack.internal.logging.Logger;
import java.util.logging.Level;

/**
 * test
 * @author normenhansen
 */
public class Main extends SimpleApplication {
    private BulletAppState bulletAppState;
    private static final Logger logger = Logger.getLogger(Main.class);
    
    public static void main(String[] args) {
        Main app = new Main();
        app.start();
    }

    @Override
    public void simpleInitApp() {
        bulletAppState = new BulletAppState();
        stateManager.attach(bulletAppState);
        bulletAppState.getPhysicsSpace().setGravity(Vector3f.ZERO);
        
        createWalls();
        createObjects(1000);

        PointLight lamp_light = new PointLight();
        lamp_light.setColor(ColorRGBA.Yellow);
        lamp_light.setRadius(1000f);
        rootNode.addLight(lamp_light);
        flyCam.setMoveSpeed(20);
    }
    
    /**
     * creates the bounds of the rooms, using 6 boxes of mass 0
     * north is Z+, east is X-, top is Y+
     */
    public void createWalls() {
        Node roomNode = new Node();
        Material wallMat = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
        wallMat.setTexture("Diffuse", assetManager.loadTexture("Textures/BrickWall_diffuse.jpg"));
        Geometry northWall = new Geometry("North Wall", new Box(100, 100, 1));
        northWall.move(0, 0, 100).setMaterial(wallMat);
        roomNode.attachChild(northWall);
        Geometry southWall = new Geometry("South Wall", new Box(100, 100, 1));
        southWall.move(0, 0, -100).setMaterial(wallMat);
        roomNode.attachChild(southWall);
        Geometry eastWall = new Geometry("East Wall", new Box(1, 100, 100));
        eastWall.move(-100, 0, 0).setMaterial(wallMat);
        roomNode.attachChild(eastWall);
        Geometry westWall = new Geometry("West Wall", new Box(1, 100, 100));
        westWall.move(100, 0, 0).setMaterial(wallMat);
        roomNode.attachChild(westWall);
        Geometry topWall = new Geometry("Top Wall", new Box(100, 1, 100)); // aka ceiling
        topWall.move(0, 100, 0).setMaterial(wallMat);
        roomNode.attachChild(topWall);
        Geometry bottomWall = new Geometry("Bottom Wall", new Box(100, 1, 100)); // aka floor
        bottomWall.move(0, -100, 0).setMaterial(wallMat);
        roomNode.attachChild(bottomWall);
        RigidBodyControl roomPhy = new RigidBodyControl(0f);
        roomNode.addControl(roomPhy);
        roomPhy.setRestitution(1f);
        bulletAppState.getPhysicsSpace().add(roomPhy);
        rootNode.attachChild(roomNode);
    }
    
    /**
     * randomely generates objects inside the room
     */
    public void createObjects(int count) {
        Geometry obj;
        Box objBounds = new Box(1f, 1f, 1f);
        RigidBodyControl control;
        float x, y, z;
        for (int i = 0; i < count; i++) {
            obj = new Geometry("Box " + i, objBounds);
            obj.setMaterial(new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md"));
            obj.setLocalTranslation(randomVector().mult(90));
            control = new RigidBodyControl(1f);
            obj.addControl(control);
            bulletAppState.getPhysicsSpace().add(control);
            control.setLinearVelocity(randomVector().mult(3f));
            control.setRestitution(1f);
            rootNode.attachChild(obj);
        }
    }
    
    public static Vector3f randomVector() {
        return new Vector3f(FastMath.rand.nextFloat()*2-1,FastMath.rand.nextFloat()*2-1,FastMath.rand.nextFloat()*2-1).normalize();
    }

    @Override
    public void simpleUpdate(float tpf) {
        //TODO: add update code
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
}
