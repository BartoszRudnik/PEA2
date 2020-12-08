import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TestUi {

    private final Data data = new Data();

    private int[][] graph;
    private int numberOfVertex;
    private int sec = 5;
    private int sas = 0;
    private int sch = 0;
    private double wsp = 0.99;

    private boolean spr = true;

    public void show(){

        Scanner scanner = new Scanner(System.in);

        while(spr) {

            System.out.println("Wybierz operacje");
            System.out.println("1. Wczytaj dane z pliku");
            System.out.println("2. Wprowadz czas wykonywania sie algorytmu w sekundach");
            System.out.println("3. Wybierz rodzaj sasiedztwa");
            System.out.println("4. Algorytm TS");
            System.out.println("5. Ustawienie wspolczynnika zmiany temperatury");
            System.out.println("6. Algorytm SW");
            System.out.println("7. Wybierz schemat schladzania");
            System.out.println("8. Wyswietl ustawione parametry");
            System.out.println("0. Wyjdz");

            int nrAlg = scanner.nextInt();

            switch (nrAlg){

                case 0:
                    spr = false;
                    break;

                case 1:
                    System.out.println("Podaj nazwe pliku do wczytania: ");

                    scanner.nextLine();
                    String fileName = scanner.nextLine();

                    data.readData2(fileName);

                    graph = data.getGraph();
                    numberOfVertex = data.getV();

                    break;

                case 2:
                    System.out.println("Podaj czas wykonywania sie algorytmu w sekundach: ");

                    try {

                        scanner.nextLine();
                        sec = scanner.nextInt();

                        if(sec <= 0) {
                            sec = 5;
                            throw new Exception();
                        }

                    }catch (Exception ex){

                        System.out.println("Podano bledna wartosc");

                    }

                    break;

                case 3:
                    System.out.println("Wybierz rodzaj sasiedztwa:");
                    System.out.println("_______________");
                    System.out.println("0. Swap");
                    System.out.println("1. Reverse");
                    System.out.println("2. Insert");
                    System.out.println("_______________");

                    try{

                        scanner.nextLine();
                        sas = scanner.nextInt();

                        if(sas < 0 || sas > 2){
                            sas = 0;
                            throw new Exception();
                        }


                    }catch(Exception ex){
                        System.out.println("Wybrano zly rodzaj sasiedztwa");
                    }

                    break;

                case 4:
                    List<TabuList> listaTabu = new ArrayList<>();
                    TabuSearch ts = new TabuSearch(numberOfVertex);

                    ts.algorithm(graph, numberOfVertex, listaTabu, sas, 5, 2, sec);

                    break;

                case 5:
                    System.out.println("Podaj wartosc wspolczynnika zmiany temperatury: ");

                    try{

                        scanner.nextLine();
                        wsp = scanner.nextDouble();

                        if(wsp <= 0.0 || wsp >= 1.0) {
                            wsp = 0.99;
                            throw new Exception();
                        }

                    }catch (Exception ex){
                        System.out.println("Podano zla wartosc wspolczynnika");
                    }

                    break;

                case 6:
                    SymulowaneWyzarzanie sw = new SymulowaneWyzarzanie(numberOfVertex);

                    sw.algorithm(graph, sas, 0, 10, wsp);

                    break;

                case 7:
                    System.out.println("Wybierz schemat schladzania");
                    System.out.println("_____________________________");
                    System.out.println("0. Schladzanie geometryczne");
                    System.out.println("1. Schladzanie liniowe");
                    System.out.println("2. Schladzanie logarytmiczne");
                    System.out.println("_____________________________");

                    try{

                        scanner.nextLine();
                        sch = scanner.nextInt();

                        if(sch < 0 || sch > 2){
                            sch = 0;
                            throw new Exception();
                        }

                    }catch (Exception ex){
                        System.out.println("Wybrano zly schemat");
                    }

                case 8:
                    System.out.println("Biezace parametry");
                    System.out.println("_____________________________");
                    System.out.println("Czas trwania algorytmu[s]: " + sec);

                    if(sas == 0)
                        System.out.println("Rodzaj sasiedztwa: Swap");
                    else if(sas == 1)
                        System.out.println("Rodzaj sasiedztwa: Reverse");
                    else if(sas == 2)
                        System.out.println("Rodzaj sasiedztwa: Insert");

                    System.out.println("Wspolczynik zmiany temperatury: " + wsp);

                    if(sch == 0)
                        System.out.println("Schemat schladzania: geometryczny");
                    else if(sch == 1)
                        System.out.println("Schemat schladzania: liniowy");
                    else if(sch == 2)
                        System.out.println("Schemat schladzania: logarytmiczny");
                    System.out.println("_____________________________");

                    break;

            }

        }

    }

}
