import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String [] args){

        SymulowaneWyzarzanie sw;
        TabuSearch ts;
        Data data = new Data();

        List<TabuList> listaTabu = new ArrayList<>();

        //data.readData2("ftv33.atsp");
        data.readData("tsp_17.txt");

        int [][] graph = data.getGraph();
        int numberOfVertex = data.getV();

        //sw = new SymulowaneWyzarzanie(numberOfVertex);
        //sw.algorithm(graph, 2, 2, numberOfVertex * numberOfVertex);

        ts = new TabuSearch(numberOfVertex);
        ts.algorithm(graph, numberOfVertex, listaTabu,0, 5, 2, 10);

    }

}
