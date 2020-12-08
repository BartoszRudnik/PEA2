import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class TabuSearch {

    private int numberOfVertex;
    private int kadencja;
    private List<TabuList> tabuList = new ArrayList<>();
    private final FunkcjePomocnicze pomoc = new FunkcjePomocnicze();

    public int getNumberOfVertex() {
        return numberOfVertex;
    }

    public void setNumberOfVertex(int numberOfVertex) {
        this.numberOfVertex = numberOfVertex;
    }

    public int getKadencja() {
        return kadencja;
    }

    public void setKadencja(int kadencja) {
        this.kadencja = kadencja;
    }

    public List<TabuList> getTabuList() {
        return tabuList;
    }

    public void setTabuList(List<TabuList> tabuList) {
        this.tabuList = tabuList;
    }

    public TabuSearch(int numberOfVertex){

        this.numberOfVertex = numberOfVertex;
        this.kadencja = (int) Math.sqrt(numberOfVertex) / 2 + 1;
        pomoc.setNumberOfVertex(numberOfVertex);

    }

    private boolean sprawdzListeTabu(int [] route, List<TabuList> tabuList){

        for(TabuList tabu : tabuList){

            if(Arrays.equals(route, tabu.getRoute()))
                return true;

        }

        return false;

    }

    private boolean aspiracjaMin(int minCost, int actualCost){

        return actualCost < minCost;

    }

    private boolean aspiracjaOstatni(int lastCost, int actualCost){

        return actualCost < lastCost;

    }

    private boolean aspiracjaParametr(int actualCost, double parametr, int minCost){

        return actualCost * parametr < minCost;

    }

    private List<TabuList> odejmijKadencje(List<TabuList> listaTabu){

        for(TabuList tmp : listaTabu){

            int nowaKadencja = tmp.getKadencja() - 1;
            tmp.setKadencja(nowaKadencja);

        }

        return  listaTabu;

    }

    private List<TabuList> czyscTabu(List<TabuList> listaTabu){

        listaTabu.removeIf(tmp -> tmp.getKadencja() == 0);

        return  listaTabu;

    }

    public void algorithm(int [][] graph, int liczbaIteracji, List<TabuList> listaTabu, int routeOption, int kryteriumDywersyfikacji, int kryteriumAspiracji, int liczbaSekund){

        Random random = new Random();

        int minCost = Integer.MAX_VALUE;
        int actualCost;
        int [] minRoute = new int[numberOfVertex + 1];
        int [] route;

        minRoute = pomoc.dataInitialization(graph, minRoute, minCost);
        minCost = pomoc.getRouteCost(graph, minRoute);
        route = minRoute.clone();

        long finishTime = System.currentTimeMillis() + liczbaSekund * 1000;

        while(finishTime >= System.currentTimeMillis()) {

            route = pomoc.shuffleArray(route);
            int forCost = Integer.MAX_VALUE;
            int lastCost = Integer.MAX_VALUE;
            int dywersyfikacja = 0;
            int [] forRoute = new int[numberOfVertex + 1];
            boolean aspiracja = false;

            for (int i = 0; i < liczbaIteracji; i++) {

                int a = 1;
                int b = 1;

                while (a == b) {

                    a = random.nextInt((numberOfVertex));
                    b = random.nextInt((numberOfVertex));

                    if (a == 0)
                        a++;
                    if (b == 0)
                        b++;

                }

                if(routeOption == 0)
                    route = pomoc.swapRoute(route, a, b);
                if(routeOption == 1)
                    route = pomoc.reverseRoute(route, a, b);
                if(routeOption == 2)
                    route = pomoc.insertRoute(route, a, b);

                actualCost = pomoc.getRouteCost(graph, route);

                if(kryteriumAspiracji == 0)
                    aspiracja = aspiracjaMin(forCost, actualCost);
                if(kryteriumAspiracji == 1)
                    aspiracja = aspiracjaParametr(actualCost, 0.9, forCost);
                if(kryteriumAspiracji == 2)
                    aspiracja = aspiracjaOstatni(lastCost, actualCost);

                if(!sprawdzListeTabu(route, listaTabu)){

                    if(forCost > actualCost){

                        forCost = actualCost;
                        forRoute = route.clone();

                        dywersyfikacja = 0;

                    }
                    else
                        dywersyfikacja++;

                    TabuList noweTabu = new TabuList(route, getKadencja());
                    listaTabu.add(noweTabu);

                }
                else if(sprawdzListeTabu(route, listaTabu) && aspiracja){

                    forCost = actualCost;
                    forRoute = route.clone();

                    dywersyfikacja = 0;

                    TabuList noweTabu = new TabuList(route, getKadencja());
                    listaTabu.add(noweTabu);

                }else{

                    dywersyfikacja++;

                    TabuList noweTabu = new TabuList(route, getKadencja());
                    listaTabu.add(noweTabu);

                }

                listaTabu = odejmijKadencje(listaTabu);
                listaTabu = czyscTabu(listaTabu);

                lastCost = actualCost;

                if(dywersyfikacja >= kryteriumDywersyfikacji)
                    break;

            }

            if(forCost < minCost){

                minCost = forCost;
                minRoute = forRoute.clone();

            }

        }

        pomoc.getResultRoute(minRoute);
        System.out.println(minCost);

    }

}
