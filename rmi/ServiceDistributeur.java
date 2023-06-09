package rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServiceDistributeur extends Remote {

    void enregistrerNoeud(ServiceNoeud noeud) throws RemoteException;

    void retirerNoeud(ServiceNoeud noeud) throws RemoteException;
    ServiceNoeud getNoeud() throws RemoteException;
}
