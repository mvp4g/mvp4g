package de.gishmo.gwt.mvp4g2.client.application;

import de.gishmo.gwt.mvp4g2.client.eventbus.IsEventBus;
import de.gishmo.gwt.mvp4g2.client.history.PlaceService;

/**
 * type of the eventBus
 */
public abstract class AbstractApplication<E extends IsEventBus>
  implements IsApplication {

  /* the eventbus */
  protected E            eventBus;
  /* the PlaceService */
  private   PlaceService<? extends IsEventBus> placeService;

  @Override
  public void run() {
    // execute the loader (if one is present)
    getApplicationLoader().load(() -> onFinishLaoding());
  }

  protected abstract IsApplicationLoader getApplicationLoader();

  /**
   * Once the loader did his job, we will continue
   */
  private void onFinishLaoding() {
    // create place service and bind
    this.placeService = new PlaceService<E>(this.eventBus);
    this.eventBus.setPlaceService(this.placeService);
    // start the application
    placeService.startApplication();
  }
}
