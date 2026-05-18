import java.math.BigDecimal;

public interface Game {
    String getTitle();
    String getGenre();
    BigDecimal getPrice();
    void play();
}
