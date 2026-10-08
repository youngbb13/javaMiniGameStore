package com.example.gamestore.repository;

import com.example.gamestore.entity.DigitalGame;
import com.example.gamestore.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GameRepository extends JpaRepository<DigitalGame, Long> {
    Optional<DigitalGame> findByTitleIgnoreCase(String title);
    List<DigitalGame> findByGenre(Genre genre);
    List<DigitalGame> findByTitleContainingIgnoreCase(String title);
}
