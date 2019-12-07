package com.adventofcode2019;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws IOException {

        var map = Files.lines(Paths.get("1.in"))
                .map(s -> s.split("\\)"))
                .collect(Collectors.groupingBy(arr -> arr[0],
                        Collectors.mapping(arr -> arr[1], Collectors.toSet())));
        
        var root = parse(null, "COM", map);
        var nodes = getAllNodes(root);
        var you = nodes.stream().filter(n -> "YOU".equals(n.getName())).findFirst().map(Node::getParent).orElseThrow();
        var santa = nodes.stream().filter(n -> "SAN".equals(n.getName())).findFirst().map(Node::getParent).orElseThrow();

        System.out.println("Part 1: " + nodes.stream().mapToInt(Main::depth).sum());
        System.out.println("Part 2: " + dijkstra(nodes, you, santa));
    }

    static List<Node> getAllNodes(Node node) {
        var nodes = new ArrayList<Node>();

        nodes.add(node);
        node.getChildren().forEach(child -> nodes.addAll(getAllNodes(child)));

        return nodes;
    }

    static int depth(Node node) {
        int depth = 0;

        while (node.getParent() != null) {
            node = node.getParent();
            depth++;
        }

        return depth;
    }

    static int dijkstra(List<Node> nodes, Node from, Node to) {

        var unvisited = new ArrayList<>(nodes);
        var distances = unvisited.stream().collect(Collectors.toMap(n -> n.getName(), n -> Integer.MAX_VALUE));

        distances.put(from.getName(), 0);

        while (!unvisited.isEmpty() && unvisited.contains(to)) {
            var current = unvisited.stream().sorted((n1, n2) -> distances.get(n1.getName()) - distances.get(n2.getName())).findFirst().orElseThrow();
            var parent = current.getParent();

            if (parent != null && unvisited.contains(parent)) {
                var distance = distances.get(current.getName()) + 1;

                if (distance < distances.get(parent.getName())) {
                    distances.put(parent.getName(), distance);
                }
            }

            for (var child : current.getChildren()) {
                if (unvisited.contains(child)) {
                    var distance = distances.get(current.getName()) + 1;

                    if (distance < distances.get(child.getName())) {
                        distances.put(child.getName(), distance);
                    }
                }
            }

            unvisited.remove(current);
        }

        return distances.get(to.getName());
    }

    static Node parse(Node parent, String name, Map<String, Set<String>> map) {
        Node node = new Node(parent, name);

        if (map.containsKey(name)) {
            for (var childName : map.get(name)) {
                node.getChildren().add(parse(node, childName, map));
            }
        }

        return node;
    }
}

class Node {

    private final Node parent;
    private final String name;
    private final List<Node> children;

    public Node(Node parent, String name, Node... children) {
        this.parent = parent;
        this.name = name;
        this.children = new ArrayList<>(Arrays.asList(children));
    }

    public Node getParent() {
        return parent;
    }

    public String getName() {
        return name;
    }

    public List<Node> getChildren() {
        return children;
    }
}
