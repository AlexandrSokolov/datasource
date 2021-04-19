package com.savdev.datasource.repositories;

import com.savdev.datasource.entities.ExampleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.cdi.Eager;

@Eager
public interface ExampleEntityRepository extends JpaRepository<ExampleEntity, Long> {
}
