package com.mvp4g.client;

public abstract class Mvp4gEventPasser<T> {

	protected T eventObject = null;

	public Mvp4gEventPasser(T eventObject) {
		this.eventObject = eventObject;
	}

	/**
	 * @param eventObject
	 *            the eventObject to set
	 */
	public void setEventObject(T eventObject) {
		this.eventObject = eventObject;
	}

	public abstract void pass(Mvp4gModule module);

}
