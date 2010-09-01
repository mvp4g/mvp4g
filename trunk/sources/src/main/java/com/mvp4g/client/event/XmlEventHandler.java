package com.mvp4g.client.event;

/**
 * Event handler to use when the event bus is defined thanks to an XML file. It's an event handler where
 * event bus type has been set to automatically match the type of the event bus generated thanks to
 * an XML file.
 * 
 * @author plcoirier
 * 
 * @param <V>
 *            Type of the view injected into the presenter. Must extends <code>LazyView</code>.
 */
public class XmlEventHandler extends BaseEventHandler<EventBusWithLookup> {

}
