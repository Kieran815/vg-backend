package com.ivgr.vgdb.controller;

import com.ivgr.vgdb.exception.InfoExistsException;
import com.ivgr.vgdb.exception.InfoNotFoundException;
import com.ivgr.vgdb.model.GameList;
import com.ivgr.vgdb.repository.GameListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api")
public class GameListController {

  private GameListRepository gameListRepo;

  // wires method to `GameListRepository`
  @Autowired
  public void setGameListRepo(GameListRepository gameListRepo) {
    this.gameListRepo = gameListRepo;
  }

//  ***** GET ALL LISTS *****
  @GetMapping(path = "/lists")
  public List<GameList> getGameLists() {
    System.out.println("Retrieving Game Lists...");
    return gameListRepo.findAll();
  }

//  ***** GET LIST BY ID *****
//  @GetMapping(path = "/lists/{listId}")
//  public Optional getGameList(@PathVariable Long gameListId) throws Exception {
//    System.out.println("Retrieving GameList ID: " + gameListId);
//    Optional gameList = gameListRepo.findById(gameListId);
//    if (gameList.isPresent()) {
//      return gameList;
//    } else {
//      throw new InfoNotFoundException("Game List with ID: " + gameListId + " Not Located.");
//    }
//  }

//  ***** CREATE NEW LIST *****
  @PostMapping(path = "/lists")
  public GameList createGameList(@RequestBody GameList gameListObject) {
    System.out.println("Creating Game List...");
    GameList gameList = gameListRepo.findByName(gameListObject.getName());
    if(gameList != null) {
      throw new InfoExistsException("Game List " + gameList.getName() + " Already Exists.");
    } else {
      return gameListRepo.save(gameListObject);
    }
  }

//  ***** UPDATE GAME LIST DETAILS *****
  @PutMapping(path = "/lists/{gameListId}")
  public GameList updateGameList(@PathVariable(value = "gameListId") Long gameListId, @RequestBody GameList gameListObject) {
    System.out.println("Updating Game Lists:" + gameListId + "...");
    Optional<GameList> gameList = gameListRepo.findById(gameListId);
    if (gameList.isPresent()) {
      if (gameListObject.getName().equals(gameList.get().getName())) {
        System.out.println("Original List Title is the Same as the Updated List Title.");
        throw new InfoExistsException("Original List Title is the Same as the Updated List Title.");
      } else {
        GameList updateGameList = gameListRepo.findById(gameListId).get();
        updateGameList.setName(gameListObject.getName());
        updateGameList.setDescription(gameListObject.getDescription());
        return gameListRepo.save(updateGameList);
      }
    } else {
      throw new InfoNotFoundException("Game List: " + gameListId + " Not Found.");
    }
  }

//  ***** REMOVE GAME LIST *****
  @DeleteMapping("/lists/{gameListId}")
  public Optional<GameList> deleteGameList(@PathVariable(value = "gameListId") Long gameListId) {
    System.out.println("Deleting Game List...");
    Optional<GameList> gameList = gameListRepo.findById((gameListId));
    if(gameList.isPresent()) {
      gameListRepo.deleteById(gameListId);
      return gameList;
    } else {
      throw new InfoNotFoundException("Game List ID: " + gameListId + " Not Found.");
    }
  }
}
