package com.example.gamestore;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GameRepository extends JpaRepository<DigitalGame, Long> {
    Optional<DigitalGame> findByTitleIgnoreCase(String title);
    List<DigitalGame> findByGenre(Genre genre);

    Long id(Long id);
}
