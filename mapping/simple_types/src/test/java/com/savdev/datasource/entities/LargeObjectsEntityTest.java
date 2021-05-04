package com.savdev.datasource.entities;

import com.savdev.datasource.MySqlLiquibaseBaseIT;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.stream.IntStream;

public class LargeObjectsEntityTest extends MySqlLiquibaseBaseIT {

  private static final long ID = 1L;

  @Autowired
  private EntityManager entityManager;

  @Test
  public void testLargeObjects() throws SQLException, IOException {
//    final long STREAM_SIZE = 10L;
//
//    LargeObjectsEntity largeObjectsEntity = new LargeObjectsEntity();
//    largeObjectsEntity.setId(ID);
//
//    java.sql.Connection connection = entityManager.unwrap(java.sql.Connection.class);
//    Blob blob = connection.createBlob();
//    OutputStream outputStream = blob.setBinaryStream(STREAM_SIZE);
//    largeObjectsEntity.setBlob(blob);
//
//    entityManager.persist(largeObjectsEntity);
//
//    IntStream.rangeClosed(1, 10).forEach(i -> {
//      try {
//        outputStream.write(("some text with value " + i).getBytes(StandardCharsets.UTF_8));
//      } catch (Exception e) {
//        throw new IllegalStateException();
//      }});
//
//    LargeObjectsEntity found = entityManager.find(LargeObjectsEntity.class, ID);
//    Blob foundBlob = found.getBlob();
//    Assertions.assertNotNull(foundBlob);
//    InputStream inputStream = foundBlob.getBinaryStream();
//    byte[] b = new byte[(int)STREAM_SIZE];
//    while(inputStream.read(b) > 0) {
//      System.out.println(new String(b, StandardCharsets.UTF_8));
//    }

  }
}
