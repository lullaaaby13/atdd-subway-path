package nextstep.subway.domain;

import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.WeightedMultigraph;

import java.util.List;
import java.util.stream.Collectors;

public class LinePath {

    private List<Line> lines;
    private WeightedMultigraph<Station, DefaultWeightedEdge> graph;

    public LinePath(List<Line> lines) {
        this.lines = lines;
        this.graph = createPathGraph();
    }

    public int getShortestDistance(Station source, Station target) {
        return (int) new DijkstraShortestPath(graph).getPath(source, target).getWeight();
    }

    public List<Station> getStations(Station source, Station target) {
        return new DijkstraShortestPath(graph).getPath(source, target).getVertexList();
//        return graph.vertexSet().stream().collect(Collectors.toList());
    }

    private WeightedMultigraph createPathGraph() {
        WeightedMultigraph<Station, DefaultWeightedEdge> graph = new WeightedMultigraph(DefaultWeightedEdge.class);
        for (Line line : lines) {
            List<Section> sections = line.getAllSections();
            for (Section section : sections) {
                graph.addVertex(section.getUpStation());
                graph.addVertex(section.getDownStation());
                graph.setEdgeWeight(graph.addEdge(section.getUpStation(), section.getDownStation()), section.getDistance());
            }
        }

        return graph;
    }

}
