package com.adventofcode2019;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws IOException {

        var layers = parse(Files.readString(Paths.get("1.in")), 25, 6);

        var layerWithFewestZeroes = layers.stream()
                .map(List::stream)
                .map(s -> s.collect(Collectors.groupingBy(Function.identity(), Collectors.counting())))
                .sorted((f1, f2) -> (int) (f1.getOrDefault(0, 0L) - f2.getOrDefault(0, 0L)))
                .findFirst()
                .orElseThrow();

        System.out.println("Part 1: " + layerWithFewestZeroes.get(1) * layerWithFewestZeroes.get(2));

        Collections.reverse(layers);

        var image = layers.stream().reduce((below, above) -> {
            var merged = new ArrayList<Integer>();

            for (int i = 0; i < above.size(); i++) {
                var color1 = below.get(i);
                var color2 = above.get(i);

                merged.add(color2 == 2 ? color1 : color2);
            }

            return merged;
        }).orElseThrow();

        System.out.println("Part 2:");
        System.out.println();
        System.out.println(render(image, 25, 6));
    }

    static List<List<Integer>> parse(String spaceImageFormat, int width, int height) {
        var layers = new ArrayList<List<Integer>>();
        var size = width * height;

        for (int i = 0; i < spaceImageFormat.length(); i += size) {
            layers.add(Arrays.stream(spaceImageFormat.substring(i, i + size).split("")).map(Integer::valueOf).collect(Collectors.toList()));
        }

        return layers;
    }

    static String render(List<Integer> image, int width, int height) {
        var sb = new StringBuilder();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                var color = image.get(y * width + x);
                sb.append(color == 0 ? "█" : "░");
            }
            
            sb.append(System.lineSeparator());
        }
        
        return sb.toString();
    }
}
