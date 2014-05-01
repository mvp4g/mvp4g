package com.mvp4g.example.client.util.token;

public interface TokenGenerator {

  public String convertToToken(String name,
                               String value);

  public String convertFromToken(String token);

}
