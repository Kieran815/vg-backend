package com.ivgr.vgdb.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

  @Id
  @Column
  @GeneratedValue(strategy =  GenerationType.IDENTITY)
  private Long id;

  private String userName;

  @Column(unique = true)
  private String emailAddress;

  @Column
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  private String password;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "profile_id", referencedColumnName = "id")
  private UserProfile userProfile;

  @OneToMany(mappedBy = "user")
  @LazyCollection(LazyCollectionOption.FALSE)
  // list of GAMES (recipe)
  private List<Game> gameList;

  @OneToMany(mappedBy = "user")
  @LazyCollection(LazyCollectionOption.FALSE)
  // list of GAMES LISTS (category)
  private List<GameList> gameListList;

  public UserProfile getUserProfile() {
    return userProfile;
  }

  public void setUserProfile(UserProfile userProfile) {
    this.userProfile = userProfile;
  }

  public User() {
  }

  public User(Long id, String userName, String emailAddress, String password) {
    this.id = id;
    this.userName = userName;
    this.emailAddress = emailAddress;
    this.password = password;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getEmailAddress() {
    return emailAddress;
  }

  public void setEmailAddress(String emailAddress) {
    this.emailAddress = emailAddress;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public List<Game> getGameList() {
    return gameList;
  }

  public void setGameList(List<Game> gameList) {
    this.gameList = gameList;
  }

  public List<GameList> getGameListList() {
    return gameListList;
  }

  public void setGameListList(List<GameList> gameListList) {
    this.gameListList = gameListList;
  }

  @Override
  public String toString() {
    return "User{" +
        "id=" + id +
        ", userName='" + userName + '\'' +
        ", emailAddress='" + emailAddress + '\'' +
        ", password='" + password + '\'' +
        '}';
  }

}