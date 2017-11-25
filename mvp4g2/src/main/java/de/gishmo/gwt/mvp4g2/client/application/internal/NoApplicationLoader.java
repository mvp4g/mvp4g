package de.gishmo.gwt.mvp4g2.client.application.internal;

import de.gishmo.gwt.mvp4g2.client.annotation.internal.ForInternalUseOnly;
import de.gishmo.gwt.mvp4g2.client.application.IsApplicationLoader;

/**
 * Default applilcation loader
 * <p>
 * <p>does nothing</p>
 * <p>
 * <p>Used by the framework</p>
 */
@ForInternalUseOnly
public final class NoApplicationLoader
  implements IsApplicationLoader {

  @Override
  public void load(FinishLoadCommand finishLoadCommand) {
    finishLoadCommand.finishLoading();
  }
}
