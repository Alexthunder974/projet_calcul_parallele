import java.rmi.RemoteException;
import java.rmi.server.RemoteServer;
import java.rmi.server.ServerNotActiveException;
import java.util.ArrayList;
import java.util.List;

public class Distributeur implements ServiceDistributeur {

    private static final boolean DEBUG = false;
    private List<ServiceNoeud> noeuds = new ArrayList<>();
    private int noeudCourant = 0;

    @Override
    public void enregistrerNoeud(ServiceNoeud noeud) {
        String host = "";
        try{
            host = RemoteServer.getClientHost();
        }catch(ServerNotActiveException e) {
            System.out.println("erreur : machine inconnue");
            System.exit(1);
        }

        if (DEBUG) {
            System.out.println("Connexion de " + host);
        }

        this.noeuds.add(noeud);
    }

    @Override
    public void retirerNoeud(ServiceNoeud noeud) {
        this.noeuds.remove(noeud);
    }

    @Override
    public ServiceNoeud getNoeud() throws RemoteException {
        ServiceNoeud n = this.noeuds.get(noeudCourant);
        noeudCourant = (noeudCourant == (noeuds.size() - 1)) ? 0 : noeudCourant+1;
        return n;
    }


}