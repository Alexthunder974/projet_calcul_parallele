import raytracer.Image;
import raytracer.Scene;

public interface ServiceNoeud {

    Image calculerImage(Scene scene, int x0, int y0, int w, int h);
}
