import java.util.Random;

public class FunkcjePomocnicze {

    private int numberOfVertex;

    public int getNumberOfVertex() {
        return numberOfVertex;
    }

    public void setNumberOfVertex(int numberOfVertex) {
        this.numberOfVertex = numberOfVertex;
    }

    public int [] algorytmZachlanny(int [][] graph){

        int [] route = new int[numberOfVertex + 1];
        int [] resultRoute = new int[numberOfVertex + 1];
        boolean check;
        int oldBestIndex = 0;
        int actualBestIndex = 0;

        for(int i = 0; i < numberOfVertex; i++){

            int bestCost = Integer.MAX_VALUE;
            oldBestIndex = actualBestIndex;

            for(int j = 0; j < numberOfVertex; j++){

                check = true;

                if(j != oldBestIndex){

                    for(int g = 0; g <= i; g++){

                        if(j == route[g]){
                            check = false;
                        }

                    }
                    if(graph[oldBestIndex][j] < bestCost && check == true){

                        bestCost = graph[oldBestIndex][j];
                        actualBestIndex = j;

                    }

                }

            }

            route[i] = actualBestIndex;
            resultRoute[i] = oldBestIndex;

        }

        resultRoute[numberOfVertex] = 0;

        return resultRoute;

    }

    //funkcja zamieniajaca pozycje elementow znajdujacych sie w 'array'
    public int [] shuffleArray(int[] array)
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

    // funkcja obliczajaca koszt przejscia przez zadana sciezke
    public int getRouteCost(int [][] graph, int [] route){

        int cost = 0;

        for(int i = 1; i <= numberOfVertex; i++){

            cost += graph[route[i - 1]][route[i]];

        }

        return cost;

    }

    // funkcja inicjalizujaca wstepna sciezke do dalszych rozwiazan dla algorytmu TS i SA
    public int [] dataInitialization(int [][] graph, int [] route, int minCost){

        int [] minRoute = new int[numberOfVertex + 1];

        for(int i = 0; i <= numberOfVertex; i++){

            if(i == numberOfVertex)
                route[i] = 0;
            else {
                route[i] = i;
            }

        }

        //losowanych jest 10 przykladowych sciezek i wybierana jest z nich sciezke o najmniejszym koszcie przejscia
        for(int i = 0; i < 10; i++){

            route = shuffleArray(route);
            int actualCost = getRouteCost(graph, route);

            if(actualCost < minCost){

                minCost = actualCost;
                minRoute = route.clone();

            }

        }

        // zwracana sciezka bedzie sciezka poczatkowa dla SA i TS
        return minRoute;

    }

    //geometryczny ochladzania temperatury dla SA
    public double geometricCooling(double startTemp, double scale){

        startTemp *= scale;

        return startTemp;

    }

    //liniowe ochladzanie temperatury dla SA
    public double linearCooling(double startTemp, double scale){

        startTemp -= scale;

        return  startTemp;

    }

    //logarytmiczne ochladzanie temperatury dla SA
    public double logarithmicCooling(double startTemp, double scale){

        startTemp /= (1 + scale / startTemp);

        return  startTemp;

    }

    // odwrocenie kolejnosci wystepowania elementow w tablicy pomiedzy elementami o wartosci 'i' oraz 'j'
    public int [] reverseRoute(int [] route, int i, int j){

        //wyznaczenie indeksow elementow o wartosci 'i' oraz 'j'
        int [] index = getIndex(route, i, j);
        int iIndex = index[0];
        int jIndex = index[1];

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

    // zamiana miejscami w tablicy elementu o wartosci 'i' z elementem o wartosci 'j'
    public int [] swapRoute(int [] route, int i, int j){

        // wyznaczenie indeksow elementow o wartosci 'i' oraz 'j'
        int [] index = getIndex(route, i, j);
        int iIndex = index[0];
        int jIndex = index[1];

        int tmp = route[iIndex];

        route[iIndex] = route[jIndex];
        route[jIndex] = tmp;

        return  route;

    }

    // wstawienie wierzcholka 'i' na pozycje o indeksie 'j'
    public int [] insertRoute(int [] route, int i, int j){

        int indexI = 0;
        int tmp = 0;
        int [] tmpArray = new int[route.length];

        // wyznaczenie indeksu elementu o wartosci 'i'
        for(int k = 1; k < route.length - 1; k++){

            if(route[k] == i){

                indexI = k;
                break;

            }

        }

        // przesuniecie pozostalych elementow tablicy i wstawienie elementu o wartosci 'i' na pozycje 'j'
        if(indexI > j) {

            for (int k = j; k < route.length; k++) {

                if (route[k] != i)
                    tmpArray[k - tmp] = route[k];
                else
                    tmp = 1;

            }

            route[j] = i;

            for (int k = j + 1; k < route.length; k++) {

                route[k] = tmpArray[k - 1];

            }

        }
        else{

            for(int k = indexI; k < j; k++){

                route[k] = route[k + 1];

            }

            route[j] = i;

        }

        return  route;

    }

    //funkcja zwracajaca indeksy wartosci 'i' oraz 'j' w tabeli route
    private int [] getIndex(int [] route, int i, int j){

        int [] index = new int[2];

        for(int k = 1 ; k < route.length - 1; k++){

            if(route[k] == i)
                index[0] = k;

            if(route[k] == j)
                index[1] = k;

        }

        return index;

    }

    // funkcja wypisujaca kolejne wierzcholki znajdujace sie w sciezce
    public void getResultRoute(int [] route){

        for(int i = 0; i <= numberOfVertex; i++){

            System.out.print(route[i]);

            if(i != numberOfVertex)
                System.out.print("-");
            else
                System.out.println();

        }

    }

}
