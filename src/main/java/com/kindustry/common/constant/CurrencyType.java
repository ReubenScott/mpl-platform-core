package com.kindustry.common.constant;

/**
 * 
 * 币种类型
 */
public enum CurrencyType {

  CNY {
    @Override
    public String getValue() {
      return "CNY";
    }

    @Override
    public String toString() {
      return "人民币";
    }
  },
  USD {
    @Override
    public String getValue() {
      return "USD";
    }

    @Override
    public String toString() {
      return "美元";
    }
  },
  HKD {
    @Override
    public String getValue() {
      return "HKD";
    }
    
    @Override
    public String toString() {
      return "港币";
    }
  };

  public abstract String getValue();

}