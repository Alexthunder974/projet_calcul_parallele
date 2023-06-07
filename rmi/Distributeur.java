package rmi;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class Distributeur implements ServiceDistributeur {

    private List<ServiceNoeud> noeuds = new ArrayList<>();
    private int noeudCourant = 0;

    @Override
    public void enregistrerNoeud(ServiceNoeud noeud) throws RemoteException {
        noeuds.add(noeud);
    }

    @Override
    public void retirerNoeud(ServiceNoeud noeud) throws RemoteException {
        noeuds.remove(noeud);
    }

    @Override
    public ServiceNoeud getNoeud() throws RemoteException {
        ServiceNoeud n = noeuds.get(noeudCourant);
        noeudCourant++;
        return n;
    }


}
