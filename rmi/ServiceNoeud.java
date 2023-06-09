package rmi;

import raytracer.Image;

public interface ServiceNoeud {

    Image calculerImage(int x0, int y0, int w, int h);
}
