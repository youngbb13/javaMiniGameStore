public class DigitalGame implements Game {
    private String title;
    private double price;
    private String genre;

    public DigitalGame(String title, double price, String genre) {
        this.title = title;
        this.price = price;
        this.genre = genre;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public double getPrice() {
        return price;
    }

    public String getGenre() {
        return genre;
    }

    @Override
    public void play() {
        System.out.println("Playing " + getTitle());
    }

    @Override
    public String toString() {
        return  title;
    }
}
