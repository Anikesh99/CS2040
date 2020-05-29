import java.util.ArrayList;

public class TSPGraph implements IApproximateTSP {

    TSPMap map;
    // Empty constructor
    TSPGraph() {
    }

    @Override
    public void MST() {
        // TODO: implement this method
        int count = map.getCount();
        ArrayList<Integer> visited = new ArrayList<>();
        ArrayList<Integer> unvisited = new ArrayList<>();
        visited.add(0);
        for (int i = 1; i < count; i++){
            unvisited.add(i);
        }
        while (visited.size() < count){
            double distance = Double.POSITIVE_INFINITY;
            int index = -1;
            int next = -1;
            for (int i = 0; i < visited.size(); i++){
                for (int j = 0 ; j < unvisited.size(); j++){
                    if (map.getPoint(visited.get(i)).
                            distance(map.getPoint(unvisited.get(j))) < distance){
                        distance = map.getPoint(visited.get(i)).distance(map.getPoint(unvisited.get(j)));
                        index = visited.get(i);
                        next = unvisited.get(j);
                    }
                }
            }
            if (map.getPoint(index).getLink() == -1){
                map.setLink(index, next, false);
            } else {
                map.setLink(next, index, false);
            }
            unvisited.remove((Object)next);
            visited.add(next);
        }
        map.redraw();
    }

    @Override
    public void TSP() {
        // TODO: implement this method
        int count = map.getCount();
        ArrayList<Integer> list = new ArrayList<>();
        ArrayList<Integer> check = new ArrayList<>();
        list.add(0);
        MST();
        while (!list.isEmpty()){
            int now = list.get(0);
            list.remove(list.get(0));
            for (int i = 0; i < count; i++){
                if (!check.contains(i) && (map.getPoint(now).getLink() == i
                        || map.getPoint(i).getLink() == now)){
                    check.add(i);
                    list.add(0, i);
               }
            }
        }
        for (int i = 0; i < check.size() - 1; i++){
            map.setLink(check.get(i), check.get(i + 1), false);
        }
        map.setLink(check.get(check.size() - 1), check.get(0), false);
        map.redraw();
    }

    @Override
    public void initialize(TSPMap map) {
        // TODO: implement this method
        this.map = map;
        this.map.redraw();
    }

    @Override
    public boolean isValidTour() {
        // TODO: implement this method
        int count = map.getCount();
        ArrayList<Integer> list = new ArrayList<>();
        int curr = 0;
        list.add(0);
        for (int i = 0; i < count - 1; i++){
            curr = map.getPoint(curr).getLink();
            if (list.contains(curr) || curr == -1){
                return false;
            }
            list.add(curr);
        }
        if (map.getPoint(curr).getLink() == 0){
            return true;
        }
        return false;
    }

    @Override
    public double tourDistance() {
        // TODO: implement this method
        if (!isValidTour()){
            return -1;
        }
        double distance = 0;
        int count = map.getCount();
        int curr = 0;
        while (count > 0){
            distance += map.pointDistance(curr, map.getPoint(curr).getLink());
            curr = map.getPoint(curr).getLink();
            count--;
        }
        return distance;
    }

    public static void main(String[] args) {
        TSPMap map = new TSPMap(args.length > 0 ? args[0] : "twentypoints.txt");
        TSPGraph graph = new TSPGraph();
        graph.initialize(map);

        graph.MST();
        graph.TSP();
        // System.out.println(graph.isValidTour());
        System.out.println(graph.tourDistance());
    }
}
