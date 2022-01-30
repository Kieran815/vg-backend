package com.ivgr.vgdb.repository;

import com.ivgr.vgdb.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {

  Game findByName(String gameName);

  Game findByNameAndIdIsNot(String gameName, Long gameId);

  List<Game> findByGameListId(Long gameId);
}
