package com.savdev.datasource.app;

import javax.ejb.Stateless;

@Stateless
public class ApplicationService {

  public void handle(){
    System.out.println("ApplicationService.handle()");
  }
}
