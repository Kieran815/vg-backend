package com.ivgr.vgdb.repository;

import com.ivgr.vgdb.model.GameList;
import org.springframework.data.jpa.repository.JpaRepository;


// The type of entity and ID that it works with, Category and Long, are specified in the generic parameters on
// JpaRepository. By extending JpaRepository, CategoryRepository inherits several methods for saving, deleting,
// and finding Category entities.
public interface GameListRepository extends JpaRepository <GameList, Long> {

// Spring Data JPA also allows you to define other query methods by declaring their method signature. For example,
// CategoryRepository declares two additional methods: findByName() and findByDescription().

// In a typical Java application, you have to write a class that implements CategoryRepository interface methods.
// However, it is no longer required with Spring Data JPA. It will create the repository implementation automatically,
// at runtime, from the repository interface. That is what makes Spring Data JPA so much powerful.

  GameList findByName(String gameListName);

  GameList findByDescription(String gameListDescription);
}
