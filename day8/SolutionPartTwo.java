package day8;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SolutionPartTwo {
     public static void main(String[] args) {
        String filePath = "day8/tests/input.txt";
        long result = 0;
        try {
            List<String> manifold = Files.readAllLines(Path.of(filePath));
            ArrayList<JunctionBox> boxes = new ArrayList<>();
            ArrayList<Set<JunctionBox>> circuits = new ArrayList<>();
            
            for (int i = 0; i < manifold.size(); i++) {
                String[] cords = manifold.get(i).split(",");
                JunctionBox box = new JunctionBox(Long.parseLong(cords[0]), Long.parseLong(cords[1]), Long.parseLong(cords[2]));
                boxes.add(box);
                Set<JunctionBox> solo = new HashSet<>();
                solo.add(box);
                circuits.add(solo);
            }

            ArrayList<Distance> distances = new ArrayList<>();
            for(int i = 0; i < boxes.size()-1; i++) {
                for(int j = i+ 1; j < boxes.size(); j++) {
                    double distance = Math.sqrt(Math.pow(boxes.get(j).X - boxes.get(i).X,2) + Math.pow(boxes.get(j).Y - boxes.get(i).Y, 2) + Math.pow(boxes.get(j).Z - boxes.get(i).Z, 2));
                    distances.add(new Distance(distance, boxes.get(i), boxes.get(j)));
                }
            }

            distances.sort((d1, d2) -> Double.compare(d1.d, d2.d));

            for(Distance dis : distances) {
                int indexOfJ1 = containsJunctionBox(dis.j1, circuits);
                int indexOfJ2 = containsJunctionBox(dis.j2, circuits);
                if (indexOfJ1 != indexOfJ2) {
                    if(circuits.size() == 2) {
                        result = dis.j1.X * dis.j2.X;
                    }
                    circuits.get(indexOfJ1).addAll(circuits.get(indexOfJ2));
                    circuits.remove(indexOfJ2);
                    circuits.removeIf(Set::isEmpty);                        
                }
            }
        } catch (IOException ex) {
            System.out.println("Some error happened");
        } finally {
            System.out.println(String.format("Result: %d", result));
            System.out.println();
        }

    }

    private record JunctionBox(long X, long Y, long Z) {}
    private record Distance(double d, JunctionBox j1, JunctionBox j2) {}

    private static int containsJunctionBox(JunctionBox j, ArrayList<Set<JunctionBox>> circuits) {
        for(int i = 0; i < circuits.size(); i++) {
            if(circuits.get(i).contains(j)) return i;
        }

        return -1;
    } 

}
