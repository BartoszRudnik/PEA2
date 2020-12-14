public class Main {

    public static void main(String [] args){

        //UI ui = new UI();
        //ui.showMenu();

        Data data = new Data();
        FunkcjePomocnicze pomoc = new FunkcjePomocnicze();

        int [][] graph;
        int numberOfVertex;

        data.readData("tsp_6_2.txt");

        graph = data.getGraph();
        numberOfVertex = data.getV();

        pomoc.setNumberOfVertex(numberOfVertex);

        int route[] = pomoc.algorytmZachlanny(graph);

        for(int i = 0; i < route.length; i++){

            System.out.print(route[i] + " ");

        }
        System.out.println();

        System.out.println(pomoc.getRouteCost(graph, route));

    }

}
