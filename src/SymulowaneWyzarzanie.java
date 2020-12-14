import java.util.*;

public class SymulowaneWyzarzanie {

    private int numberOfVertex;
    private final FunkcjePomocnicze pomoc = new FunkcjePomocnicze();

    public int getNumberOfVertex() {
        return numberOfVertex;
    }

    public void setNumberOfVertex(int numberOfVertex) {
        this.numberOfVertex = numberOfVertex;
    }

    public SymulowaneWyzarzanie(int numberOfVertex){

        this.numberOfVertex = numberOfVertex;
        pomoc.setNumberOfVertex(numberOfVertex);

    }

    public void algorithm(int [][] graph, int routeOption, int coolingOption, int iterationsLimit, double scale, int seconds){

        Random random = new Random();

        int [] minRoute = new int[numberOfVertex + 1];

        int minCost = Integer.MAX_VALUE;
        int actualCost;
        double startTemp = 1.0;
        double finishTemp = 0.001;

        // wyznaczenie sciezki poczatkowej i kosztu przejscia tej sciezki
        minRoute = pomoc.algorytmZachlanny(graph);
        minCost = pomoc.getRouteCost(graph, minRoute);

        int resultCost = minCost;
        int [] resultRoute = minRoute.clone();
        int [] route = minRoute.clone();

        //wyznaczenie czasu trwania algorytmu
        long finishTime = System.currentTimeMillis() + seconds * 1000;
        boolean test = true;

        //dopóki nie został przekroczony czas trwania algorytmu
        while(test){

            //sciezka, ktorej sasiedztwo bedzie badane
            route = resultRoute.clone();

            for(int k = 0; k < iterationsLimit; k++) {

                int i = 1;
                int j = 1;
                double moveTest = random.nextDouble();

                //wyznaczenie wierzcholkow na podstawie, ktorych zostanie przeksztalcona sciezka
                while (i == j) {

                    i = random.nextInt((numberOfVertex));
                    j = random.nextInt((numberOfVertex));

                    if (i == 0)
                        i++;
                    if (j == 0)
                        j++;

                }

                //przeksztalcenie sciezki w zaleznosci
                if (routeOption == 0)
                    route = pomoc.swapRoute(route, i, j);
                else if (routeOption == 1)
                    route = pomoc.reverseRoute(route, i, j);
                else if (routeOption == 2)
                    route = pomoc.insertRoute(route, i, j);

                //wyznaczenie kosztu przejscia aktualnie badanej sciezki
                actualCost = pomoc.getRouteCost(graph, route);

                //jesli koszt aktualnie badanej sciezki jest najmniejszy to jest przyjmowany jako aktualny wynik
                if (actualCost - minCost <= 0) {

                    minCost = actualCost;
                    minRoute = route.clone();

                    if(minCost < resultCost){

                        resultCost = minCost;
                        resultRoute = minRoute.clone();

                    }

                }// uwzglednienie funkcji prawdopodobienstwa
                else if (moveTest < Math.exp((actualCost - minCost) / startTemp * (-1))) {

                    minCost = actualCost;
                    minRoute = route.clone();

                    if(minCost < resultCost){

                        resultCost = minCost;
                        resultRoute = minRoute.clone();

                    }

                }

            }

            //ochladzania temperatury wedlug wybranego schematu
            if(coolingOption == 0){

                startTemp = pomoc.geometricCooling(startTemp, scale);

            }
            else if(coolingOption == 1){

                startTemp = pomoc.linearCooling(startTemp, scale);

            }
            else if(coolingOption == 2){

                startTemp = pomoc.logarithmicCooling(startTemp, scale);

            }

            //sprawdzenie czy nie zostal przekroczony czas dzialania algorytmu
            if (System.currentTimeMillis() > finishTime)
                test = false;

        }

        //wypisanie najmniejszej wykrytej sciezki i kosztu jej przejscia
        pomoc.getResultRoute(resultRoute);

        System.out.println(resultCost);
        //System.out.println(pomoc.getRouteCost(graph, resultRoute));

    }

    public long [] measureAlgorithm(int [][] graph, int routeOption, int coolingOption, int iterationsLimit, double scale, int seconds){

        Random random = new Random();

        int [] minRoute = new int[numberOfVertex + 1];

        int minCost = Integer.MAX_VALUE;
        int actualCost;
        double startTemp = 1;
        double finishTemp = 0.001;

        minRoute = pomoc.algorytmZachlanny(graph);
        minCost = pomoc.getRouteCost(graph, minRoute);

        int resultCost = minCost;
        int [] resultRoute = minRoute.clone();
        int [] route = minRoute.clone();

        long finishTime = System.currentTimeMillis() + seconds * 1000;
        long startTime = System.currentTimeMillis();
        long actualTime = 0;
        long solutionTime = 0;

        boolean test = true;

        while(test){

            route = resultRoute.clone();

            for(int k = 0; k < iterationsLimit; k++) {

                int i = 1;
                int j = 1;
                double moveTest = random.nextDouble();

                while (i == j) {

                    i = random.nextInt((numberOfVertex));
                    j = random.nextInt((numberOfVertex));

                    if (i == 0)
                        i++;
                    if (j == 0)
                        j++;

                }

                if (routeOption == 0)
                    route = pomoc.swapRoute(route, i, j);
                else if (routeOption == 1)
                    route = pomoc.reverseRoute(route, i, j);
                else if (routeOption == 2)
                    route = pomoc.insertRoute(route, i, j);

                actualCost = pomoc.getRouteCost(graph, route);

                if (actualCost - minCost <= 0) {

                    minCost = actualCost;
                    minRoute = route.clone();
                    actualTime = System.currentTimeMillis();

                    if(minCost < resultCost){

                        resultCost = minCost;
                        resultRoute = minRoute.clone();
                        solutionTime = actualTime;

                    }

                } else if (moveTest < Math.exp((actualCost - minCost) / startTemp * (-1))) {

                    minCost = actualCost;
                    minRoute = route.clone();
                    actualTime = System.currentTimeMillis();

                    if(minCost < resultCost){

                        resultCost = minCost;
                        resultRoute = minRoute.clone();
                        solutionTime = actualTime;

                    }

                }

            }

            if(coolingOption == 0){

                startTemp = pomoc.geometricCooling(startTemp, scale);

            }
            else if(coolingOption == 1){

                startTemp = pomoc.linearCooling(startTemp, 0.001);

            }
            else if(coolingOption == 2){

                startTemp = pomoc.logarithmicCooling(startTemp, scale);

            }

            if (System.currentTimeMillis() > finishTime)
                test = false;

        }

        pomoc.getResultRoute(resultRoute);
        System.out.println(pomoc.getRouteCost(graph,resultRoute));

        long [] array = new long[2];

        array[0] = pomoc.getRouteCost(graph, resultRoute);
        array[1] = (solutionTime - startTime) / 1000;

        return  array;

    }

}
