package rmi;

import raytracer.Image;
import raytracer.Scene;

import java.io.Serializable;

public class Noeud implements ServiceNoeud, Serializable {

    public Image calculerImage(Scene scene, int x0, int y0, int w, int h) {
        System.out.println("Calcul de l'image");
        return scene.compute(x0, y0, w, h);
    }
}
