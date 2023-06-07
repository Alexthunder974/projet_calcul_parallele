package rmi;

public class Client extends Thread {
    private ServiceDistributeur sd;
    private int x0, y0, l, h;

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

    public Client(ServiceDistributeur sd, int x0, int y0, int l, int h){
        this.sd = sd;
        this.x0 = x0;
        this.y0 = y0;
        this.l = l;
        this.h = h;
    }
    
    @Override
    public void run() {
        int coteCarre = 10;
        int longueurReste = l%10;
        int hauteurReste = h%10;
        for (int i = 0; i<height; i++) {
            for (int j = 0; j < width; j++) {
                ServiceNoeud sn = sd.getNoeud();
                /* sn.calculerImage(i, coteCarre, longueurReste, hauteurReste);
                Image image = scene.compute(x0 + l*j/width, y0 + i * l / height, l/width, h/height);
                disp.setImage(image, x0 + l*j/width, y0 + i * l /height); */
            }
        }

    }
}
