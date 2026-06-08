import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class LibraryFileService {
    public void saveLibrary(User user, String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (Game game : user.getGamesLibrary()) {
                writer.write(game.getTitle());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Cannot save library: " + e.getMessage());
        }
    }
}
