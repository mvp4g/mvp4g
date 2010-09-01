package com.mvp4g.client.event;

/**
 * Default implementation of <code>EventHandlerInterface</code>.<br/>
 * <br/>
 * This implementation has an event bus attribute.<br/>
 * You should extend this class to create a presenter.<br/>
 * <br/>
 * 
 * @param <E>
 *            Type of the event bus used by the event handler.
 * 
 * 
 * @author Dan Persa
 */
public class BaseEventHandler<E extends EventBus> implements EventHandlerInterface<E> {

	private boolean binded = false;
	private boolean activated = true;

	protected E eventBus;

	/*
	 * (non-Javadoc)
	 * @see com.mvp4g.client.event.EventHandlerInterface#setEventBus(com.mvp4g.client.event.EventBus)
	 */
	public void setEventBus( E eventBus ) {
		this.eventBus = eventBus;
	}

	/*
	 * (non-Javadoc)
	 * @see com.mvp4g.client.event.EventHandlerInterface#getEventBus()
	 */
	public E getEventBus() {
		return eventBus;
	}

	/*
	 * (non-Javadoc)
	 * @see com.mvp4g.client.event.EventHandlerInterface#bind()
	 */
	public void bind() {
		/*
		 * Default implementation does nothing: extensions are responsible for (optionally) defining
		 * binding specifics.
		 */
	}

	/*
	 * (non-Javadoc)
	 * @see com.mvp4g.client.event.EventHandlerInterface#isActivated()
	 */
	public boolean isActivated() {
		if ( activated && !binded ) {
			bind();
			binded = true;
		}
		return activated;
	}

	/*
	 * (non-Javadoc)
	 * @see com.mvp4g.client.event.EventHandlerInterface#setActivated(boolean)
	 */
	public void setActivated( boolean activated ) {
		this.activated = activated;
	}
}
