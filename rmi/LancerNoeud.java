package rmi;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class LancerNoeud {

    public static void main(String[] args) {
        Registry reg = null;
        ServiceDistributeur distributeur = null;

        try {
            if (args.length != 2) {
                System.out.println("nombre d'arguments incorrect");
                System.out.println("utilisation des valeurs par défaut : ");
                System.out.println("\t- adresse : 127.0.0.1");
                System.out.println("\t- port : 1099");
                System.out.println();
                reg = LocateRegistry.getRegistry("127.0.0.1", 1099);
            } else {
                reg = LocateRegistry.getRegistry(args[0], Integer.parseInt(args[1]));
            }
        } catch (RemoteException e) {
            System.out.println("connexion au serveur impossible");
            System.exit(1);
        }

        try {
            distributeur = (ServiceDistributeur) reg.lookup("distributeur");
        } catch (java.rmi.NotBoundException e) {
            System.out.println("le service demandé n'existe pas");
            System.exit(1);
        } catch (java.rmi.ConnectException e) {
            System.out.println("communication impossible avec rmi registry présent sur le serveur distant");
            System.exit(1);
        } catch (RemoteException e) {
            System.out.println("aucunes routes n'existe entre le client et le serveur");
            System.exit(1);
        } catch (java.lang.IllegalArgumentException e) {
            System.out.println("numero de port invalide");
            System.exit(1);
        }


        Noeud noeud = new Noeud();
        try {
            distributeur.enregistrerNoeud((ServiceNoeud) UnicastRemoteObject.exportObject(noeud, 0));
        } catch (RemoteException e) {
            System.out.println("erreur : le registre distant n'est pas accessible (vérifier l'adresse et le port de l'annuaire)");
            System.exit(1);
        }


    }
}
