import java.util.Scanner;

public class MeasureUI {

    private final Data data = new Data();

    private int [][] graph;
    private int numberOfVertex;
    private String fileName;

    private boolean spr = true;

    public void show(){

        Scanner scanner = new Scanner(System.in);

        while(spr){

            System.out.println("Wybierz operacje:");
            System.out.println("1. Wczytaj plik");
            System.out.println("2. Przeprowadz pomiary -> TabuSearch");
            System.out.println("3. Przeprowadz pomiary -> SymulowaneWyzarzanie");
            System.out.println("0. Wyjscie");

            int nr = scanner.nextInt();

            switch (nr){

                case  0:
                    spr = false;
                    break;

                case 1:
                    System.out.println("Podaj nazwe pliku do wczytania: ");

                    scanner.nextLine();
                    fileName = scanner.nextLine();

                    data.readData2(fileName);

                    graph = data.getGraph();
                    numberOfVertex = data.getV();

                    break;

                case 2:
                    TabuSearch ts = new TabuSearch(numberOfVertex);
                    long [] resultArray = new long[20];
                    int count = 0;

                    for(int i = 0; i < 10; i++){

                        long [] tmpArray = ts.measureAlgorithm(graph, numberOfVertex, 0, 5, 0, 5);

                        resultArray[count] = tmpArray[0];
                        resultArray[count + 1] = tmpArray[1];

                        count += 2;

                    }

                    data.saveResultEtap2(fileName + "TABU.txt", resultArray);

                    break;

                case 3:
                    SymulowaneWyzarzanie sw = new SymulowaneWyzarzanie(numberOfVertex);
                    long [] resultArraySW = new long[20];
                    int c = 0;

                    for(int i = 0; i < 10; i++){

                        long [] tmpArray = sw.measureAlgorithm(graph, 0, 0, numberOfVertex, 0.96, 5);

                        resultArraySW[c] = tmpArray[0];
                        resultArraySW[c + 1] = tmpArray[1];

                        c += 2;

                    }

                    data.saveResultEtap2(fileName + "Wyzarzanie.txt", resultArraySW);

                    break;


            }

        }

    }


}
