package com.ivgr.vgdb.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;

@Entity
@Table(name = "games")
public class Game {
  @Id
  @Column
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column
  // game ID from json result
  private Long api_id;

  @Column
  private String name;

  @ManyToOne
  @JoinColumn(name = "user_id")
  @JsonIgnore
  private User user;

  @JsonIgnore
  @ManyToOne
  @JoinColumn(name = "list_id")
  private GameList gameList;

  public Game() {}

//  ***** GETTERS / SETTERS *****
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getApi_id() {
    return api_id;
  }

  public void setApi_id(Long api_id) {
    this.api_id = api_id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return "Game {" +
        "id = " + id +
        ", api_id = " + api_id +
        ", name = " + name +
        ", gameList = " + gameList +
        "}";
  }

  public GameList getGameList() {
    return gameList;
  }

  public void setGameList(GameList gameList) {
    this.gameList = gameList;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }
}
