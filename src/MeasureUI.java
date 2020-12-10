import java.util.Scanner;

public class MeasureUI {

    private final Data data = new Data();

    private int [][] graph;
    private int numberOfVertex;
    private String fileName;
    private int seconds = 10;

    private boolean spr = true;

    public void show(){

        Scanner scanner = new Scanner(System.in);

        while(spr){

            System.out.println("Wybierz operacje:");
            System.out.println("1. Wczytaj plik");
            System.out.println("2. Przeprowadz pomiary -> TabuSearch");
            System.out.println("3. Przeprowadz pomiary -> SymulowaneWyzarzanie");
            System.out.println("4. Zmien czas wykonywania sie algorytmow");
            System.out.println("5. Wsysztkie pomiary");
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

                        long [] tmpArray = ts.measureAlgorithm(graph, numberOfVertex, 0, 5, 0, seconds);

                        resultArray[count] = tmpArray[0];
                        resultArray[count + 1] = tmpArray[1];

                        count += 2;

                    }

                    data.saveResultEtap2(seconds + "sek_" + fileName + "TABU.txt", resultArray);

                    break;

                case 3:
                    SymulowaneWyzarzanie sw = new SymulowaneWyzarzanie(numberOfVertex);
                    long [] resultArraySW = new long[20];
                    int c = 0;

                    for(int i = 0; i < 10; i++){

                        long [] tmpArray = sw.measureAlgorithm(graph, 0, 0, numberOfVertex, 0.96, seconds);

                        resultArraySW[c] = tmpArray[0];
                        resultArraySW[c + 1] = tmpArray[1];

                        c += 2;

                    }

                    data.saveResultEtap2(seconds + "sek_" + fileName + "Wyzarzanie.txt", resultArraySW);

                    break;

                case 4:
                    try {

                        scanner.nextLine();
                        seconds = scanner.nextInt();

                        if(seconds <= 0) {
                            seconds = 10;
                            throw new Exception();
                        }

                    }catch (Exception ex){

                        System.out.println("Podano bledna wartosc");

                    }

                    break;

                case 5:
                    data.readData2("ftv47.atsp");
                    graph = data.getGraph();
                    numberOfVertex = data.getV();

                    /*tabu(1, 5);
                    tabu(1, 10);
                    tabu(1, 15);
                    tabu(1, 20);
                    tabu(1, 25);
                    tabu(1, 30);
                    tabu(2, 5);
                    tabu(2, 10);
                    tabu(2, 15);
                    tabu(2, 20);
                    tabu(2, 25);
                    tabu(2, 30);
                    symulowane(1, 5);
                    symulowane(1, 10);
                    symulowane(1, 15);
                    symulowane(1, 20);*/
                    symulowane(1, 25, "ftv47.atsp");
                    symulowane(1, 30, "ftv47.atsp");
                    symulowane(2, 5, "ftv47.atsp");
                    symulowane(2, 10, "ftv47.atsp");
                    symulowane(2, 15, "ftv47.atsp");
                    symulowane(2, 20, "ftv47.atsp");
                    symulowane(2, 25, "ftv47.atsp");
                    symulowane(2, 30, "ftv47.atsp");

                    data.readData2("ftv170.atsp");
                    graph = data.getGraph();
                    numberOfVertex = data.getV();

                    tabu(1, 5, "ftv170.atsp");
                    tabu(1, 10, "ftv170.atsp");
                    tabu(1, 15, "ftv170.atsp");
                    tabu(1, 20, "ftv170.atsp");
                    tabu(1, 25, "ftv170.atsp");
                    tabu(1, 30, "ftv170.atsp");
                    tabu(2, 5, "ftv170.atsp");
                    tabu(2, 10, "ftv170.atsp");
                    tabu(2, 15, "ftv170.atsp");
                    tabu(2, 20, "ftv170.atsp");
                    tabu(2, 25, "ftv170.atsp");
                    tabu(2, 30, "ftv170.atsp");
                    symulowane(1, 5, "ftv170.atsp");
                    symulowane(1, 10, "ftv170.atsp");
                    symulowane(1, 15, "ftv170.atsp");
                    symulowane(1, 20, "ftv170.atsp");
                    symulowane(1, 25, "ftv170.atsp");
                    symulowane(1, 30, "ftv170.atsp");
                    symulowane(2, 5, "ftv170.atsp");
                    symulowane(2, 10, "ftv170.atsp");
                    symulowane(2, 15, "ftv170.atsp");
                    symulowane(2, 20, "ftv170.atsp");
                    symulowane(2, 25, "ftv170.atsp");
                    symulowane(2, 30, "ftv170.atsp");

                    data.readData2("rbg403.atsp");
                    graph = data.getGraph();
                    numberOfVertex = data.getV();

                    tabu(1, 5, "rbg403.atsp");
                    tabu(1, 10, "rbg403.atsp");
                    tabu(1, 15, "rbg403.atsp");
                    tabu(1, 20, "rbg403.atsp");
                    tabu(1, 25, "rbg403.atsp");
                    tabu(1, 30, "rbg403.atsp");
                    tabu(2, 5, "rbg403.atsp");
                    tabu(2, 10, "rbg403.atsp");
                    tabu(2, 15, "rbg403.atsp");
                    tabu(2, 20, "rbg403.atsp");
                    tabu(2, 25, "rbg403.atsp");
                    tabu(2, 30, "rbg403.atsp");
                    symulowane(1, 5, "rbg403.atsp");
                    symulowane(1, 10, "rbg403.atsp");
                    symulowane(1, 15, "rbg403.atsp");
                    symulowane(1, 20, "rbg403.atsp");
                    symulowane(1, 25, "rbg403.atsp");
                    symulowane(1, 30, "rbg403.atsp");
                    symulowane(2, 5, "rbg403.atsp");
                    symulowane(2, 10, "rbg403.atsp");
                    symulowane(2, 15, "rbg403.atsp");
                    symulowane(2, 20, "rbg403.atsp");
                    symulowane(2, 25, "rbg403.atsp");
                    symulowane(2, 30, "rbg403.atsp");

                    break;

            }

        }

    }

    private void symulowane(int temperatura, int seconds, String fileName){

        SymulowaneWyzarzanie sw = new SymulowaneWyzarzanie(numberOfVertex);
        long [] resultArraySW = new long[20];
        int c = 0;

        for(int i = 0; i < 10; i++){

            long [] tmpArray = sw.measureAlgorithm(graph, 0, temperatura, numberOfVertex, 0.96, seconds);

            resultArraySW[c] = tmpArray[0];
            resultArraySW[c + 1] = tmpArray[1];

            c += 2;

        }

        data.saveResultEtap2(temperatura + "_temp_" + seconds + "sek_" + fileName + "Wyzarzanie.txt", resultArraySW);

    }

    private void tabu(int sasiedztwo, int seconds, String fileName){

        TabuSearch ts = new TabuSearch(numberOfVertex);
        long [] resultArray = new long[20];
        int count = 0;

        for(int i = 0; i < 10; i++){

            long [] tmpArray = ts.measureAlgorithm(graph, numberOfVertex, sasiedztwo, 5, 0, seconds);

            resultArray[count] = tmpArray[0];
            resultArray[count + 1] = tmpArray[1];

            count += 2;

        }

        data.saveResultEtap2(sasiedztwo + "_sas_" + seconds + "sek_" + fileName + "TABU.txt", resultArray);

    }

}
