public class Main {

    public static void main(String [] args){

        SymulowaneWyzarzanie sw = new SymulowaneWyzarzanie();
        Data data = new Data();

        data.readData2("ftv33.atsp");
        //data.readData("test17");

        int [][] graph = data.getGraph();
        int numberOfVertex = data.getV();

        sw.setNumberOfVertex(numberOfVertex);
        sw.algorithm(graph, 2, 2, numberOfVertex * numberOfVertex);

    }

}
