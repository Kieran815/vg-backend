package com.ivgr.vgdb.controller;

//import com.ivgr.vgdb.exception.InfoExistsException;
//import com.ivgr.vgdb.exception.InfoNotFoundException;
import com.ivgr.vgdb.exception.InfoNotFoundException;
import com.ivgr.vgdb.model.GameList;
//import com.ivgr.vgdb.repository.GameListRepository;
import com.ivgr.vgdb.service.GameListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(path = "/api")
public class GameListController {

//  private GameListRepository gameListRepo;
  private GameListService gameListService;

  // wires method to `GameListRepository`
  @Autowired
  public void setGameListService(GameListService gameListService) {
    this.gameListService = gameListService;
  }

//  ***** GET ALL LISTS *****
  @GetMapping(path = "/lists")
  public List<GameList> getGameLists() {
    System.out.println("Retrieving Game Lists...");
    return gameListService.getGameLists();
  }

//  ***** GET LIST BY ID *****
  @GetMapping(path = "/lists/{gameListId}")
  public Optional getGameList(@PathVariable Long gameListId) {
    System.out.println("Retrieving GameList ID: " + gameListId);
    return gameListService.getGameList(gameListId);
  }

//  ***** CREATE NEW LIST *****
  @PostMapping(path = "/lists")
  public GameList createGameList(@RequestBody GameList gameListObject) {
    System.out.println("Creating Game List...");
    return gameListService.createGameList(gameListObject);
  }

//  ***** UPDATE GAME LIST DETAILS *****
  @PutMapping(path = "/lists/{gameListId}")
  public GameList updateGameList(@PathVariable(value = "gameListId") Long gameListId, @RequestBody GameList gameListObject) {
    System.out.println("Updating Game Lists:" + gameListId + "...");
    return gameListService.updateGameList(gameListId, gameListObject);
  }

//  ***** REMOVE GAME LIST *****
  @DeleteMapping("/lists/{gameListId}")
  public Optional<GameList> deleteGameList(@PathVariable(value = "gameListId") Long gameListId) {
    System.out.println("Deleting Game List...");
    return gameListService.deleteGameList(gameListId);
  }
}
