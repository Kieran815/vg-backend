package com.ivgr.vgdb.service;

import com.ivgr.vgdb.exception.InfoExistsException;
import com.ivgr.vgdb.exception.InfoNotFoundException;
import com.ivgr.vgdb.model.Game;
import com.ivgr.vgdb.model.GameList;
import com.ivgr.vgdb.repository.GameListRepository;
import com.ivgr.vgdb.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class GameListService {
  private GameListRepository gameListRepository;
  private GameRepository gameRepository;

  @Autowired
  public void setGameListRepository(GameListRepository gameListRepository) {
    this.gameListRepository = gameListRepository;
  }

  @Autowired
  public void setGameRepository(GameRepository gameRepository) {
    this.gameRepository = gameRepository;
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
      throw new InfoExistsException("gameList with name " + gameList.getName() + " already exists");
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
    if (gameList.isPresent()) {
      gameListRepository.deleteById(gameListId);
      return gameList;
    } else {
      throw new InfoNotFoundException("Game List ID: " + gameListId + " Not Found.");
    }
  }
  
//  *************************************************************************************************************

  public Game createGameListGame(Long gameListId, Game gameObject) {
    System.out.println(("GameListService adding game to List: " + gameListId));
    try {
      Optional gameList = gameListRepository.findById(gameListId);
      gameObject.setGameList((GameList) gameList.get());
      return gameRepository.save(gameObject);
    } catch (NoSuchElementException e) {
      throw new InfoNotFoundException("GameList ID: " + gameListId + " Not Found.");
    }
  }

  public List<Game> getGameListGames(Long gameListId) {
    System.out.println("Calling getGameListGames from GameListServices...");
    Optional<GameList> gameList = gameListRepository.findById(gameListId);
    if (gameList.isPresent()) {
      return gameList.get().getGameList();
    } else {
      throw new InfoNotFoundException("GameList ID: " + gameListId + " Not Found.");
    }
  }

  public Game getGameListGame(Long gameListId, Long gameId) {
    System.out.println("GameListService calling getGameListGame...");
    Optional<GameList> gameList = gameListRepository.findById(gameListId);
    if (gameList.isPresent()) {
      Optional<Game> game = gameRepository.findByGameListId(gameListId).stream().filter(
          gameListItem -> gameListItem.getId().equals(gameId)
      ).findFirst();
      if(game.isEmpty()) {
        throw new InfoNotFoundException("Game ID: " + gameId + " Not Found.");
      } else {
        return game.get();
      }
    } else {
      throw new InfoNotFoundException("GameList ID: " + gameListId + " Not Found");
    }
  }

//  ********** WILL NOT BE USING **********
  //  game data based on api, so will not be updating items, only deleting from game lists.
    // *** left in for navigation purposes and debugging ***
//  public Game updateGameListGame(Long gameListId, Long gameId, Game gameObject) {
//    System.out.println("GameListService calling updateGameListGame...");
//    try {
//      Game game = (gameRepository.findByGameListId(gameListId).stream().filter(gameListItem -> gameListItem.getId().equals(gameId)).findFirst()).get();
//      game.setName(gameObject.getName());
//    } catch {
//      throw new InfoNotFoundException("Game or GameList Not Found");
//    }
//  }

  public void deleteGameListGame(Long gameListId, Long gameId) {
    try {
      Game game = (gameRepository.findByGameListId(gameListId).stream().filter(gameListItem -> gameListItem.getId().equals(gameId)).findFirst()).get();
      gameRepository.deleteById(game.getId());
    } catch (NoSuchElementException e) {
      throw new InfoNotFoundException("Game or GameList Not Found");
    }
  }
}
