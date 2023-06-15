package rmi;

import raytracer.Disp;
import raytracer.Image;
import raytracer.Scene;

import java.rmi.RemoteException;

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
        this.scene = scene;
        this.sd = sd;
        this.x0 = x0;
        this.y0 = y0;
        this.l = l;
        this.h = h;
        this.disp = disp;
    }
    
    @Override
    public void run() {
        ServiceNoeud sn = null;
        while (true) {
            try {
                if (!(sd.getNbNoeuds() > 0)) break;
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
            try {
                sn = sd.getNoeud();
                Image image = sn.calculerImage(this.scene, this.x0, this.y0, this.l, this.h);
                disp.setImage(image, x0, y0);
                break;
            } catch (RemoteException e) {
                // retirer le noeud de la liste
                try {
                    sd.retirerNoeud(sn);
                } catch (RemoteException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
    }
}
