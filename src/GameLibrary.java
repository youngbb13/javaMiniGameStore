import java.util.ArrayList;
import java.util.List;

public class GameLibrary<T extends Game> {
    private List<T> items = new ArrayList<>();

    public void add(T item) {
        items.add(item);
    }

    public void showAllGames() {
        for (T game : items) {
            System.out.println(game);
        }
    }

    public T getFirst() {
        if (items.isEmpty()) return null;
        return items.get(0);
    }
}
