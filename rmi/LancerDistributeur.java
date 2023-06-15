package rmi;

import java.rmi.AccessException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class LancerDistributeur {

    public static void main(String[] args) {
        int port = 1099;
        String adresse = "127.0.0.1";

        try {
            if (args.length != 2) {
                System.out.println("nombre d'arguments incorrect, utilisation : java LancerDistributeur <adresse> <port>");
                System.out.println("utilisation des valeurs par défaut : ");
                System.out.println("\t- adresse annuaire : 127.0.0.1");
                System.out.println("\t- port annuaire : 1099");
            } else {
                adresse = args[0];
                port = Integer.parseInt(args[1]);
            }
        } catch (NumberFormatException e) {
            System.out.println("erreur : le port doit être un entier");
            System.exit(1);
        }

        Distributeur distributeur = null;
        try {
            distributeur = new Distributeur();
        } catch (java.lang.NoClassDefFoundError e) {
            System.out.println("erreur : service inexistant");
            System.exit(1);
        }

        ServiceDistributeur rd;
        Registry reg;
        try {
            rd = (ServiceDistributeur) UnicastRemoteObject.exportObject(distributeur, 0);
            reg = LocateRegistry.getRegistry(adresse, port);
            reg.rebind("distributeur", rd);

        } catch (java.rmi.ConnectException e) {
            System.out.println("erreur : lancez le service rmiregistry");
            System.exit(1);
        } catch (AccessException e) {
            System.out.println("erreur : accès interdit");
            System.exit(1);
        } catch (RemoteException e) {
            System.out.println("erreur : le registre distant n'est pas accessible (vérifier l'adresse et le port de l'annuaire)");
            System.exit(1);
        }
    }
}
