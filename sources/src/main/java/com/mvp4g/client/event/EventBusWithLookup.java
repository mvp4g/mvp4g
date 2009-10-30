package com.mvp4g.client.event;

import com.mvp4g.client.Mvp4gException;

public interface EventBusWithLookup extends EventBus {

	/**
	 * Execute the command associated with the event type in order to trigger the presenters that
	 * can handle the event. Forward to the handler the object associated with the event.<br/>
	 * <br/>
	 * Store the event in GWT History stack if needed and possible. If an event can't be stored in
	 * GWT History stack (ie when no History Converter has been associated to the event), the method
	 * will not try to store in GWT History stack even if storeInHistory is true.
	 * 
	 * @param eventType
	 *            type of the event to dispatch
	 * @param form
	 *            form to forward to the handlers
	 * @param storeInHistory
	 *            indicates if the event must be stored in the GWT History stack. This parameter
	 *            matters only if the event can be stored in the GWT History stack (ie if a History
	 *            converter is associated with the event).
	 * @throws Mvp4gException
	 *             exception thrown in case an error occurs while the event is dispatched.<br/>
	 *             The most common cases that this error could be thrown is in case:
	 *             <ul>
	 *             <li>the type of the event can't be handled by the event bus because no command is
	 *             associated to it.</li>
	 *             <li>the class type of the object to be used by the handler methods is different
	 *             from the class type of the handlers method.</li>
	 *             </ul>
	 *             This exception shouldn't be caught. If this exception is raised, it means that
	 *             there is an error in the application that needs to be fixed (most of the time, an
	 *             error in the configuration file).
	 */
	public void dispatch( String eventType, Object form );

	/**
	 * Execute the command associated with the event type in order to trigger the presenters that
	 * can handle the event. In this case no object is forwarded to the handler(s).<br/>
	 * <br/>
	 * 
	 * The event is automatically stored in GWT History stack if possible (ie if an History
	 * Converter is associated with the event)
	 * 
	 * Calling dispatch(eventType) is equivalent to calling dispatch(eventType, null, true).
	 * 
	 * @param eventType
	 *            type of the event to dispatch
	 * @throws Mvp4gException
	 *             exception thrown in case an error occurs while the event is dispatched.<br/>
	 *             The most common cases that this error could be thrown is in case:
	 *             <ul>
	 *             <li>the type of the event can't be handled by the event bus because no command is
	 *             associated to it.</li>
	 *             <li>the class type of the object to be used by the handler methods is different
	 *             from the class type of the handlers method.</li>
	 *             </ul>
	 *             This exception shouldn't be caught. If this exception is raised, it means that
	 *             there is an error in the application that needs to be fixed (most of the time, an
	 *             error in the configuration file).
	 */
	public void dispatch( String eventType );

	/**
	 * Execute the command associated with the event type in order to trigger the presenters that
	 * can handle the event. Forward to the handler the object associated with the event. <br/>
	 * In this function, the type is given as a enumeration. The toString method of this enumeration
	 * must return the type of the event defined the configuration file.<br/>
	 * <br/>
	 * The event is automatically stored in GWT History stack if possible (ie if an History
	 * Converter is associated with the event)<br/>
	 * <br/>
	 * Calling dispatch(enumEventType, form) is equivalent to calling
	 * dispatch(enumEventType.toString(), form, true).
	 * 
	 * 
	 * 
	 * @param enumEventType
	 *            type of the event to dispatch
	 * @param form
	 *            form to forward to the handlers
	 * @throws Mvp4gException
	 *             exception thrown in case an error occurs while the event is dispatched.<br/>
	 *             The most common cases that this error could be thrown is in case:
	 *             <ul>
	 *             <li>the type of the event can't be handled by the event bus because no command is
	 *             associated to it.</li>
	 *             <li>the class type of the object to be used by the handler methods is different
	 *             from the class type of the handlers method.</li>
	 *             </ul>
	 *             This exception shouldn't be caught. If this exception is raised, it means that
	 *             there is an error in the application that needs to be fixed (most of the time, an
	 *             error in the configuration file).
	 */
	public <E extends Enum<E>> void dispatch( Enum<E> enumEventType, Object form );

	/**
	 * Execute the command associated with the event type in order to trigger the presenters that
	 * can handle the event. In this case no object is forwarded to the handler(s).<br/>
	 * <br/>
	 * In this function, the type is given as a enumeration. The toString method of this enumeration
	 * must return the type of the event defined the configuration file.<br/>
	 * <br/>
	 * The event is automatically stored in GWT History stack if possible (ie if an History
	 * Converter is associated with the event)<br/>
	 * <br/>
	 * Calling dispatch(enumEventType) is equivalent to calling dispatch(enumEventType.toString(),
	 * null, true).
	 * 
	 * @param enumEventType
	 *            type of the event to dispatch
	 * @throws Mvp4gException
	 *             exception thrown in case an error occurs while the event is dispatched.<br/>
	 *             The most common cases that this error could be thrown is in case:
	 *             <ul>
	 *             <li>the type of the event can't be handled by the event bus because no command is
	 *             associated to it.</li>
	 *             <li>the class type of the object to be used by the handler methods is different
	 *             from the class type of the handlers method.</li>
	 *             </ul>
	 *             This exception shouldn't be caught. If this exception is raised, it means that
	 *             there is an error in the application that needs to be fixed (most of the time, an
	 *             error in the configuration file).
	 */
	public <E extends Enum<E>> void dispatch( Enum<E> enumEventType );

}
