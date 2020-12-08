import java.util.Scanner;

public class TestUi {

    private final Data data = new Data();

    private int [][] graph;
    private int numberOfVertex;
    private int liczbaSekund = 5;
    private int rodzajSasiedztwa = 0;
    private int schematSchladzania = 0;
    private int kryteriumAspiracji = 0;
    private int strategiaDywersyfikacji = 5;
    private int liczbaIteracji = 10;
    private double zmianaTemperatury = 0.99;

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
            System.out.println("9. Wybierz kryterium aspiracji");
            System.out.println("10. Ustal strategie dywersyfikacji");
            System.out.println("11. Ustal liczbe iteracji");
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
                        liczbaSekund = scanner.nextInt();

                        if(liczbaSekund <= 0) {
                            liczbaSekund = 5;
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
                        rodzajSasiedztwa = scanner.nextInt();

                        if(rodzajSasiedztwa < 0 || rodzajSasiedztwa > 2){
                            rodzajSasiedztwa = 0;
                            throw new Exception();
                        }


                    }catch(Exception ex){
                        System.out.println("Wybrano zly rodzaj sasiedztwa");
                    }

                    break;

                case 4:
                    TabuSearch ts = new TabuSearch(numberOfVertex);

                    ts.algorithm(graph, liczbaIteracji, rodzajSasiedztwa, strategiaDywersyfikacji, kryteriumAspiracji, liczbaSekund);

                    break;

                case 5:
                    System.out.println("Podaj wartosc wspolczynnika zmiany temperatury: ");

                    try{

                        scanner.nextLine();
                        zmianaTemperatury = scanner.nextDouble();

                        if(zmianaTemperatury <= 0.0 || zmianaTemperatury >= 1.0) {
                            zmianaTemperatury = 0.99;
                            throw new Exception();
                        }

                    }catch (Exception ex){
                        System.out.println("Podano zla wartosc wspolczynnika");
                    }

                    break;

                case 6:
                    SymulowaneWyzarzanie sw = new SymulowaneWyzarzanie(numberOfVertex);

                    sw.algorithm(graph, rodzajSasiedztwa, schematSchladzania, liczbaIteracji, zmianaTemperatury, liczbaSekund);

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
                        schematSchladzania = scanner.nextInt();

                        if(schematSchladzania < 0 || schematSchladzania > 2){
                            schematSchladzania = 0;
                            throw new Exception();
                        }

                    }catch (Exception ex){
                        System.out.println("Wybrano zly schemat");
                    }

                case 8:
                    System.out.println("Biezace parametry");
                    System.out.println("_____________________________");
                    System.out.println("Czas trwania algorytmu[s]: " + liczbaSekund);

                    if(rodzajSasiedztwa == 0)
                        System.out.println("Rodzaj sasiedztwa: Swap");
                    else if(rodzajSasiedztwa == 1)
                        System.out.println("Rodzaj sasiedztwa: Reverse");
                    else if(rodzajSasiedztwa == 2)
                        System.out.println("Rodzaj sasiedztwa: Insert");

                    System.out.println("Wspolczynik zmiany temperatury: " + zmianaTemperatury);

                    if(schematSchladzania == 0)
                        System.out.println("Schemat schladzania: geometryczny");
                    else if(schematSchladzania == 1)
                        System.out.println("Schemat schladzania: liniowy");
                    else if(schematSchladzania == 2)
                        System.out.println("Schemat schladzania: logarytmiczny");

                    if(kryteriumAspiracji == 0)
                        System.out.println("Kryterium aspiracji: najmniejsza wartosc");
                    else if(kryteriumAspiracji == 1)
                        System.out.println("Kryterium aspiracji: najmniejsza wartosc przemnozona przez parametr");
                    else if(kryteriumAspiracji == 2)
                        System.out.println("Kryterium aspiracji: ostatnia wartosc");

                    System.out.println("Po ilu iteracjach bez poprawy wygenerowac nowe rozwiazanie startowe: " + strategiaDywersyfikacji);
                    System.out.println("Liczba iteracji: " + liczbaIteracji);

                    System.out.println("_____________________________");

                    break;

                case 9:
                    System.out.println("Wybierz kryterium aspiracji");
                    System.out.println("0. Najmniejsza wartosc");
                    System.out.println("1. Najmniejsza wartosc przemnozona przez parametr");
                    System.out.println("2. Ostatnia wartosc");

                    try {
                        scanner.nextLine();
                        kryteriumAspiracji = scanner.nextInt();

                        if(kryteriumAspiracji < 0 || kryteriumAspiracji > 2){
                            kryteriumAspiracji = 0;
                            throw new Exception();
                        }

                    } catch (Exception ex){
                        System.out.println("Wybrano zly numer");
                    }

                    break;

                case 10:
                    System.out.println("Podaj po ilu iteracjach bez poprawy wygenerowac nowe rozwiazanie startowe");

                    try{

                        scanner.nextLine();
                        strategiaDywersyfikacji = scanner.nextInt();

                        if(strategiaDywersyfikacji <= 0 || strategiaDywersyfikacji > liczbaIteracji){

                            strategiaDywersyfikacji = 5;
                            throw new Exception();

                        }

                    }catch (Exception ex){
                        System.out.println("Podano zla wartosc");
                    }

                case 11:
                    System.out.println("Podaj liczbe iteracji");

                    try {

                        scanner.nextLine();
                        liczbaIteracji = scanner.nextInt();

                        if(liczbaIteracji <= 0){

                            liczbaIteracji = 10;
                            throw new Exception();

                        }

                    }catch (Exception e){
                        System.out.println("Podano zla wartosc");
                    }

            }

        }

    }

}
