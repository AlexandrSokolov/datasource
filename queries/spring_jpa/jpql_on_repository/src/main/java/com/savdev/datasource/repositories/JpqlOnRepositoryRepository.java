package com.savdev.datasource.repositories;

import com.savdev.datasource.entities.JpqlOnRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.cdi.Eager;

import java.util.List;

@Eager
public interface JpqlOnRepositoryRepository extends JpaRepository<JpqlOnRepository, Long> {


  @Query("SELECT e FROM " + JpqlOnRepository.Persistence.ENTITY_NAME + " e")
  List<JpqlOnRepository> findAllByExplicitJpql();
}
