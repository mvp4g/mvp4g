package com.mvp4g.util.config.element;

import java.util.ArrayList;
import java.util.List;

public class EventAssociation<T> {

	private List<T> handlers = new ArrayList<T>();

	private List<T> binds = new ArrayList<T>();

	private List<T> activated = new ArrayList<T>();

	private List<T> deactivated = new ArrayList<T>();

	/**
	 * @return the handlers
	 */
	public List<T> getHandlers() {
		return handlers;
	}

	/**
	 * @return the binds
	 */
	public List<T> getBinds() {
		return binds;
	}

	/**
	 * @return the activated
	 */
	public List<T> getActivated() {
		return activated;
	}

	/**
	 * @return the deactivated
	 */
	public List<T> getDeactivated() {
		return deactivated;
	}

}
