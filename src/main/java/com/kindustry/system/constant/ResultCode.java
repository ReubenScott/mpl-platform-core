package com.kindustry.system.constant;

public enum ResultCode {
  
  SUCCESS("1"), FAILED("0");
  
  private String message;

  ResultCode(String message) {
    this.message = message;
  }

  public String toString() {
    return this.message;
  }
}
