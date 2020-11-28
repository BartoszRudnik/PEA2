import java.util.*;

public class SymulowaneWyzarzanie {

    private int numberOfVertex;

    public int getNumberOfVertex() {
        return numberOfVertex;
    }

    public void setNumberOfVertex(int numberOfVertex) {
        this.numberOfVertex = numberOfVertex;
    }

    private int [] shuffleArray(int[] array)
    {
        int index, temp;
        Random random = new Random();

        int [] tmpArray = new int[array.length - 2];

        for(int i = 0; i < tmpArray.length; i++){
            tmpArray[i] = array[i + 1];
        }

        for (int i = tmpArray.length - 1; i > 0; i--)
        {
            index = random.nextInt(i + 1);
            temp = tmpArray[index];
            tmpArray[index] = tmpArray[i];
            tmpArray[i] = temp;
        }

        for(int i = 0; i < tmpArray.length; i++){

            array[i + 1] = tmpArray[i];

        }

        return  array;

    }

    private int getRouteCost(int [][] graph, int [] route){

        int cost = 0;

        for(int i = 1; i <= numberOfVertex; i++){

            cost += graph[route[i - 1]][route[i]];

        }

        return cost;

    }

    private int dataInitialization(int [][] graph, int [] route, int minCost){

        int [] minRoute = new int[numberOfVertex + 1];

        for(int i = 0; i <= numberOfVertex; i++){

            if(i == numberOfVertex)
                route[i] = 0;
            else {
                route[i] = i;
            }

        }

        for(int i = 0; i < 5; i++){

            int actualCost = getRouteCost(graph, route);

            if(actualCost < minCost){

                minCost = actualCost;
                minRoute = route;

            }

            route = shuffleArray(route);

        }

        route = minRoute;
        return minCost;

    }

    private int [] reverseRoute(int [] route, int i, int j){

        int iIndex = 0;
        int jIndex = 0;

        for(int k = 0 ; k < route.length; k++){

            if(route[k] == i)
                iIndex = k;

            if(route[k] == j)
                jIndex = k;

        }

        if(iIndex < jIndex){
            while(iIndex < jIndex){

                int tmp = route[iIndex];
                route[iIndex] = route[jIndex];
                route[jIndex] = tmp;

                iIndex++;
                jIndex--;

            }
        }
        else
        {
            while(jIndex < iIndex){

                int tmp = route[iIndex];
                route[iIndex] = route[jIndex];
                route[jIndex] = tmp;

                iIndex--;
                jIndex++;

            }
        }

        return route;

    }

    private int [] swapRoute(int [] route, int i, int j){

        int iIndex = 0;
        int jIndex = 0;

        for(int k = 0 ; k < route.length; k++){

            if(route[k] == i)
                iIndex = k;

            if(route[k] == j)
                jIndex = k;

        }

        int tmp = route[iIndex];

        route[iIndex] = route[jIndex];
        route[jIndex] = tmp;

        return  route;

    }

    private int [] insertRoute(int [] route, int i, int j){

        return  route;

    }

    public void algorithm(int [][] graph, int routeOption){

        Random random = new Random();

        int minCost = Integer.MAX_VALUE;
        int [] resultRoute = new int[numberOfVertex + 1];
        int [] route = new int[numberOfVertex + 1];
        double startTemp = 1.0;
        double finishTemp = 0.0001;
        double scale = 0.95;

        minCost = dataInitialization(graph, route, minCost);
        int actualCost;

        long finishTime = System.currentTimeMillis() + 7 * 1000; //10 sekund
        boolean test = true;

        while(test){

            int i = 1;
            int j = 1;

            while(i == j){

                i = random.nextInt((numberOfVertex));
                j = random.nextInt((numberOfVertex));

                if(i == 0)
                    i++;
                if(j == 0)
                    j++;

            }

            if(routeOption == 1)
                route = swapRoute(route, i, j);
            else if(routeOption == 2)
                route = reverseRoute(route, i, j);
            else
                route = insertRoute(route, i, j);

            actualCost = getRouteCost(graph, route);

            if(actualCost - minCost <= 0){

                minCost = actualCost;
                resultRoute = route.clone();

            }

            if (System.currentTimeMillis() > finishTime)
                test = false;

        }

        for(int i = 0; i <= numberOfVertex; i++){

            System.out.print(resultRoute[i]);

            if(i != numberOfVertex)
                System.out.print("-");
            else
                System.out.println();

        }

        System.out.println(minCost);

    }

}
