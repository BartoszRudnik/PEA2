public class Main {

    public static void main(String [] args){

        SymulowaneWyzarzanie sw = new SymulowaneWyzarzanie();
        Data data = new Data();

        data.readData("test10");

        int [][] graph = data.getGraph();
        int numberOfVertex = data.getV();

        /*sw.setNumberOfVertex(numberOfVertex);
        for(int i = 0; i < 50; i++) {
            sw.algorithm(graph, 0, 0, 100);
        }*/

        data.readData2("rbg403.atsp");
        data.printData();

    }

}
