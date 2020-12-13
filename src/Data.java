import java.io.*;
import java.util.Random;

public class Data {

    private int [][] graph;
    private int v;

    public int[][] getGraph(){
        return this.graph;
    }

    public int getV(){
        return this.v;
    }

    public void saveResult(String fileName, long[] tab){

        try{

            BufferedWriter bw = new BufferedWriter(new FileWriter(fileName,true));

            for (long l : tab) {

                bw.write(Long.toString(l));
                bw.newLine();

            }

            bw.close();


        } catch (IOException e) {
            e.getMessage();
        }

    }

    public void saveResultEtap2(String fileName, long[] tab){

        try{

            BufferedWriter bw = new BufferedWriter(new FileWriter(fileName,true));

            for(int j = 0; j < tab.length; j++) {

                if(j % 2 == 0)
                    bw.newLine();

                bw.write(tab[j] + " ");

            }

            bw.close();

        } catch (IOException e) {
            e.getMessage();
        }

    }

    public void printData(){

        if(graph != null){

            System.out.println(v);

            for(int i = 0; i < v; i++){

                for(int j = 0; j < v; j++){

                    System.out.printf("%-10d", graph[i][j]);

                }

                System.out.println();

            }

        }

    }

    public boolean checkGraph(int [][] graph){

        if(v <= 1)
            return false;

        for(int i = 0; i < v; i++){

            if(graph[i][i] != -1)
                return false;

        }

        return true;

    }

    public void generateRandomDataAsymetric(int vertex){

        v = vertex;

        graph = new int[vertex][vertex];

        Random rand = new Random();

        for(int i = 0; i < vertex; i++){

            for(int j = 0; j < vertex; j++){

                if(i == j)
                    graph[i][j] = -1;
                else
                    graph[i][j] = rand.nextInt(100);

            }

        }

    }

    public void generateRandomDataSymetric(int vertex){

        v = vertex;
        graph = new int[vertex][vertex];
        boolean [][] paths = new boolean[vertex][vertex];

        Random rand = new Random();

        for(int i = 0; i < vertex; i++){

            for(int j = 0; j < vertex; j++){

                if( i == j)
                    graph[i][j] = -1;
                else if(!paths[i][j]){

                    int tmp = rand.nextInt(100);

                    graph[i][j] = graph[j][i] = tmp;
                    paths[i][j] = paths[j][i] = true;

                }

            }

        }

    }

    public void readData(String fileName) {

        try {

            FileInputStream fileInputStream = new FileInputStream(fileName);
            BufferedReader br = new BufferedReader(new InputStreamReader(fileInputStream));

            String line = br.readLine();

            if(line != null)
                v = Integer.parseInt(line);

            graph = new int[v][v];

            for(int i = 0; i < v; i++){

                line = br.readLine();
                String[] str = line.trim().split("\\s+");

                for(int j = 0; j < v; j++)
                {
                    graph[i][j] = Integer.parseInt(str[j]);
                }

            }

        } catch(IOException e){
            System.out.println(e.getMessage());
        }

    }

    public void readData2(String fileName){

        try {

            FileInputStream fileInputStream = new FileInputStream(fileName);
            BufferedReader br = new BufferedReader(new InputStreamReader(fileInputStream));

            String line = "";

            for(int i = 0; i < 4; i++)
                line = br.readLine();

            line = line.substring(11);

            if(line != null)
                v = Integer.parseInt(line);

            graph = new int[v][v];

            for(int i = 0; i < 3; i++)
                br.readLine();

            int count = 0;

            for(int i = 0; i < v;){

                line = br.readLine();
                String[] str = line.trim().split("\\s+");

                for (String s : str) {
                    graph[i][count] = Integer.parseInt(s);
                    count++;

                    if (count == v) {
                        i++;
                        count = 0;
                    }

                }

            }

        } catch(IOException e){
            System.out.println(e.getMessage());
        }

    }

}