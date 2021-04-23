package com.savdev.datasource.repositories;

import com.savdev.datasource.entities.NativeOnRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.cdi.Eager;

import java.util.List;

import static com.savdev.datasource.entities.NativeOnRepository.Persistence.TABLE_NAME;

@Eager
public interface NativeOnRepositoryRepository extends JpaRepository<NativeOnRepository, Long> {


  @Query(value = "SELECT * FROM " + TABLE_NAME, nativeQuery = true)
  List<NativeOnRepository> findAllByNativeQuery();
}
