package com.vasiliyzhigalov.services.dbservices;

public class DbServiceException extends RuntimeException {
  public DbServiceException(Exception e) {
    super(e);
  }
}
