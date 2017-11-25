package de.gishmo.gwt.mvp4g2.client.ui;

/**
 * <p>Marks an class as mvp4g2 shell.</p>
 */
public interface IsShell {

  /**
   * This method is used by the framework, to delegate the adding
   * of the shell to the user. Here the user has to add the shell
   * of the application to the viewport.
   * <p>
   * It is a good idea to use a presenter/view pair as shell:
   * <p>
   * <code>
   *
   * @Override public void setShell() {
   * RootLayoutPanel.get().add(view.asWidget());
   * }
   * </code>
   * <p>
   * <p>This will make the framework indepent of GWT or user implemantations!</p>
   */
  void setShell();

}
