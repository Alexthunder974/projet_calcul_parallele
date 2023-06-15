import java.time.Instant;
import java.time.Duration;
import rmi.Client;
import rmi.ServiceDistributeur;
import raytracer.Disp;
import raytracer.Scene;
import raytracer.Image;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.RemoteException;
import java.rmi.UnknownHostException;
import java.rmi.ConnectException;
import java.rmi.NotBoundException;
import java.rmi.server.ServerNotActiveException;


public class LancerRaytracer {

    public static String aide = "Raytracer : synthèse d'image par lancé de rayons (https://en.wikipedia.org/wiki/Ray_tracing_(graphics))\n\nUsage : java LancerRaytracer [fichier-scène] [largeur] [hauteur]\n\tfichier-scène : la description de la scène (par défaut simple.txt)\n\tlargeur : largeur de l'image calculée (par défaut 512)\n\thauteur : hauteur de l'image calculée (par défaut 512)\n";
     
    public static void main(String args[]){

        // Le fichier de description de la scène si pas fournie
        String fichier_description="/projet_calcul_parallele/simple.txt";

        // largeur et hauteur par défaut de l'image à reconstruire
        int largeur = 512, hauteur = 512;
        
        if(args.length > 0){
            fichier_description = args[0];
            if(args.length > 1){
                largeur = Integer.parseInt(args[1]);
                if(args.length > 2)
                    hauteur = Integer.parseInt(args[2]);
            }
        }else{
            System.out.println(aide);
        }
        
   
        // création d'une fenêtre 
        Disp disp = new Disp("Raytracer", largeur, hauteur);
        
        // Initialisation d'une scène depuis le modèle 
        Scene scene = new Scene(fichier_description, largeur, hauteur);
        
        // Calcul de l'image de la scène les paramètres : 
        // - x0 et y0 : correspondant au coin haut à gauche
        // - l et h : hauteur et largeur de l'image calculée
        // Ici on calcule toute l'image (0,0) -> (largeur, hauteur)
        
        int x0 = 0, y0 = 0;
        int l = largeur, h = hauteur;
                
        // Chronométrage du temps de calcul
        Instant debut = Instant.now();
        System.out.println("Calcul de l'image :\n - Coordonnées : "+x0+","+y0
                           +"\n - Taille "+ largeur + "x" + hauteur);

        // Affichage de l'image calculée
        try {
            // On récupère l'adresse et le port
            String adresse = args[3] == null ? "127.0.0.1" : args[3] ;
            int port = 1099;
            if(args.length > 1) port = Integer.parseInt(args[4]);
            // On récupère le registre distant
            Registry reg = LocateRegistry.getRegistry(adresse, port);
            // On récupère le service distant
            ServiceDistributeur sd = (ServiceDistributeur) reg.lookup("distributeur");

            int nbNoeuds = sd.getNbNoeuds();
            int pas = l / nbNoeuds;
            for (int i = 0; i < nbNoeuds; i++) {
                System.out.println("coordonnées : " + i * pas + " " + y0 + " " + (x0 + (i + 1) * pas) + " " + h);
                Client c =  new Client(scene, disp, sd, i*pas , y0 ,x0 + (i+1)*pas, h );
                c.start();
            }

        // On gère les exceptions
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Une IP ou un hôte doit être spécifié en argument");
        } catch (NotBoundException e) {
            System.out.println("Le service distant appelé est introuvable");
        } catch (UnknownHostException e) {
            System.out.println("Serveur inexistant ou introuvable");
        } catch (ConnectException e) {
            System.out.println("Impossible de se connecter à l’annuaire rmiregistry distant");
        } catch (RemoteException e) {
            System.out.println("Impossible de se connecter au serveur distant");
        }

        

        Instant fin = Instant.now();
        long duree = Duration.between(debut, fin).toMillis();

        System.out.println("Image calculée en :"+duree+" ms");
    }	
}
