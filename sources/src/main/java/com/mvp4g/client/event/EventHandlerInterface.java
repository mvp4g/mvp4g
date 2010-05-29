package com.mvp4g.client.event;


/**
 * 
 * @since 27.04.2010
 * @author Dan Persa
 */
public interface EventHandlerInterface<E extends EventBus> {

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
	 * Performs required bindings between this Presenter and its View.</p>
	 * 
	 * This is automatically invoked when the View is wired into the Presenter.
	 * 
	 */
	public void bind();

	/**
	 * Indicates if the presenter is activated or not. If the presenter is activated and if the bind
	 * method hasn't been called yet, then the bind method should be called.
	 * 
	 * @return true if the presenter is activated, false otherwise
	 */
	public boolean isActivated();

	/**
	 * Set if the presenter is activated or not. By default, a presenter should be activated.
	 * 
	 * @param activated
	 *            new activation parameter
	 */
	public void setActivated( boolean activated );

}
