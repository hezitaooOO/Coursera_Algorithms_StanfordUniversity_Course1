import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

/*
 * This class represents a graph.
 * @author: Zitao He
 * */
public class Graph {

    private HashMap<Integer, GraphVertex> vertices;
    private ArrayList<GraphEdge> edges;
    private String inputFileName;

    public Graph() {
        this.vertices = new HashMap<Integer, GraphVertex>();
        this.edges = new ArrayList<GraphEdge>();
        this.inputFileName = "";
    }
    public Graph(String inputFileName){
        this.vertices = new HashMap<Integer, GraphVertex>();
        this.edges = new ArrayList<GraphEdge>();
        this.inputFileName = inputFileName;
    }

    public void buildGraphFromFile() throws FileNotFoundException {

        this.vertices = new HashMap<Integer, GraphVertex>();
        this.edges = new ArrayList<GraphEdge>();

        try {
            Scanner fileScanner = new Scanner(new File(inputFileName));
            //add vertices
            while(fileScanner.hasNextLine()){
                Scanner lineScanner = new Scanner(fileScanner.nextLine());
                int startId = lineScanner.nextInt();
                //add a vertex using the first int value of each row in file as vertex ID
                vertices.put(startId, new GraphVertex(startId));
            }
            //add edges
            fileScanner = new Scanner(new File(inputFileName));
            while(fileScanner.hasNextLine()){
                Scanner lineScanner = new Scanner(fileScanner.nextLine());
                int startId = lineScanner.nextInt();
                while(lineScanner.hasNextInt()){
                    int endId = lineScanner.nextInt();
                    GraphVertex startVertex = vertices.get(startId);
                    GraphVertex endVertex = vertices.get(endId);
                    startVertex.addEdge(endVertex);
                    this.edges.add(new GraphEdge(startVertex, endVertex));
                }
            }
        }
        catch (IOException e) {
            throw new FileNotFoundException("Error: Input file is not found.");
        }
    }

    public void addVertex(int vertexId) throws IllegalArgumentException {
        if (vertices.containsKey(vertexId)) {
            throw new IllegalArgumentException("Vertex ID: " + vertexId + " already exists.");
        }
        GraphVertex toAdd = new GraphVertex(vertexId);
        vertices.put(vertexId, toAdd);
    }

    public void addEdge(int startId, int endId) {
        GraphVertex startVertex = vertices.get(startId);
        GraphVertex endVertex = vertices.get(endId);
        //add edge to edge list
        this.edges.add(new GraphEdge(startVertex, endVertex));
        this.edges.add(new GraphEdge(endVertex, startVertex));
        //add edge to start and end vertices
        startVertex.addEdge(endVertex);
        endVertex.addEdge(startVertex);
    }
    public int minimumCut() throws FileNotFoundException {
        int minCut;
        //Algorithm:
        while(vertices.keySet().size() > 2){
            // Obtain a number between [0 - edges.size-1].
            Random rand = new Random();
            //System.out.println("Current edge #: " + edges.size());
            int n = rand.nextInt(edges.size());
            //System.out.println("Random integer generated is: " + n);
            GraphEdge edgeRandom = edges.get(n);
            //System.out.println("Random edge selected is: " + edgeRandom);

            GraphVertex randVertexStart = edgeRandom.getStartVertex();
            GraphVertex randVertexEnd = edgeRandom.getEndVertex();

            //add new edges between neighboring vertices of end vertex and start vertex
            for (GraphVertex neighbor : randVertexEnd.getNeighbors()){

                GraphEdge neighborToStart = new GraphEdge(neighbor, randVertexStart);
                neighbor.addEdge(neighborToStart);
                edges.add(neighborToStart);

                neighbor.deleteEdge(randVertexEnd);

                GraphEdge startToNeighbor = new GraphEdge(randVertexStart, neighbor);
                randVertexStart.addEdge(startToNeighbor);
                edges.add(startToNeighbor);

            }

            //add newly added edges to this graph

            //remove all edges of end vertex
            randVertexEnd.cleanEdges();

            //remove self edges for start vertex
            randVertexStart.cleanSelfEdges();

            //remove end vertex in the graph
            vertices.remove(randVertexEnd.getVertexId());

            //remove any edges associated with end vertex in graph
            //remove any self edges in this graph
            Iterator<GraphEdge> itr = edges.iterator();
            while (itr.hasNext()) {
                GraphEdge edge = itr.next();
                if (edge.getEndVertex() == randVertexEnd || edge.getStartVertex() == randVertexEnd) {
                    itr.remove();
                }
                if (edge.getStartVertex() == edge.getEndVertex()){
                    itr.remove();
                }
            }

            //printGraph();
            //System.out.println(edges);
        }
        minCut = vertices.get(vertices.keySet().iterator().next()).getEdges().size();

        //recover the graph from input file
        buildGraphFromFile();
        return minCut;
    }

    public int getNumVertices(){
        return vertices.keySet().size();
    }

    public int getNumEdges(){
        return edges.size();
    }

    public void printGraph(){
        for (int id : vertices.keySet()){
            System.out.println("Vertex " + id + " has neighbors: " +  vertices.get(id).getNeighbors());
        }
    }

    public static void main(String[] args) throws FileNotFoundException {

        Graph test = new Graph("data/kargerMinCut.txt");
        test.buildGraphFromFile();
        int n = test.getNumVertices();
        int trials = 1000;

        //double trials = n * n * Math.log(n);
        //test.printGraph();
        int minCut = test.getNumEdges();
        for (int i = 0; i < trials; i++){
            int currMinCut = test.minimumCut();
            if (currMinCut < minCut){
                minCut = currMinCut;
            }
            System.out.println("Running trial: " + i + "Min cut found in this trial is: "+ minCut +". Total trial: "+ trials);
        }
        System.out.println("Final minimum cut found is: " + minCut);
        //test.printGraph();
    }
}