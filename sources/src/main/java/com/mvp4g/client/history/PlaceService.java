package com.mvp4g.client.history;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.History;
import com.mvp4g.client.Mvp4gEventPasser;
import com.mvp4g.client.Mvp4gModule;

/**
 * Place Service defines the connection between the application and the history.<br/>
 * <br/>
 * When an event needs to be stored in the history, the place method of
 * <code>PlaceService</code> by the <code>Command</code> instance in charge of
 * an event.<br/>
 * <br/>
 * When the History token changes, the onValueChange method is called.<br/>
 * <br/>
 * The <code>PlaceService</code> convert an event to a History token the
 * following way:<br/>
 * <br/>
 * myurl#eventType?params<br/>
 * <br/>
 * where params is the string returned by the convertToToken method of the
 * converter associated with the event.<br/>
 * If params is null, then the URL will be:<br/>
 * <br/>
 * myurl#eventType
 * 
 * 
 * 
 * @author plcoirier
 * 
 */
public abstract class PlaceService implements ValueChangeHandler<String> {

	public static final String MODULE_SEPARATOR = "/";

	/**
	 * Interface to define methods needed to manage history<br/>
	 * <br/>
	 * This interface is needed in order to test the PlaceService without using
	 * GWT History class.<br/>
	 * GWT History class can't be used with JUnit test.
	 * 
	 * @author plcoirier
	 * 
	 */
	public interface HistoryProxy {

		public void addValueChangeHandler(ValueChangeHandler<String> handler);

		public void newItem(String historyToken, boolean issueEvent);

	}

	private static final String FIRST = "?";
	private static final String FIRST_RE = "\\" + FIRST;

	private HistoryProxy history = null;
	private Mvp4gModule module = null;

	@SuppressWarnings("unchecked")
	private Map<String, HistoryConverter> converters = new HashMap<String, HistoryConverter>();

	/**
	 * Build a <code>PlaceService</code> and inject <code>EventBus</code>
	 * instance.
	 * 
	 * @param eventBus
	 *            event bus to inject
	 */
	public PlaceService() {
		this(new HistoryProxy() {

			public void addValueChangeHandler(ValueChangeHandler<String> handler) {
				History.addValueChangeHandler(handler);
			}

			public void newItem(String historyToken, boolean issueEvent) {
				History.newItem(historyToken, issueEvent);
			}

		});
	}

	/**
	 * Build a <code>PlaceService</code> and inject <code>EventBus</code> and
	 * <code>HistoryProxy</code> instances.<br/>
	 * <br/>
	 * This constructor is handy when you want to test <code>PlaceService</code>
	 * without using the GWT History class.<br/>
	 * It shouldn't be called otherwise.
	 * 
	 * 
	 * @param eventBus
	 *            event bus to inject
	 * @param history
	 *            history proxy to inject
	 */
	protected PlaceService(HistoryProxy history) {
		this.history = history;
		history.addValueChangeHandler(this);
	}

	/**
	 * Called when the History token has changed.<br/>
	 * <br/>
	 * Decode the history token and call the convertFromToken method of the
	 * history converters associated with this action stored in the token.<br/>
	 * <br/>
	 * If token is equal to empty string, ask the event bus to dispatch an
	 * initEvent.
	 * 
	 * @param event
	 *            event containing the new history token
	 * 
	 */
	public void onValueChange(ValueChangeEvent<String> event) {
		String token = event.getValue();
		if ((token != null) && (token.length() > 0)) {
			String[] tokenTab = token.split(FIRST_RE);
			final String eventType = tokenTab[0];
			final String param = (tokenTab.length > 1) ? tokenTab[1] : null;
			if (eventType.contains("/")) {
				Mvp4gEventPasser<Boolean> passer = new Mvp4gEventPasser<Boolean>(
						true) {

					@Override
					public void pass(Mvp4gModule module) {
						if (eventObject) {
							dispatchEvent(eventType, param, module);
						} else {
							sendNotFoundEvent();
						}
					}
				};
				module.dispatchHistoryEvent(eventType, passer);
			} else {
				dispatchEvent(eventType, param, module);
			}
		} else {
			sendInitEvent();
		}
	}

	@SuppressWarnings("unchecked")
	private void dispatchEvent(String eventType, String param,
			Mvp4gModule module) {
		HistoryConverter converter = converters.get(eventType);
		if (converter == null) {
			sendNotFoundEvent();
		} else {
			String[] tab = eventType.split("/");
			String finalEventType = tab[tab.length - 1];
			converter.convertFromToken(finalEventType, param, module
					.getEventBus());
		}
	}

	/**
	 * Convert an event and its associated object to a token.<br/>
	 * <br/>
	 * The object is converted to a string thanks to the history converter
	 * associated with the event.<br/>
	 * 
	 * @param eventType
	 *            type of the event to store
	 * @param form
	 *            object associated with the event
	 */
	@SuppressWarnings("unchecked")
	public <T> void place(String eventType, T form) {
		HistoryConverter hc = converters.get(eventType);
		if (hc instanceof ClearHistory) {
			history.newItem("", false);
		} else {
			String param = hc.convertToToken(eventType, form);
			String token = ((param == null) || (param.length() == 0)) ? eventType
					: (eventType + FIRST + param);
			history.newItem(token, false);
		}
	}

	/**
	 * Add a converter for an event.<br/>
	 * 
	 * @param eventType
	 * @param converter
	 */
	@SuppressWarnings("unchecked")
	public void addConverter(String eventType, HistoryConverter converter) {
		converters.put(eventType, converter);
	}

	/**
	 * @param module
	 *            the module to set
	 */
	public void setModule(Mvp4gModule module) {
		this.module = module;
	}

	/**
	 * Call when token retrieved is null or equals to empty string
	 */
	abstract protected void sendInitEvent();

	/**
	 * Call when token retrieved doesn't correspond to an event
	 */
	abstract protected void sendNotFoundEvent();

}
