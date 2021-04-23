package com.savdev.datasource.repositories;

import com.savdev.datasource.entities.JpqlOnEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.cdi.Eager;

import java.util.List;

@Eager
public interface JpqlOnEntityRepository extends JpaRepository<JpqlOnEntity, Long> {

  List<JpqlOnEntity> findAllByJpql();
}
