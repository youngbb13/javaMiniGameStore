import java.math.BigDecimal;

public class GameDto {
    private String title;
    private BigDecimal price;
    private Genre genre;

    public GameDto(String title, BigDecimal price, Genre genre) {
        this.title = title;
        this.price = price;
        this.genre = genre;
    }

    public String getTitle() {
        return title;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Genre getGenre() {
        return genre;
    }
}
