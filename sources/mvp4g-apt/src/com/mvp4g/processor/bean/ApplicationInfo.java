package com.mvp4g.processor.bean;

import java.util.Map;

public class ApplicationInfo {

	Map<String, ModuleInfo> modules;

	Map<String, String> eventBus;

	public ApplicationInfo( Map<String, ModuleInfo> modules, Map<String, String> eventBus ) {
		super();
		this.modules = modules;
		this.eventBus = eventBus;
	}

	/**
	 * @return the modules
	 */
	public Map<String, ModuleInfo> getModules() {
		return modules;
	}

	/**
	 * @param modules
	 *            the modules to set
	 */
	public void setModules( Map<String, ModuleInfo> modules ) {
		this.modules = modules;
	}

	/**
	 * @return the eventBus
	 */
	public Map<String, String> getEventBus() {
		return eventBus;
	}

	/**
	 * @param eventBus
	 *            the eventBus to set
	 */
	public void setEventBus( Map<String, String> eventBus ) {
		this.eventBus = eventBus;
	}

}
