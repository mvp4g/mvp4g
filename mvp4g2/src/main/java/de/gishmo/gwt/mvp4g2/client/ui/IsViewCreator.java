package de.gishmo.gwt.mvp4g2.client.ui;

import de.gishmo.gwt.mvp4g2.client.ui.annotation.Presenter;

/**
 * Presenters marked with this interface will
 * create the view instance.
 * <br>
 * Make sure, that viewCreator attriburte of the
 *
 * @param <V> type of view
 * @see Presenter#viewCreator() is set to
 * PRESENTER!
 */
public interface IsViewCreator<V extends IsLazyReverseView<?>> {

  V createView();

}
