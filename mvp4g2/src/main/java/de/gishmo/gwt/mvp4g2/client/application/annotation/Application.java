package de.gishmo.gwt.mvp4g2.client.application.annotation;

import de.gishmo.gwt.mvp4g2.client.application.IsApplicationLoader;
import de.gishmo.gwt.mvp4g2.client.application.internal.NoApplicationLoader;
import de.gishmo.gwt.mvp4g2.client.eventbus.IsEventBus;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * <p>This annotation is used to annotate an interface in mvp4g and mark it as mvp4g application.
 * <br><br>
 * The annotation has the following attributes:
 * <ul>
 * <li>eventBus: defines the eventbus of this application</li>
 * <li>loader: a loader that will be executed in case the application loads. If no loader
 * is defined, the NoApplicationLoader.class will be used</li>
 * <p>
 * <p>
 * <p>
 * <p>
 * <p>
 * <p>
 * <p>
 * <p>
 * <p>
 * <p>
 * <p>
 * <p>
 * <p>
 * <li>shell: the shell of the application. The shell will be added to the viewport.</li>
 * <p>
 * <p>
 * <p>
 * <p>
 * <p>
 * <p>
 * <p>
 * <p>
 * <p>
 * <p>
 * <p>
 * <p>
 * <li>applicationType: defines the application type. Possible type are: MAIN_MODULE or SUB_MODULE</li>
 * <li>framework: defines the base technologic of this application. Currently the only supported technology is GWT.</li>
 * <p>
 * <p>
 * <p>
 * <p>
 * <p>
 * <p>
 * </ul>
 * <p>
 * <p>
 * <br><br><br><br><br><br><br><br><br><br>
 * </p>
 * <p>
 * <p>
 * <p>
 * The annotation has the following attributes:
 * <ul>
 * <li>handlers: classes of the handlers of this event. You can have zero to several handlers for an
 * event.</li>
 * <p>
 * <p>
 * <li>modules: defines the modules of the application. You have at least one module.</li>
 * <p>
 * <p>
 * <li>handlerNames: instead of using their classes, you can define handlers thanks to their name
 * (in case you have given names to your handlers). Not recommended because this method tends to
 * create typo errors.</li>
 * <li> bind: classes that need to be binded when this event occurs. You can have zero to several classes
 * for an event. </li>
 * <li> bindNames: instead of using their classes, you can define binds thanks to their name. Not recommended.
 * <li>modulesToLoad: child modules that should be loaded if necessary and to which the event should
 * be forwarded. Child modules to which the event is forwarded must be one of the child modules of
 * the <code>Mvp4gInternalEventBus</code> interface's module (ie one of the modules defined inside
 * <code>ChildModules</code> annotation). If object(s) are associated to the event, they will also
 * be forwarded. An event can be forwarded to zero to several child modules.</li>
 * <li>forwardToParent: if true, event will be forwarded to the parent module. In this case, the
 * module must have a parent.</li>
 * <li>calledMethod: name of the method that handlers should define and that will be called when the
 * event is fired. By default it's equal to "on" + event's method name.</li>
 * <li>historyConverter: class of the history converter that should be used to store the event in
 * browse history. If no history converter is specified, event won't be stored in browse history.
 * You can define only one history converter for each event.
 * <li>historyConverterName: instead of using its class, you can define the history converter thanks
 * to his name (in case you have given names to your history converter). Not recommended because
 * this method tends to create typo errors.</li>
 * <li>historyName: name of the event that should be stored in the history token. By default, this
 * name is equal to the name of the event's method.
 * <li>activate: classes of handlers that should be activated with this event. You can activate zero
 * to several handlers. Handlers to activate don't have to handle the event.</li>
 * <li>activateNames: instead of using their classes, you can activate handlers thanks to their name
 * (in case you have given names to your handlers). Not recommended because this method tends to
 * create typo errors.</li>
 * <li>deactivate: classes of handlers that should be deactivated with this event. You can activate
 * zero to several handlers. Handlers to deactivate must not handle the event.</li>
 * <li>deactivateNames: instead of using their classes, you can activate handlers thanks to their
 * name (in case you have given names to your handlers). Not recommended because this method tends
 * to create typo errors.</li>
 * <li>navigationEvent: indicates that when this event is fired, a navigation control is done to
 * verify the event can be fired. Usually a navigation event is an event that will change the
 * displayed screen.</li>
 * <li>passive: when an event is fired, it will build any handlers not built yet and/or load any
 * child modules not loaded yet expect if the event is passive.</li>
 * </ul>
 *
 * @author Frank Hossfeld
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Application {

  Class<? extends IsEventBus> eventBus();

  Class<? extends IsApplicationLoader> loader() default NoApplicationLoader.class;


  //  String applicationId();
  //
  //  ApplicationType applicationType() default ApplicationType.MAIN_MODULE;
  //
  //
  //  Framework framework() default Framework.GWT;

  //  Class<? extends Mvp4gModule>[] modules();
  //
  //  //default name that developers are unlikely to enter to know when method name should be used
  //  public static final String DEFAULT_NAME = "#%!|&";
  //
  //  Class<? extends EventHandlerInterface<? extends Mvp4gInternalEventBus>>[] handlers() default {};
  //
  //  String[] handlerNames() default {};
  //
  //  Class<? extends EventHandlerInterface<? extends Mvp4gInternalEventBus>>[] bind() default {};
  //
  //  String[] bindNames() default {};
  //
  //  Class<? extends Mvp4gModule>[] forwardToModules() default {};
  //
  //  boolean forwardToParent() default false;
  //
  //  String calledMethod() default "";
  //
  //  String historyConverterName() default "";
  //
  //  Class<? extends HistoryConverter<?>> historyConverter() default NoHistoryConverter.class;
  //
  //  Class<? extends EventHandlerInterface<? extends Mvp4gInternalEventBus>>[] activate() default {};
  //
  //  String[] activateNames() default {};
  //
  //  Class<? extends EventHandlerInterface<? extends Mvp4gInternalEventBus>>[] deactivate() default {};
  //
  //  String[] deactivateNames() default {};
  //
  //  String name() default DEFAULT_NAME;
  //
  //  boolean navigationEvent() default false;
  //
  //  boolean passive() default false;

  //  Class<?>[] broadcastTo();

  //  Class<? extends EventHandlerInterface<? extends Mvp4gInternalEventBus>>[] generate() default {};
  //
  //  String[] generateNames() default {};
  //
  //  class NoHistoryConverter
  //    implements HistoryConverter<Mvp4gInternalEventBus> {
  //
  //    private NoHistoryConverter() {
  //      //to prevent this class to be used
  //    }
  //
  //    public void convertFromToken(String historyName,
  //                                 String param,
  //                                 Mvp4gInternalEventBus eventBus) {
  //    }
  //
  //    public boolean isCrawlable() {
  //      return false;
  //    }
  //
  //  }
  //
  //  class NoBroadcast {
  //  }

  //  /* Suported frameworks */
  //  enum Framework {
  //    GWT
  //  }
  //
  //  enum ApplicationType {
  //    MAIN_MODULE,
  //    SUB_MODULE
  //  }

}
