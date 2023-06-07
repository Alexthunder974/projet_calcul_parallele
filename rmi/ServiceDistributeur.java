package rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServiceDistributeur extends Remote {

    void enregistrerNoeud(ServiceNoeud noeud) throws RemoteException;
}
