package com.mvp4g.client.history;

/**
 * <p>HistoryProxyProvider</p>
 * <p>This provider provioes an instance of ths history proxy of your application
 * depending on the value of the @see com.mvp4g.client.annotation.Events annotation.</p>
 *
 * @author: Frank Hossfeld
 */
public class HistoryProxyProvider {

  public static HistoryProxyProvider INSTANCE = new HistoryProxyProvider();

  private HistoryProxy historyProxy;

  private HistoryProxyProvider() {
  }

  public void set(HistoryProxy historyProxy) {
    this.historyProxy = historyProxy;
  }

  public HistoryProxy get() {
    return this.historyProxy;
  }
}
