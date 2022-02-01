package com.ivgr.vgdb.controller;
import com.ivgr.vgdb.model.Game;
import com.ivgr.vgdb.model.GameList;
import com.ivgr.vgdb.service.GameListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(path = "/api")
public class GameListController {

// `GameListRepository` moved to `gameListService`;
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
  public GameList getGameList(@PathVariable Long gameListId) {
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
  @DeleteMapping(path = "/lists/{gameListId}")
  public String deleteGameList(@PathVariable(value = "gameListId") Long gameListId) {
    System.out.println("Deleting Game List...");
    return gameListService.deleteGameList(gameListId);
  }

//  ***** ADD GAME TO LIST *****
  @PostMapping(path = "/lists/{gameListId}/games")
  public Game createGameListGame(@PathVariable(value = "gameListId") Long gameListId, @RequestBody Game gameObject) {
    System.out.println("Calling createGameListGame from GameListController");
    return gameListService.createGameListGame(gameListId, gameObject);
  }

  //  ***** GET GAMES IN LIST *****
  @GetMapping(path = "/lists/{gameListId}/games")
  public List<Game> getGameListGames(@PathVariable(value = "gameListId") Long gameListId) {
    System.out.println("Calling `getGameListGames` from `GameListController`");
    return gameListService.getGameListGames(gameListId);
  }

  //  ***** GET GAME FROM GAME LIST *****
  @GetMapping(path = "/lists/{gameListId}/games/{gameId}")
  public Game getGameListGame(@PathVariable(value = "gameListId") Long gameListId, @PathVariable(value = "gameId") Long gameId) {
    System.out.println(("Calling getGameListGame from GameListController"));
    return gameListService.getGameListGame(gameListId, gameId);
  }

//  ********** WILL NOT BE USING UPDATE GAME **********
  //  game data based on api, so will not be updating items, only deleting from game lists.
  // *** left in for navigation purposes and debugging ***
//  @PutMapping(path = "/lists/{gameListId/games/{gameId}")
//  public Game updateGameListGame(@PathVariable(value = "gameListId") Long gameListId, @PathVariable(value = "gameId") Long gameId, @RequestBody Game gameObject) {
//    System.out.println("Calling updateGameListGame from GameListController");
//    return gameListService.updateGameListGame(gameListId, gameId, gameObject);
//  }

  //  ***** REMOVE GAME FROM GAME LIST *****
  @DeleteMapping(path = "/lists/{gameListId}/games/{gameId}")
  public ResponseEntity<HashMap> deleteGameListGame(
      @PathVariable(value = "gameListId") Long gameListId, @PathVariable(value = "gameId") Long gameId
  ) {
    System.out.println("Calling deleteGameListGame from GameList Controller");
    gameListService.deleteGameListGame(gameListId, gameId);
    HashMap responseMessage = new HashMap();
    responseMessage.put("status", "Game with ID: " + gameId + " was successfully deleted.");
    return new ResponseEntity<HashMap>(responseMessage, HttpStatus.OK);
  }

}
