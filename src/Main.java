import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Main {

    public static void main(String [] args){

        SymulowaneWyzarzanie sw = new SymulowaneWyzarzanie();
        Data data = new Data();

        data.readData("test10");

        int [][] graph = data.getGraph();
        int numberOfVertex = data.getV();

        sw.setNumberOfVertex(numberOfVertex);
        sw.algorithm(graph, 3);

    }

}
