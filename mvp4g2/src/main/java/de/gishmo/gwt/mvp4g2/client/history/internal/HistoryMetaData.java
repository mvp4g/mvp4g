package de.gishmo.gwt.mvp4g2.client.history.internal;

import de.gishmo.gwt.mvp4g2.client.history.annotation.History;

/**
 * meta data history annotation
 */
public abstract class HistoryMetaData {

  /* type of the history converter */
  private History.HistoryConverterType             type;

  public HistoryMetaData(History.HistoryConverterType type) {
    this.type = type;
  }

  public History.HistoryConverterType getType() {
    return type;
  }

}
