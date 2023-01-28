
package com.example.minesweeper;

import java.util.List;
import org.springframework.data.repository.CrudRepository;

import com.example.minesweeper.models.highscores;

public interface Database extends CrudRepository<highscores, Integer> {
    List<highscores> findByName(String name);
    
    highscores findById(int id);
}
