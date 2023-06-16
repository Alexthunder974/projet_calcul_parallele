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
                Instant debut = Instant.now();
                sn = sd.getNoeud();
                Instant fin = Instant.now();
                long duree = Duration.between(debut, fin).toMillis();
                System.out.println("get noeud en : "+duree+" ms");
                if (sn == null) {
                    System.out.println("erreur : aucun noeud disponible");
                    System.exit(1);
                }


                Instant debut_image = Instant.now();
                Image image = sn.calculerImage(this.scene, this.x0, this.y0, this.l, this.h);
                Instant fin_image = Instant.now();
                if (image != null) {
                    long duree_image = Duration.between(debut_image, fin_image).toMillis();
                    System.out.println("portion d'image calculée en : "+duree_image+" ms");
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
