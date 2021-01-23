import java.util.ArrayList;
import java.util.Iterator;

/*
 * This class represents a vertex(node) used in graph.
 * @author: Zitao He
 * */
public class GraphVertex extends Object{

    private int vertexId;
    private ArrayList<GraphEdge> edges;

    public GraphVertex(int vertexId){
        this.vertexId = vertexId;
        edges = new ArrayList<GraphEdge>();
    }

    //add an outgoing edge to end vertex (input is an vertex object)
    public void addEdge(GraphVertex end){
        GraphEdge toAdd = new GraphEdge(this, end);
        edges.add(toAdd);
    }

    //add an outgoing edge to end vertex (input is another edge)
    public void addEdge(GraphEdge toAdd) throws IllegalArgumentException{
        if (toAdd.getStartVertex() != this){
            throw new IllegalArgumentException("Error: edge to add does not start with this node.");
        }
        edges.add(toAdd);
    }
    public void deleteEdge(GraphVertex end){

        Iterator<GraphEdge> itr = edges.iterator();
        while (itr.hasNext()) {
            GraphEdge edge = itr.next();
            if (edge.getEndVertex() == end) {
                itr.remove();
            }
        }

    }
    public void cleanEdges(){
        edges = new ArrayList<GraphEdge>();
    }

    public void cleanSelfEdges(){
        Iterator<GraphEdge> itr = edges.iterator();
        while (itr.hasNext()) {
            GraphEdge edge = itr.next();
            if (edge.getEndVertex() == this) {
                itr.remove();
            }
        }
    }

    public ArrayList<GraphVertex> getNeighbors(){
        ArrayList<GraphVertex> neighbors = new ArrayList<GraphVertex>();
        for (GraphEdge edge : edges){
            neighbors.add(edge.getEndVertex());
        }
        return neighbors;
    }

    public int getVertexId() {
        return vertexId;
    }

    public ArrayList<GraphEdge> getEdges() {
        return edges;
    }

    @Override
    public String toString(){
        return String.valueOf(vertexId);
    }

}
