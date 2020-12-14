import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class TabuSearch {

    private int numberOfVertex;
    private int kadencja;
    private List<TabuElement> tabuList = new ArrayList<>();
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

    public List<TabuElement> getTabuList() {
        return tabuList;
    }

    public void setTabuList(List<TabuElement> tabuList) {
        this.tabuList = tabuList;
    }

    public TabuSearch(int numberOfVertex){

        this.numberOfVertex = numberOfVertex;
        this.kadencja = (int) Math.sqrt(numberOfVertex) / 2 + 1;
        pomoc.setNumberOfVertex(numberOfVertex);

    }

    //sprawdzenie czy zadana sciezka znajduje sie na liscie Tabu
    private boolean sprawdzListeTabu(int [] route, List<TabuElement> tabuList){

        for(TabuElement tabu : tabuList){

            if(Arrays.equals(route, tabu.getRoute()))
                return true;

        }

        return false;

    }

    //sprawdzenie czy zadany koszt jest mniejszy od dotychczas najmniejszego
    private boolean aspiracjaMin(int minCost, int actualCost){

        return actualCost < minCost;

    }

    //sprawdzenie czy zadany koszt jest mniejszy niz poprzednio uzyskany koszt
    private boolean aspiracjaOstatni(int lastCost, int actualCost){

        return actualCost < lastCost;

    }

    //sprawdzenie czy zadany koszt przemnozony przez parametr jest mniejszy od dotychczasowego najmniejszego kosztu
    private boolean aspiracjaParametr(int actualCost, double parametr, int minCost){

        return actualCost * parametr < minCost;

    }

    //obnizenie kadencji dla elementow znajdujacych sie na liscie tabu
    private List<TabuElement> odejmijKadencje(List<TabuElement> listaTabu){

        for(TabuElement tmp : listaTabu){

            int nowaKadencja = tmp.getKadencja() - 1;
            tmp.setKadencja(nowaKadencja);

        }

        return  listaTabu;

    }

    //usuniecie z listy tabu elementow, ktorych kadencja wynosi 0
    private List<TabuElement> czyscTabu(List<TabuElement> listaTabu){

        listaTabu.removeIf(tmp -> tmp.getKadencja() == 0);

        return  listaTabu;

    }

    public void algorithm(int [][] graph, int liczbaIteracji, int routeOption, int kryteriumDywersyfikacji, int kryteriumAspiracji, int liczbaSekund){

        List<TabuElement> listaTabu = new ArrayList<>();

        Random random = new Random();

        int minCost = Integer.MAX_VALUE;
        int actualCost;
        int [] minRoute = new int[numberOfVertex + 1];
        int [] route;

        //wyznaczenie poczatkowej sciezki i kosztu jej przejscia
        minRoute = pomoc.algorytmZachlanny(graph);
        minCost = pomoc.getRouteCost(graph, minRoute);

        route = minRoute.clone();

        //wyznaczenie czasu trwania algorytmu
        long finishTime = System.currentTimeMillis() + liczbaSekund * 1000;

        //dopoki czas nie zostanie przekroczony
        while(finishTime >= System.currentTimeMillis()) {

            route = minRoute.clone();
            int forCost = Integer.MAX_VALUE;
            int lastCost = Integer.MAX_VALUE;
            int dywersyfikacja = 0;
            int [] forRoute = new int[numberOfVertex + 1];
            boolean aspiracja = false;

            for (int i = 0; i < liczbaIteracji; i++) {

                int a = 1;
                int b = 1;

                //wyznaczenie wierzcholkow na podstawie, ktorych zostanie przeksztalcona sciezka
                while (a == b) {

                    a = random.nextInt((numberOfVertex));
                    b = random.nextInt((numberOfVertex));

                    if (a == 0)
                        a++;
                    if (b == 0)
                        b++;

                }

                //przeksztalcenie sciezki wedlug wybranego schematu
                if(routeOption == 0)
                    route = pomoc.swapRoute(route, a, b);
                if(routeOption == 1)
                    route = pomoc.reverseRoute(route, a, b);
                if(routeOption == 2)
                    route = pomoc.insertRoute(route, a, b);

                //wyznaczenie kosztu przeksztalconej sciezki
                actualCost = pomoc.getRouteCost(graph, route);

                //sprawdzenie wybranego kryterium aspiracji
                if(kryteriumAspiracji == 0)
                    aspiracja = aspiracjaMin(forCost, actualCost);
                if(kryteriumAspiracji == 1)
                    aspiracja = aspiracjaParametr(actualCost, 0.9, forCost);
                if(kryteriumAspiracji == 2)
                    aspiracja = aspiracjaOstatni(lastCost, actualCost);

                //jesli ruch nie jest na liscie tabu
                if(!sprawdzListeTabu(route, listaTabu)){

                    //sprawdzenie czy koszt aktualnej sciezki jest najmniejszy
                    if(forCost > actualCost){

                        forCost = actualCost;
                        forRoute = route.clone();

                        dywersyfikacja = 0;

                    }
                    //jesli nie jest zwiekszamy wartosc pola sprawdzajacego liczbe ruchow bez poprawy wyniku
                    else
                        dywersyfikacja++;

                }
                //jesli ruch jest na liscie tabu ale spelnia kryterium aspiracji
                else if(sprawdzListeTabu(route, listaTabu) && aspiracja){

                    if(forCost > actualCost){

                        forCost = actualCost;
                        forRoute = route.clone();

                        dywersyfikacja = 0;

                    }
                    //jesli nie jest zwiekszamy wartosc pola sprawdzajacego liczbe ruchow bez poprawy wyniku
                    else
                        dywersyfikacja++;

                }// jesli ruch jest na liscie tabu i nie spelnia kryterium aspiracji
                else{

                    dywersyfikacja++;

                }

                //zaktualizowanie kadencji elementow znajdujacych sie na liscie tabu i usniecie z listy elementow o kadencji rownej 0
                listaTabu = odejmijKadencje(listaTabu);
                listaTabu = czyscTabu(listaTabu);

                //dodanie ruchu do listy tabu
                TabuElement noweTabu = new TabuElement(route, getKadencja());
                listaTabu.add(noweTabu);

                lastCost = actualCost;

                //sprawdzenie czy nie zostalo przekroczone kryterium dywersyfikacji
                if(dywersyfikacja >= kryteriumDywersyfikacji) {
                    break;
                }

            }

            //zaktualizowanie najmniejszego kosztu przejscia i jego sciezki
            if(forCost < minCost){

                minCost = forCost;
                minRoute = forRoute.clone();

            }

        }

        //wypisanie wynikowej sciezki i jej kosztu przejscia
        pomoc.getResultRoute(minRoute);
        System.out.println(minCost);

    }

    public long [] measureAlgorithm(int [][] graph, int liczbaIteracji, int routeOption, int kryteriumDywersyfikacji, int kryteriumAspiracji, int liczbaSekund){

        List<TabuElement> listaTabu = new ArrayList<>();

        Random random = new Random();

        int minCost = Integer.MAX_VALUE;
        int actualCost;
        int [] minRoute = new int[numberOfVertex + 1];
        int [] route;

        minRoute = pomoc.algorytmZachlanny(graph);
        minCost = pomoc.getRouteCost(graph, minRoute);
        route = minRoute.clone();

        long finishTime = System.currentTimeMillis() + liczbaSekund * 1000;
        long startTime = System.currentTimeMillis();
        long actualTime = 0;
        long solutionTime = 0;

        while(finishTime >= System.currentTimeMillis()) {

            route = minRoute.clone();
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
                        actualTime = System.currentTimeMillis();

                        dywersyfikacja = 0;

                    }
                    else
                        dywersyfikacja++;

                    TabuElement noweTabu = new TabuElement(route, getKadencja());
                    listaTabu.add(noweTabu);

                }
                else if(sprawdzListeTabu(route, listaTabu) && aspiracja){

                    forCost = actualCost;
                    forRoute = route.clone();
                    actualTime = System.currentTimeMillis();

                    dywersyfikacja = 0;

                    TabuElement noweTabu = new TabuElement(route, getKadencja());
                    listaTabu.add(noweTabu);

                }else{

                    dywersyfikacja++;

                    TabuElement noweTabu = new TabuElement(route, getKadencja());
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
                solutionTime = actualTime;

            }

        }

        pomoc.getResultRoute(minRoute);

        long [] array = new long[2];

        array[0] = minCost;
        array[1] = (solutionTime - startTime) / 1000;

        return array;

    }

}
