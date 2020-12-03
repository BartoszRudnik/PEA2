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

    public void algorithm(int [][] graph, int routeOption, int coolingOption, int iterationsLimit){

        Random random = new Random();

        int [] minRoute = new int[numberOfVertex + 1];

        int minCost = Integer.MAX_VALUE;
        int actualCost;
        double startTemp = 1;
        double finishTemp = 0.001;
        double scale = 0.96;

        minRoute = pomoc.dataInitialization(graph, minRoute, minCost);
        minCost = pomoc.getRouteCost(graph, minRoute);

        int resultCost = minCost;
        int [] resultRoute = minRoute.clone();
        int [] route = minRoute.clone();

        long finishTime = System.currentTimeMillis() + 10 * 1000;
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

                    if(minCost < resultCost){

                        resultCost = minCost;
                        resultRoute = minRoute.clone();

                    }

                } else if (moveTest < Math.exp((actualCost - minCost) / startTemp * (-1))) {

                    minCost = actualCost;
                    minRoute = route.clone();

                    if(minCost < resultCost){

                        resultCost = minCost;
                        resultRoute = minRoute.clone();

                    }

                }

            }

            if(coolingOption == 0){

                startTemp = pomoc.geometricCooling(startTemp, scale);

            }
            else if(coolingOption == 1){

                startTemp = pomoc.linearCooling(startTemp, scale);

            }
            else if(coolingOption == 2){

                startTemp = pomoc.logarithmicCooling(startTemp, scale);

            }

            if (System.currentTimeMillis() > finishTime)
                test = false;

        }

        pomoc.getResultRoute(resultRoute);

        System.out.println(pomoc.getRouteCost(graph, resultRoute));

    }

}
