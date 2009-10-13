/**
 * 
 */
package com.mvp4g.util.config;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.configuration.XMLConfiguration;

import com.mvp4g.util.config.element.EventElement;
import com.mvp4g.util.config.element.HistoryConverterElement;
import com.mvp4g.util.config.element.HistoryElement;
import com.mvp4g.util.config.element.Mvp4gElement;
import com.mvp4g.util.config.element.Mvp4gWithServicesElement;
import com.mvp4g.util.config.element.PresenterElement;
import com.mvp4g.util.config.element.ServiceElement;
import com.mvp4g.util.config.element.StartElement;
import com.mvp4g.util.config.element.ViewElement;
import com.mvp4g.util.config.loader.EventsLoader;
import com.mvp4g.util.config.loader.HistoryConverterLoader;
import com.mvp4g.util.config.loader.HistoryLoader;
import com.mvp4g.util.config.loader.PresentersLoader;
import com.mvp4g.util.config.loader.ServicesLoader;
import com.mvp4g.util.config.loader.StartLoader;
import com.mvp4g.util.config.loader.ViewsLoader;
import com.mvp4g.util.exception.InvalidMvp4gConfigurationException;
import com.mvp4g.util.exception.NonUniqueIdentifierException;
import com.mvp4g.util.exception.UnknownConfigurationElementException;

/**
 * An in-memory representation of all elements in the mvp4g-config.xml file.
 * 
 * @author javier
 * 
 */
public class Mvp4gConfiguration {

	private Set<PresenterElement> presenters = new HashSet<PresenterElement>();
	private Set<ViewElement> views = new HashSet<ViewElement>();
	private Set<EventElement> events = new HashSet<EventElement>();
	private Set<ServiceElement> services = new HashSet<ServiceElement>();
	private Set<HistoryConverterElement> historyConverters = new HashSet<HistoryConverterElement>();
	private StartElement start = new StartElement();
	private HistoryElement history = new HistoryElement();

	/**
	 * Loads all Mvp4g elements from an in-memory representation of the XML configuration.</p>
	 * 
	 * Configuration loading comprises two phases:
	 * 
	 * <ol>
	 * <li/>Phase 1, <i>Parsing all the XML elements</i>: during this phase, all the Mvp4gElement
	 * instances are constructed from their corresponding xml tags. Simple validation of element
	 * attributes is performed.
	 * <li/>Phase 2, <i>Validation of cross-element references</i>: in this phase element
	 * identifiers are checked for global uniqueness; event handler references and view references
	 * are checked for existence.
	 * </ol>
	 * 
	 * @param xmlConfig
	 *            raw, in-memory representation of the XML configuration.
	 * 
	 * @throws InvalidMvp4gConfigurationException
	 *             if any tags cannot be loaded.
	 * 
	 * @throws NonUniqueIdentifierExcpetion
	 *             if two or more elements have the same textual identifier.
	 * 
	 * @throws UnknownConfigurationElementException
	 *             <ol>
	 *             <li/>if an event handler cannot be found among the configured elements.
	 *             <li/>if a view reference cannot be found among the configured elements.
	 *             </ol>
	 */
	public void load( XMLConfiguration xmlConfig ) {

		// Phase 1: load all elements, performing attribute validation
		loadViews( xmlConfig );
		loadServices( xmlConfig );
		loadHistoryConverters( xmlConfig );
		loadPresenters( xmlConfig );
		loadEvents( xmlConfig );
		loadStart( xmlConfig );
		loadHistory( xmlConfig );

		// Phase 2: perform cross-element validations
		checkUniquenessOfAllElements();
		validateEventHandlers();
		validateViews();
		validateServices();
		validateHistoryConverters();
		validateEvents();
	}

	/**
	 * Pre-loads all Presenters in the configuration file.
	 * 
	 * @param xmlConfig
	 *            raw representation of mvp4g-config.xml file.
	 * 
	 * @throws InvalidMvp4gConfigurationException
	 *             if presenter tags cannot be loaded.
	 * 
	 */
	private void loadPresenters( XMLConfiguration xmlConfig ) throws InvalidMvp4gConfigurationException {
		PresentersLoader presentersConfig = new PresentersLoader( xmlConfig );
		presenters = presentersConfig.loadElements();
	}

	/**
	 * Pre-loads all Services in the configuration file.
	 * 
	 * @param xmlConfig
	 *            raw representation of mvp4g-config.xml file.
	 * 
	 * @throws InvalidMvp4gConfigurationException
	 *             if service tags cannot be loaded.
	 * 
	 */
	private void loadServices( XMLConfiguration xmlConfig ) throws InvalidMvp4gConfigurationException {
		ServicesLoader servicesConfig = new ServicesLoader( xmlConfig );
		services = servicesConfig.loadElements();
	}

	/**
	 * Pre-loads all Views in the configuration file.
	 * 
	 * @param xmlConfig
	 *            raw representation of mvp4g-config.xml file.
	 * 
	 * @throws InvalidMvp4gConfigurationException
	 *             if view tags cannot be loaded.
	 */
	private void loadViews( XMLConfiguration xmlConfig ) throws InvalidMvp4gConfigurationException {
		ViewsLoader viewsConfig = new ViewsLoader( xmlConfig );
		views = viewsConfig.loadElements();
	}

	/**
	 * Pre-loads all Events in the configuration file.
	 * 
	 * @param xmlConfig
	 *            raw representation of mvp4g-config.xml file.
	 * 
	 * @throws InvalidMvp4gConfigurationException
	 *             if event tags cannot be loaded.
	 */
	private void loadEvents( XMLConfiguration xmlConfig ) throws InvalidMvp4gConfigurationException {
		EventsLoader eventsConfig = new EventsLoader( xmlConfig );
		events = eventsConfig.loadElements();
	}

	/**
	 * Pre-loads all History Converter in the configuration file.
	 * 
	 * @param xmlConfig
	 *            raw representation of mvp4g-config.xml file.
	 * 
	 * @throws InvalidMvp4gConfigurationException
	 *             if event tags cannot be loaded.
	 */
	private void loadHistoryConverters( XMLConfiguration xmlConfig ) throws InvalidMvp4gConfigurationException {
		HistoryConverterLoader historyConverterConfig = new HistoryConverterLoader( xmlConfig );
		historyConverters = historyConverterConfig.loadElements();
	}

	/**
	 * Pre-loads the Start element in the configuration file.
	 * 
	 * @param xmlConfig
	 *            raw representation of mvp4g-config.xml file.
	 * 
	 * @throws InvalidMvp4gConfigurationException
	 *             if start tag cannot be loaded.
	 */
	private void loadStart( XMLConfiguration xmlConfig ) throws InvalidMvp4gConfigurationException {
		StartLoader startConfig = new StartLoader( xmlConfig );
		start = startConfig.loadElement();
	}

	/**
	 * Pre-loads the History element in the configuration file.
	 * 
	 * @param xmlConfig
	 *            raw representation of mvp4g-config.xml file.
	 * 
	 * @throws InvalidMvp4gConfigurationException
	 *             if start tag cannot be loaded.
	 */
	private void loadHistory( XMLConfiguration xmlConfig ) throws InvalidMvp4gConfigurationException {
		HistoryLoader historyConfig = new HistoryLoader( xmlConfig );
		history = historyConfig.loadElement();
	}

	/**
	 * Returns a set of valid Presenters loaded from the configuration file.
	 */
	public Set<PresenterElement> getPresenters() {
		return presenters;
	}

	/**
	 * Returns a set of valid Views loaded from the configuration file.
	 */
	public Set<ViewElement> getViews() {
		return views;
	}

	/**
	 * Returns a set of valid History Converters loaded from the configuration file.
	 */
	public Set<HistoryConverterElement> getHistoryConverters() {
		return historyConverters;
	}

	/**
	 * Returns a set of valid Events loaded from the configuration file.
	 */
	public Set<EventElement> getEvents() {
		return events;
	}

	/**
	 * Returns a set of valid Services loaded from the configuration file.
	 */
	public Set<ServiceElement> getServices() {
		return services;
	}

	/**
	 * Returns the Start element loaded from the configuration file.
	 */
	public StartElement getStart() {
		return start;
	}

	/**
	 * Returns the Start element loaded from the configuration file.
	 */
	public HistoryElement getHistory() {
		return history;
	}

	/**
	 * Validates that every mvp4g element has a globally unique identifier.</p>
	 * 
	 * @throws NonUniqueIdentifierExcpetion
	 *             if two or more elements have the same textual identifier.
	 */
	void checkUniquenessOfAllElements() {
		Set<String> allIds = new HashSet<String>();
		checkUniquenessOf( historyConverters, allIds );
		checkUniquenessOf( presenters, allIds );
		checkUniquenessOf( views, allIds );
		checkUniquenessOf( events, allIds );
		checkUniquenessOf( services, allIds );
	}

	private <E extends Mvp4gElement> void checkUniquenessOf( Set<E> subset, Set<String> ids ) {
		for ( E item : subset ) {
			boolean unique = ids.add( item.getUniqueIdentifier() );
			if ( !unique ) {
				throw new NonUniqueIdentifierException( item.getUniqueIdentifier() );
			}
		}
	}

	/**
	 * Checks that all event handler names correspond to a configured mvp4g element.</p>
	 * 
	 * @throws UnknownConfigurationElementException
	 *             if an event handler cannot be found among the configured elements.
	 */
	void validateEventHandlers() {
		for ( EventElement event : events ) {
			for ( String handlerName : event.getHandlers() ) {
				if ( !elementExists( handlerName, presenters ) ) {
					throw new UnknownConfigurationElementException( handlerName );
				}
			}
		}
	}

	/**
	 * Checks that all injected views correspond to a configured mvp4g element.</p>
	 * 
	 * @throws UnknownConfigurationElementException
	 *             if a view reference cannot be found among the configured elements.
	 */
	void validateViews() {
		Set<String> viewIds = new HashSet<String>();

		// Include view injected into start tag
		viewIds.add( start.getView() );

		// Include all views injected into presenter tags
		for ( PresenterElement presenter : presenters ) {
			viewIds.add( presenter.getView() );
		}

		for ( String view : viewIds ) {
			if ( !viewExists( view ) ) {
				throw new UnknownConfigurationElementException( view );
			}
		}
	}

	private boolean viewExists( String viewId ) {
		for ( ViewElement view : views ) {
			if ( view.getUniqueIdentifier().equals( viewId ) ) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Checks that all service names injected on every presenter and history converter correspond to
	 * a configured mvp4g element.</p>
	 * 
	 * @throws UnknownConfigurationElementException
	 *             if a service cannot be found among the configured elements.
	 */
	void validateServices() {

		Set<Mvp4gWithServicesElement> elements = new HashSet<Mvp4gWithServicesElement>();
		elements.addAll( presenters );
		elements.addAll( historyConverters );

		for ( Mvp4gWithServicesElement element : elements ) {
			for ( String serviceName : element.getServices() ) {
				if ( !elementExists( serviceName, services ) ) {
					throw new UnknownConfigurationElementException( serviceName );
				}
			}
		}

	}

	void validateHistoryConverters() {

		String historyConverterName = null;
		for ( EventElement event : events ) {
			if ( event.hasHistory() ) {
				historyConverterName = event.getHistory();
				if ( !elementExists( historyConverterName, historyConverters ) ) {
					throw new UnknownConfigurationElementException( historyConverterName );
				}
			}
		}

	}

	private <T extends Mvp4gElement> boolean elementExists( String elementName, Set<T> elements ) {
		for ( Mvp4gElement element : elements ) {
			if ( element.getUniqueIdentifier().equals( elementName ) ) {
				return true;
			}
		}
		return false;
	}

	void validateEvents() {
		String event = null;
		if ( start.hasEventType() ) {
			event = start.getEventType();
			if ( !elementExists( event, events ) ) {
				throw new UnknownConfigurationElementException( event );
			}
		}

		if ( history != null ) {
			event = history.getInitEvent();
			if ( !elementExists( event, events ) ) {
				throw new UnknownConfigurationElementException( event );
			}
		}

	}

}
