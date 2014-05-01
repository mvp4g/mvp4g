package com.mvp4g.example.client.main.historyConverter;

import com.google.inject.Inject;
import com.mvp4g.client.annotation.History;
import com.mvp4g.client.annotation.History.HistoryConverterType;
import com.mvp4g.client.history.HistoryConverter;
import com.mvp4g.example.client.main.MainEventBus;
import com.mvp4g.example.client.util.token.TokenGenerator;

@History(type = HistoryConverterType.SIMPLE)
public class MenuHistoryConverter
  implements HistoryConverter<MainEventBus> {

  @Inject
  private TokenGenerator tokenGenerator;

  public String convertToToken(String eventName,
                               int start,
                               int end) {
    return tokenGenerator.convertToToken("start",
                                         Integer.toString(start)) + "&"
      + tokenGenerator.convertToToken("end",
                                      Integer.toString(end));
  }

  public void convertFromToken(String eventType,
                               String param,
                               MainEventBus eventBus) {
    String[] paramTab = param.split("&");
    int start = Integer.parseInt(tokenGenerator.convertFromToken(paramTab[0]));
    int end = Integer.parseInt(tokenGenerator.convertFromToken(paramTab[1]));
    eventBus.dispatch(eventType,
                      start,
                      end);
  }

  public boolean isCrawlable() {
    return true;
  }

}
