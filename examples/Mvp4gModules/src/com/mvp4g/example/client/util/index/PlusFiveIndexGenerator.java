package com.mvp4g.example.client.util.index;

public class PlusFiveIndexGenerator
  implements IndexGenerator {

  public int generateIndex(int baseIndex) {
    return baseIndex + 5;
  }

}
