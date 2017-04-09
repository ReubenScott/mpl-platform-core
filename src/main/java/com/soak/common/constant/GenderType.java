package com.soak.common.constant;

public enum GenderType {
  
  MALE("男性"), FEMALE("女性");
  
  private String message;

  private GenderType(String message) {
    this.message = message;
  }

  public String toString() {
    return message;
  }
  
}
