import java.math.BigDecimal;

public class DigitalGame implements Game {
    private final String title;
    private final BigDecimal price;
    private final String genre;

    public DigitalGame(String title, BigDecimal price, String genre) {
        this.title = title;
        this.price = price;
        this.genre = genre;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public BigDecimal getPrice() {
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

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        else if (o == null || getClass() != o.getClass())
            return false;

        DigitalGame game = (DigitalGame) o;
        return title.equals(game.title);
    }

    @Override
    public int hashCode() {
        return title.hashCode();
    }
}
