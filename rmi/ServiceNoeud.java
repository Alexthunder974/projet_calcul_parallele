package rmi;

import raytracer.Image;
import raytracer.Scene;

import java.rmi.Remote;

public interface ServiceNoeud extends Remote {

    Image calculerImage(Scene scene, int x0, int y0, int w, int h);
}
