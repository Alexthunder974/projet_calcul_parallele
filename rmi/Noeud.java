import raytracer.Image;
import raytracer.Scene;

import java.rmi.Remote;

public class Noeud implements ServiceNoeud, Remote {

    public Image calculerImage(Scene scene, int x0, int y0, int w, int h) {
        return scene.compute(x0, y0, w, h);
    }
}
