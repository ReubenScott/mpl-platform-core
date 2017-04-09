package com.soak.common.constant;

public enum BooleanType {
  
  TRUE(1), FALSE(0);
  
  private Integer message;

  BooleanType(Integer message) {
    this.message = message;
  }

  public String toString() {
    return this.message.toString();
  }
  
}
