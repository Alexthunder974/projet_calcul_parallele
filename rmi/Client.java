package rmi;

import java.rmi.RemoteException;
import raytracer.Image;
import raytracer.Scene;
import raytracer.Disp;

public class Client extends Thread {
    Scene scene;
    private ServiceDistributeur sd;
    private int x0, y0, l, h;
    private Disp disp;

    /* public void diviserImage(int l, int h){
        int height = 40;
        int width = 40;
        for (int i = 0; i<height; i++) {
            for (int j = 0; j < width; j++) {
                Image image = scene.compute(x0 + l*j/width, y0 + i * l / height, l/width, h/height);
                disp.setImage(image, x0 + l*j/width, y0 + i * l /height);
            }
        }

    } */

    public Client(Scene scene, Disp disp, ServiceDistributeur sd, int x0, int y0, int l, int h){
        this.sd = sd;
        this.x0 = x0;
        this.y0 = y0;
        this.l = l;
        this.h = h;
        this.disp = disp;
    }
    
    @Override
    public void run() {
        /* int coteCarre = 10;
        int longueurReste = l%10;
        int hauteurReste = h%10;
        for (int i = 0; i<coteCarre; i++) {
            for (int j = 0; j < coteCarre; j++) {
                try {
                    ServiceNoeud sn = sd.getNoeud();
                    Image image = sn.calculerImage(x0 + l*j / coteCarre, y0 + i * l / coteCarre, l / coteCarre, h/ coteCarre);
                    disp.setImage(image, x0 + l*j/coteCarre, y0 + i * l /coteCarre);
                } catch (RemoteException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }       
            }
        } */
        try {
            ServiceNoeud sn = sd.getNoeud();
            Image image = sn.calculerImage(scene, x0, y0, l, h);
            disp.setImage(image, x0, y0);
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }  
    }
}
