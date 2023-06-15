package rmi;

import raytracer.Disp;
import raytracer.Image;
import raytracer.Scene;

import java.rmi.RemoteException;
import java.time.Duration;
import java.time.Instant;

public class Client extends Thread {
    Scene scene;
    private ServiceDistributeur sd;
    private int x0, y0, l, h;
    private Disp disp;

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
                sn = sd.getNoeud();
                if (sn == null) {
                    System.out.println("erreur : aucun noeud disponible");
                    System.exit(1);
                }
                Instant debut = Instant.now();
                Image image = sn.calculerImage(this.scene, this.x0, this.y0, this.l, this.h);
                Instant fin = Instant.now();
                if (image != null) {
                    long duree = Duration.between(debut, fin).toMillis();
                    System.out.println("portion d'image calculée en : "+duree+" ms");
                    disp.setImage(image, x0, y0);
                    break;
                }
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
