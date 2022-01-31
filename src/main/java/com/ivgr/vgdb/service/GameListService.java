package com.ivgr.vgdb.service;

import com.ivgr.vgdb.exception.InfoExistsException;
import com.ivgr.vgdb.exception.InfoNotFoundException;
import com.ivgr.vgdb.model.Game;
import com.ivgr.vgdb.model.GameList;
import com.ivgr.vgdb.repository.GameListRepository;
import com.ivgr.vgdb.repository.GameRepository;
import com.ivgr.vgdb.security.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class GameListService {

  private GameListRepository gameListRepository;
  private GameRepository gameRepository;

  private static final Logger LOGGER = Logger.getLogger(UserService.class.getName());

  @Autowired
  public void setGameListRepository(GameListRepository gameListRepository) {
    this.gameListRepository = gameListRepository;
  }

  @Autowired
  public void setGameRepository(GameRepository gameRepository) {
    this.gameRepository = gameRepository;
  }

  public List<GameList> getGameLists() {
    LOGGER.info("GameListService Calling GameLists...");
    MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    System.out.println("USER DETAILS" + userDetails);
    System.out.println(userDetails.getUser().getId());
    List<GameList> gameList = gameListRepository.findByUserId(userDetails.getUser().getId());
    if (gameList.isEmpty()) {
      throw new InfoNotFoundException("No Game Lists Associated with User ID: " + userDetails.getUser().getId() + ".");
    } else {
      return gameList;
    }
  }

  public GameList getGameList(Long gameListId) {
    LOGGER.info("GameList Service calling Game List...");
    MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    GameList gameList = gameListRepository.findByIdAndUserId(gameListId, userDetails.getUser().getId());
    if (gameList == null) {
      throw new InfoNotFoundException("GameList ID: " + gameListId + " Not Found.");
    } else {
      return gameList;
    }
  }

  public GameList createGameList(GameList gameListObject) {
    LOGGER.info("Service Creating Game List...");
    MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    GameList gameList = gameListRepository.findByUserIdAndName(userDetails.getUser().getId(), gameListObject.getName());
    if (gameList != null) {
      throw new InfoExistsException("gameList with name " + gameList.getName() + " already exists");
    } else {
      gameListObject.setUser(userDetails.getUser());
      return gameListRepository.save(gameListObject);
    }
  }

  public GameList updateGameList(Long gameListId, GameList gameListObject) {
    LOGGER.info("Game Service calling updateGameList");
    MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    GameList gameList = gameListRepository.findByIdAndUserId(gameListId, userDetails.getUser().getId());
    if (gameList == null) {
      throw new InfoNotFoundException("Game List ID: " + gameListId + " Not Found.");
    } else {
      gameList.setName(gameListObject.getName());
      gameList.setDescription((gameListObject.getDescription()));
      gameList.setUser(userDetails.getUser());
      return gameListRepository.save(gameList);
    }
  }

  public String deleteGameList(Long gameListId) {
    LOGGER.info("Service Deleting Game List...");
    MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    GameList gameList = gameListRepository.findByIdAndUserId(gameListId, userDetails.getUser().getId());
    if (gameList == null) {
      throw new InfoNotFoundException("Game List ID: " + gameListId + " Not Found.");
    } else {
      gameListRepository.deleteById(gameListId);
      return "GameList ID: " + gameListId + " Successfully Deleted";
    }
  }
  
//  *************************************************************************************************************

  public Game createGameListGame(Long gameListId, Game gameObject) {
    LOGGER.info(("GameListService adding game to List: " + gameListId));
    MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    GameList gameList = gameListRepository.findByIdAndUserId(gameListId, userDetails.getUser().getId());
    if (gameList == null) {
      throw new InfoNotFoundException("GameList ID: " + gameList.getId() + " Not Found");
    }
    Game game = gameRepository.findByUserIdAndName( userDetails.getUser().getId(), gameObject.getName());
    if (game != null) {
      throw new InfoExistsException("Game: " + gameObject.getName() + " is Already On this List.");
    }
    gameObject.setUser(userDetails.getUser());
    gameObject.setGameList(gameList);
    return gameRepository.save(gameObject);
  }

  public List<Game> getGameListGames(Long gameListId) {
    LOGGER.info("Calling getGameListGames from GameListServices...");
    MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    GameList gameList = gameListRepository.findByIdAndUserId(gameListId, userDetails.getUser().getId());
    if (gameList == null) {
      throw new InfoNotFoundException("GameList ID: " + gameListId + " Not Found.");
    }
    return gameList.getGameList();
  }

  public Game getGameListGame(Long gameListId, Long gameId) {
    LOGGER.info("GameListService calling getGameListGame...");
    MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    GameList gameList = gameListRepository.findByIdAndUserId(gameListId, userDetails.getUser().getId());
    if (gameList == null) {
      throw new InfoNotFoundException("GameList ID: " + gameListId + " Not Found");
    }
    Optional<Game> game = gameRepository.findByGameListId(gameListId).stream().filter(gameItem -> gameItem.getId().equals(gameId)).findFirst();
    if (!game.isPresent()) {
      throw new InfoNotFoundException("Game ID: " + gameId + " Not Found.");
    }
    return game.get();
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
    LOGGER.info("Deleting Game from GameList ID: " + gameListId + "...");
    MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    GameList gameList = gameListRepository.findByIdAndUserId(gameListId, userDetails.getUser().getId());
    if (gameList == null) {
      throw new InfoNotFoundException("Game List ID: " + gameListId + " Not Found.");
    }
    Optional<Game> game = gameRepository.findByGameListId(gameListId).stream().filter(gameItem -> gameItem.getId().equals(gameId)).findFirst();
    if (!game.isPresent()) {
      throw new InfoNotFoundException("Game ID: " + gameId + " Not Found");
    }
    gameRepository.deleteById(game.get().getId());
  }
}
