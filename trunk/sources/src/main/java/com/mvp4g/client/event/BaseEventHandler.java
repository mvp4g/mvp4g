package com.mvp4g.client.event;

/**
 * 
 * @since 27.04.2010
 * @author Dan Persa
 */
public class BaseEventHandler<E extends EventBus> implements EventHandlerInterface<E> {

	private boolean binded = false;
	private boolean activated = true;

	protected E eventBus;

	public void setEventBus( E eventBus ) {
		this.eventBus = eventBus;
	}

	public E getEventBus() {
		return eventBus;
	}

	public void bind() {
		/*
		 * Default implementation does nothing: extensions are responsible for (optionally) defining
		 * binding specifics.
		 */
	}

	public boolean isActivated() {
		if ( activated && !binded ) {
			bind();
			binded = true;
		}
		return activated;
	}

	public void setActivated( boolean activated ) {
		this.activated = activated;
	}
}
