import java.math.BigDecimal;

public class GameDto {
    private String title;
    private BigDecimal price;
    private String genre;

    public GameDto(String title, BigDecimal price, String genre) {
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

    public String getGenre() {
        return genre;
    }
}
