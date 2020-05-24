import org.w3c.dom.Node;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Main {

    private static Scanner sc = new Scanner(System.in);

    private static int[] dist;
    private static int[] vertexes;
    private static int[] parent;
    private static int[][] weightMatrix;
    private static int n;
    private static PriorityQueue<Pair> priorityQueue = new PriorityQueue<Pair>();


    public static int[][] readMatrix()
    {
        String path = "";
        System.out.print("Enter your matrix file path: ");
        path = sc.nextLine();

        int[][] weightMatrix = null;

        try
        {
            BufferedReader bw = new BufferedReader(new FileReader(path));

            ArrayList<String> lines = new ArrayList();
            String newLine;
            int countLine = 0;

            while((newLine = bw.readLine())!=null)
            {
                countLine++;
                lines.add(newLine);
            }

            weightMatrix = new int[countLine][countLine];

            for(int i=0; i<countLine; i++)
            {
                String currentLine = lines.get(i);
                String[] splitString = currentLine.split(" ");
                int j = 0;

                for(String s : splitString)
                {
                    weightMatrix[i][j] = Integer.parseInt(s);
                    j++;
                }
            }


        } catch (IOException e)
        {
            System.out.println("Error, file not found!");
        }

        return weightMatrix;
    }

    public static void findShortestPath()
    {

        int source, destination;

        //Read matrix from file
        weightMatrix = readMatrix();
        n = weightMatrix.length;

        System.out.println("\nNOTICE: NODE INDEX STARTS FROM 1!");
        System.out.print("Enter source node index: ");
        source = Integer.parseInt(sc.nextLine());
        System.out.print("Enter destination node index: ");
        destination = Integer.parseInt(sc.nextLine());


        //Set all dist elements to infinity
        dist = new int[n];
        parent = new int[n];

        for(int i=0; i<n; i++)
        {
            dist[i]= Integer.MAX_VALUE;
        }

        source-=1;
        destination-=1;

        //Insert source destination to the queue
        Pair sourcePair = new Pair(0, source);
        priorityQueue.add(sourcePair);
        dist[source] = 0;

        //Priority Queue operations
        while(priorityQueue.peek()!=null)
        {
            Pair currentPair = priorityQueue.poll();

            for(int x = 0; x < n; x++)
            {
                int vertex = currentPair.getVertex();
                int weight = weightMatrix[vertex][x];

                if(weight == 0)
                {
                    continue;
                }

                if(dist[x] > dist[vertex] + weight)
                {
                    dist[x] = dist[vertex] + weight;
                    priorityQueue.add(new Pair(dist[x],x));
                    parent[x] = vertex;
                }

            }
        }

        int current = destination;
        int currentWeight = 0;
        String message="";

        message+= ( "-> Destination node " + (destination+1) + '\n');
        while(current!=source)
        {
            currentWeight = dist[current] - dist[parent[current]];
            message=("-> Node " + (parent[current] + 1) + " - Weight to next node: " + (currentWeight) + " - Total path weight to next node:  " + dist[current] + '\n') + message;
            current = parent[current];
        }
        message= ("\nShortest path from node " + (source+1) + " to node " + (destination+1)  + '\n') + message;
        message+= "Total weight is " + dist[destination];
        System.out.println(message);

    }

    public static void main(String[] args)
    {
        //LƯU Ý: SỐ THỨ TỰ NODE BẮT ĐẦU TỪ 1
        findShortestPath();
    }
}

class Pair implements Comparable<Pair>{

    private int weight;
    private int vertex;

    public Pair(int weight, int vertex)
    {
        this.weight = weight;
        this.vertex = vertex;
    }

    public void setVertex(int vertex) {
        this.vertex = vertex;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getVertex() {
        return vertex;
    }

    public int getWeight() {
        return weight;
    }

    public String toString()
    {
        return "["+weight + "," + (vertex+1) +"]";
    }
    @Override
    public int compareTo(Pair o) {
        return this.weight - o.getWeight();
    }
}


