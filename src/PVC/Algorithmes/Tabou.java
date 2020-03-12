package PVC.Algorithmes;

import PVC.Definitions.City;
import PVC.Definitions.Route;
import PVC.Definitions.Tuple;
import PVC.Definitions.Voisin;

import java.util.ArrayList;

public class Tabou {
    private Route bestRoute;
    private Route actualRoute;
    private ArrayList<Tuple<Integer, Integer>> T = new ArrayList<>();
    private int iter = 10;

    public void run() {
        while (this.iter-- > 0) {
            Voisin actualVoisin = new Voisin(this.actualRoute);
            Route minRoute = actualVoisin.getMinRoute(this.T,this.actualRoute);
            this.actualRoute = minRoute;

            this.upgradeT(this.actualRoute.getTransfert());
            if (minRoute.getTotalDistance() < this.bestRoute.getTotalDistance()) {
                this.bestRoute = minRoute;
            }
        }
    }

    public Tabou(Route actualRoute) {
        this.actualRoute = actualRoute;
        this.bestRoute = new Route(actualRoute);
    }
    public Tabou(Route actualRoute, int it) {
        this(actualRoute);
        this.iter=it;
    }

    public void setIter(int iter) {
        this.iter = iter;
    }

    private boolean checkT(Tuple<Integer, Integer> trans) {
        return T.contains(trans);
    }

    private void upgradeT(Tuple<Integer, Integer> trans) {
        if (T.size() > 5) {
            T.remove(0);
        }
        T.add(new Tuple<>(trans));
    }

    public static void main(String[] args) {
        ArrayList<City> testCity = new ArrayList<>();
        double[] l1 = {-0.57, 4.85, -1.55, 2.34, 5.4, 5.02};
        double[] l2 = {44.83, 45.75, 47.22, 48.85, 43.3, 47.32};
        String[] name = {"B", "L", "N", "P", "M", "D"};
        for (int i = 0; i < l1.length; i++) {
            testCity.add(new City(l1[i], l2[i], name[i]));
        }
        Route initRoute = new Route(testCity);
        Tabou t = new Tabou(initRoute);
        t.run();
        System.out.println(t.bestRoute);
        System.out.println(t.bestRoute.getTotalDistance());
    }
}
