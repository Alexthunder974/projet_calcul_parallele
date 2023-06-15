package rmi;

import raytracer.Image;
import raytracer.Scene;


public interface ServiceNoeud extends java.rmi.Remote {

    Image calculerImage(Scene scene, int x0, int y0, int w, int h) throws java.rmi.RemoteException;
}
