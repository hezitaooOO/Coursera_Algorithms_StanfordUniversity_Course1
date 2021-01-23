/*
 * This class represents a edge used in graph.
 * @author: Zitao He
 * */
public class GraphEdge {

    //Note that the edge is un-directional even if we specify start and end vertex
    private GraphVertex startVertex;
    private GraphVertex endVertex;

    public GraphEdge(GraphVertex startVertex, GraphVertex endVertex){
        this.startVertex = startVertex;
        this.endVertex = endVertex;
    }

    public void resetEnd(GraphVertex endVertex){
        this.endVertex = endVertex;
    }

    public void resetStart(GraphVertex startVertex){
        this.startVertex = startVertex;
    }

    public GraphVertex getStartVertex() {
        return startVertex;
    }

    public GraphVertex getEndVertex() {
        return endVertex;
    }

    @Override
    public String toString(){
        return "Edge is Start: " + startVertex.getVertexId() + ", End: " + endVertex.getVertexId();
    }
}
