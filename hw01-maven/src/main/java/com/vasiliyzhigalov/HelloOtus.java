package com.vasiliyzhigalov;

import com.google.common.graph.GraphBuilder;
import com.google.common.graph.MutableGraph;
public class HelloOtus {
    public static void main(String[] args) {
        MutableGraph<Integer> graph = GraphBuilder.undirected().allowsSelfLoops(false).build();
        graph.putEdge(1,2);
        graph.putEdge(1,3);
        graph.putEdge(2,4);
        graph.putEdge(4,5);
        graph.putEdge(5,6);
        graph.putEdge(3,6);
        System.out.println(graph);
    }
}
