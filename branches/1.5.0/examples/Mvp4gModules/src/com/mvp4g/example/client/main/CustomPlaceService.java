package com.mvp4g.example.client.main;

import com.mvp4g.client.history.PlaceService;

public class CustomPlaceService
  extends PlaceService {

  @Override
  public String tokenize(String eventName,
                         String param) {
    //always add the paramSeparator since "/" is used for module separator and paramSeparator
    String token = eventName + getParamSeparator();
    if ((param != null) && (param.length() > 0)) {
      token = token + param;
    }
    return token;
  }

  protected String getParamSeparator() {
    return "/";
  }

}
