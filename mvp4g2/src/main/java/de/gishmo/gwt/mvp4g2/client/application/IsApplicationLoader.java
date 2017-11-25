package de.gishmo.gwt.mvp4g2.client.application;

/**
 * MVP4G2 application loader
 * <p>
 * <p>The Loader is executed during the start sequence of the application.
 * The loader can be used to load meta-informations at the the start of the applilcation</p>
 * <p>Once the work is done call finishLoadCommand.finishLoad() to resume with the normal processing.</p>
 */
public interface IsApplicationLoader {

  void load(FinishLoadCommand finishLoadCommand);

  interface FinishLoadCommand {

    void finishLoading();

  }
}
