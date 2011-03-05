package com.mvp4g.processor.bean;

import javax.lang.model.element.TypeElement;

public class ModuleInfo {

	private TypeElement currentEventBus;

	private TypeElement parentEventBus;

	/**
	 * @return the currentEventBus
	 */
	public TypeElement getCurrentEventBus() {
		return currentEventBus;
	}

	/**
	 * @param currentEventBus
	 *            the currentEventBus to set
	 */
	public void setCurrentEventBus( TypeElement currentEventBus ) {
		this.currentEventBus = currentEventBus;
	}

	/**
	 * @return the parentEventBus
	 */
	public TypeElement getParentEventBus() {
		return parentEventBus;
	}

	/**
	 * @param parentEventBus
	 *            the parentEventBus to set
	 */
	public void setParentEventBus( TypeElement parentEventBus ) {
		this.parentEventBus = parentEventBus;
	}

}
