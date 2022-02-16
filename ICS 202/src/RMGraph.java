import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.AllDirectedPaths;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultDirectedWeightedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class RMGraph {

    public static void main(String[] args) throws FileNotFoundException {
        FileInputStream input = new FileInputStream("C:\\Users\\Kroom Saleh\\Desktop\\Project\\Blue Lane.csv");
        FileInputStream input2 = new FileInputStream("C:\\Users\\Kroom Saleh\\Desktop\\Project\\Red Lane.csv");
        FileInputStream input3 = new FileInputStream("C:\\Users\\Kroom Saleh\\Desktop\\Project\\Purble lane.csv");
        Scanner kb = new Scanner(input);
        Scanner kb2 = new Scanner(input2);
        Scanner kb3 = new Scanner(input3);
        String[] A = new String[25];
        String[] Blue= new String[25];
        String[] Red= new String[15];
        String[] Purble= new String[9];
        int[] Blue1= new int[25];
        int[] Red1= new int[15];
        int[] Purble1= new int[9];
        kb.nextLine();
        kb2.nextLine();
        kb3.nextLine();
        int i=0;
        while(kb.hasNext()){
            A=kb.nextLine().split(",");
            Blue[i] = A[0];
            Blue1[i]= Integer.parseInt(A[1]);
            i++;
        }
        i=0;
        while(kb2.hasNext()){
            A=kb2.nextLine().split(",");
            Red[i] = A[0];
            Red1[i]= Integer.parseInt(A[1]);
            i++;
        }
        i=0;
        while(kb3.hasNext()){
            A=kb3.nextLine().split(",");
            Purble[i] = A[0];
            Purble1[i]= Integer.parseInt(A[1]);
            i++;
        }
        DefaultDirectedWeightedGraph<String,
                DefaultWeightedEdge> RM = new DefaultDirectedWeightedGraph<String,DefaultWeightedEdge>(DefaultWeightedEdge.class);
        RM.addVertex(Blue[0]);
        for(int k=1;k<Blue.length;k++){
            RM.addVertex(Blue[k]);
            RM.setEdgeWeight(RM.addEdge(Blue[k-1],Blue[k]),Blue1[k-1]);

        }
        RM.addVertex(Red[0]);
        for(int k=1;k<Red.length;k++){
            for(int j=0;j< Blue.length;j++){
                if(Red[k].length()>3) {
                    if (Red[k].substring(4).equals(Blue[j].substring(0, 3))) {
                        Red[k] = Blue[j];
                    }
                }
            }
            RM.addVertex(Red[k]);
            RM.setEdgeWeight(RM.addEdge(Red[k-1],Red[k]),Red1[k-1]);

        }
        Purble[0]=Blue[2];
        RM.addVertex(Purble[0]);
        for(int k=1;k<Purble.length;k++){
            for(int j=0;j< Blue.length;j++){
                if(Purble[k].length()>3) {
                    if (Purble[k].substring(4).equals(Blue[j].substring(0, 3))) {
                        Purble[k] = Blue[j];
                    }
                }
            }
            for(int j=0;j< Red.length;j++){
                if(Purble[k].length()>3) {
                    if (Purble[k].substring(4).equals(Red[j].substring(0, 3))) {
                        Purble[k] = Red[j];
                    }
                }
            }
            RM.addVertex(Purble[k]);
            RM.setEdgeWeight(RM.addEdge(Purble[k-1],Purble[k]),Purble1[k-1]);

        }
        System.out.println(RM.toString());
        DijkstraShortestPath dsp = new DijkstraShortestPath(RM);
        AllDirectedPaths asp =new AllDirectedPaths(RM);
        Scanner kb4 = new Scanner(System.in);
        int choice=3;
        try {
            System.out.println("METRO PROJECT: Enter your choice?\n 1) Find the fastest route between two stations.\n 2) Find all the routes between two stations and the time for each route.\n 3) End\n");
            choice = kb4.nextInt();
        }catch (InputMismatchException e){
            System.out.println("Wrong Entry!");
        }
        switch (choice){
            case 1:
               try {
                   System.out.println("Enter the two Stations:");

                   String stat1 = kb4.next();
                   String stat2 = kb4.next();
                   if(dsp.getPath(stat1,stat2)==null){
                       System.out.println("NO routes between these two Stations");
                   }else {
                       System.out.println("The path is: " + dsp.getPath(stat1, stat2) + "\n The length is: " + dsp.getPathWeight(stat1, stat2));
                   }
               }catch (IllegalArgumentException e){
                   System.out.println("Wrong Station Name!");
               }
                break;
            case 2:
               try{ System.out.println("Enter the two Stations:");
                String stat3=kb4.next();
                String stat4=kb4.next();
                GraphPath[] paths=new GraphPath[5];
                ArrayList paths1= (ArrayList) asp.getAllPaths(stat3,stat4,false,Integer.MAX_VALUE);
                for(int k=0;k<paths1.size();k++) {
                    paths[k] = (GraphPath) paths1.get(k);
                    System.out.println("Path " + (k + 1) + ":" + paths[k]);
                    System.out.println("Time is :" + paths[k].getWeight());
                }
                if(paths1.size()==0){
                    System.out.println("NO routes between these two Stations");
                }
                }catch (IllegalArgumentException e){
                   System.out.println("Wrong Station Name!");
               }
                break;
            case 3:
                break;
        }

    }
}
