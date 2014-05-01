package com.mvp4g.example.client.util.token;

public class BaseTokenGenerator
  implements TokenGenerator {

  public String convertFromToken(String token) {
    return token.split("=")[1];
  }

  public String convertToToken(String name,
                               String value) {
    return name + "=" + value;
  }

}
