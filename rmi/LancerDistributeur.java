package rmi;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class LancerDistributeur {

    public static void main(String[] args) throws RemoteException {
        Distributeur distributeur = new Distributeur();
        int port = 1500;
        ServiceDistributeur serviceDistributeur = (ServiceDistributeur) UnicastRemoteObject.exportObject(distributeur, port);

        Registry rg = LocateRegistry.createRegistry(1500);
        rg.rebind("distributeur", serviceDistributeur);
    }
}
