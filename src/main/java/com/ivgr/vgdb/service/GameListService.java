package com.ivgr.vgdb.service;

import com.ivgr.vgdb.exception.InfoExistsException;
import com.ivgr.vgdb.exception.InfoNotFoundException;
import com.ivgr.vgdb.model.GameList;
import com.ivgr.vgdb.repository.GameListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GameListService {
  private GameListRepository gameListRepository;

  @Autowired
  public void setGameListRepository(GameListRepository gameListRepository) {
    this.gameListRepository = gameListRepository;
  }

  public List<GameList> getGameLists() {
    System.out.println("GameListService Calling GameLists");
    return gameListRepository.findAll();
  }

  public Optional getGameList(Long gameListId) {
    System.out.println(("GameList Service calling Game List..."));
    Optional gameList = gameListRepository.findById(gameListId);
    if (gameList.isPresent()) {
      return gameList;
    } else {
      throw new InfoNotFoundException("Game List ID: " + gameListId + " not found.");
    }
  }

  public GameList createGameList(GameList gameListObject) {
    System.out.println("service calling createCategory ==>");
    GameList gameList = gameListRepository.findByName(gameListObject.getName());
    if (gameList != null) {
      throw new InfoExistsException("category with name " + gameList.getName() + " already exists");
    } else {
      return gameListRepository.save(gameListObject);
    }
  }

  public GameList updateGameList(Long gameListId, GameList gameListObject) {
    System.out.println("Game Service calling updateGameList");
    Optional<GameList> gameList = gameListRepository.findById(gameListId);
    if (gameList.isPresent()) {
      if (gameListObject.getName().equals(gameList.get().getName())) {
        System.out.println("List Not Updated, Same Name.");
        throw new InfoExistsException("GameList Name: " + gameList.get().getName() + " Already in Use.");
      } else {
        GameList updateGameList = gameListRepository.findById(gameListId).get();
        updateGameList.setName(gameListObject.getName());
        updateGameList.setDescription((gameListObject.getDescription()));
        return gameListRepository.save(updateGameList);
      }
    } else {
      throw new InfoNotFoundException("Game List ID: " + gameListId + " Not Found.");
    }
  }

  public Optional<GameList> deleteGameList(Long gameListId) {
    System.out.println("Service Deleting Game List...");
    Optional<GameList> gameList = gameListRepository.findById(gameListId);

    if (((Optional<?>) gameList).isPresent()) {
      gameListRepository.deleteById(gameListId);
      return gameList;
    } else {
      throw new InfoNotFoundException("Game List ID: " + gameListId + " Not Found.");
    }
  }
}
