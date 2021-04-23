package com.savdev.datasource.repositories;

import com.savdev.datasource.entities.NativeOnEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.cdi.Eager;

import java.util.List;

@Eager
public interface NativeOnEntityRepository extends JpaRepository<NativeOnEntity, Long> {

  List<NativeOnEntity> findAllByNative();
}
