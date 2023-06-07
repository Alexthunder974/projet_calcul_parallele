package rmi;

import raytracer.Image;
import raytracer.Scene;

public class Noeud implements ServiceNoeud {

    private Scene scene;

    public Noeud(Scene s) {
        this.scene = s;
    }

    public Image calculerImage(int x0, int y0, int w, int h) {
        return scene.compute(x0, y0, w, h);
    }
}
