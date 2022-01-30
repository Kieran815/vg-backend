package com.ivgr.vgdb.model;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;

@Entity // indicating that it is a JPA entity
@Table(name = "lists")
public class GameList {

  @Id // JPA recognizes it as the object’s ID
  @Column
  @GeneratedValue(strategy = GenerationType.IDENTITY) // indicate that the ID should be generated automatically
  private Long id;

  // left unannotated
    // mapped to columns that have the same names as the properties themselves.
  @Column
  private String name;

  @Column
  private String description;

  @OneToMany(mappedBy = "gameList", orphanRemoval = true)
  @LazyCollection(LazyCollectionOption.FALSE)
  private List<Game> gameList;

  // use to create instances of `list` to be saved to the database
  public GameList(Long id, String name, String description) {
    this.id = id;
    this.name = name;
    this.description = description;
  }

  // The no-argument `GameList` constructor is only required for JPA
  public GameList() { }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

//  Temp Output for Lists
  @Override
  public String toString() {
    return "GameList {" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", description='" + description + '\'' +
        '}';
  }

  public List<Game> getGameList() {
    return gameList;
  }

  public void setGameList(List<Game> gameList) {
    this.gameList = gameList;
  }
}
