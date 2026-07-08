import java.io.*;

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

    public void loadLibrary(User user, String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("Cannot load library: " + e.getMessage());
        }
    }
}

