package com.mvp4g.client.presenter;

import com.mvp4g.client.event.EventBus;

/**
 * Interface that defines a presenter.<br/>
 * <br/>
 * This interface provides getter and setter for a view and an event bus.
 * 
 * @author plcoirier
 * 
 */
public interface PresenterInterface<V, E extends EventBus> {

	/**
	 * Set an event bus to the presenter
	 * 
	 * @param eventBus
	 *            event bus to set
	 */
	public void setEventBus( E eventBus );
	
	/**
	 * Get the view associated with the presenter
	 * 
	 * @return eventBus manipulated by the presenter.
	 */
	public E getEventBus();

	/**
	 * Sets the view associated with the presenter.
	 * 
	 * @param view
	 *            view to set
	 */
	public void setView( V view );

	/**
	 * Gets the view associated with the presenter.
	 * 
	 * @return view manipulated by the presenter.
	 */
	public V getView();

	/**
	 * Performs required bindings between this Presenter and its View.</p>
	 * 
	 * This is automatically invoked when the View is wired into the Presenter.
	 * 
	 */
	public void bind();
	
	/**
	 * Call the bind method is needed. Usually, the first time this method is called, the bind method should be called, the other time
	 * nothing should be done.
	 */
	public void bindIfNeeded();

}
