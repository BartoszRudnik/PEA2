public class Main {

    public static void main(String [] args){

        SymulowaneWyzarzanie sw = new SymulowaneWyzarzanie();
        Data data = new Data();

        //data.readData2("ftv47.atsp");
        //data.readData("test15");

        int [][] graph = data.getGraph();
        int numberOfVertex = data.getV();

        sw.setNumberOfVertex(numberOfVertex);
        sw.algorithm(graph, 2, 2, 100);

    }

}
