# MVP4G
GWT is a very powerful framework that allows you to build efficient applications, especially if you follow the best practices described by Ray Ryan at Google IO 2009:

- Event Bus
- Dependency Injection
- Model View Presenter
- Place Service

(see [https://www.youtube.com/watch?v=PDuhR18-EdM for the video](https://www.youtube.com/watch?v=PDuhR18-EdM) or [http://extgwt-mvp4g-gae.blogspot.com/2009/10/gwt-app-architecture-best-practices.html](http://extgwt-mvp4g-gae.blogspot.com/2009/10/gwt-app-architecture-best-practices.html) for the text, thanks to Araik Minosian.)
However, following these best practices is not always easy and you can end up with a project with a lot of boilerplate code that is hard to manage.

That's why Mvp4g offers a solution to following these best practices using simple mechanisms that only need a few lines of code and a few annotations.

This is all you need to create an event bus with four events:
```
@Events(startPresenter = CompanyListPresenter.class, module = CompanyModule.class) 
public interface CompanyEventBus extends EventBus {          
        @Event( handlers = CompanyEditPresenter.class )        
        public void goToEdit( CompanyBean company );          
        
        @Event( handlers = CompanyDisplayPresenter.class )         
        public void goToDisplay( CompanyBean company );          
        
        @Event( handlers = { CompanyListPresenter.class, CompanyDisplayPresenter.class } )         
        public void companyCreated( CompanyBean newBean );          
        
        @Event( handlers = CompanyListPresenter.class )         
        public void companyDeleted( CompanyBean newBean ); 
}
```
Eventbus:
- create an event bus using a few annotations and one centralized interface where you can easily manage your events
- control your event flow thanks to event filtering, event logs, event broadcast, passive event
- have the same control of user's navigation as the GWT Activities/Place architecture thanks to Navigation Event

MVP:
- create a presenter and inject a view with one annotation
- inject anything you want to your presenters/views thanks to GIN
- support for multiple instances of the same presenter
- easily implement the Reverse MVP (or View Delegate) pattern thanks to Reverse View feature
- easily control your presenter thanks to onBeforeEvent, onLoad and onUnload methods (thanks to the Cycle Presenter feature)

History Management/Place Service:
- convert any event to history token thanks to simple history converters
- support for crawlable urls
- easily customize your place service
- support for hyperlink token

Not only does Mvp4g help you follow the best practices, it also provides mechanisms to build fast applications:
- support for GWT code splitting feature: easily divide your applications into smaller modules thanks to Multi-Modules feature or (**NEW**) split one or a few presenters thanks to Splitter.
- support for lazy loading: build your presenters/views only when you need them. Useless presenters/views are also automatically removed.
- (**NEW**) easily develop your application for multiple application. Reuse most of your code for Android or Iphone/Ipad devices and just switch your GIN configuration.

To understand how the framework works, you can look at the documentation, the [tutorials](https://github.com/FrankHossfeld/mvp4g/wiki/1.-Tutorials-and-Examples) or the [examples](https://github.com/FrankHossfeld/mvp4g-examples).

Mvp4g has been successfully used on several commercial projects, [take a look at a few of them](https://github.com/FrankHossfeld/mvp4g/wiki/1.-Tutorials-and-Examples). You can also read and post feedback on the official [GWT marketplace](http://www.gwtmarketplace.com/#mvp4g) or [Mvp4g forum](https://groups.google.com/forum/#!forum/mvp4g).

Any comments or ideas to improve the framework are more than welcome. If you want to help us to improve and contribute to this project, feel free to do so.

To ensure quality, library code is covered by JUnit tests.

# Note 
Mvp4g uses heavily generators. Also it has a dependency to GIN, which is at least end of life. That are some of the reasons why mvp4g will never work with j2cl/GWT 3!

Of course mvp4g will be maintained in the future. Bugs will be fixed, etc. But, you will not see any no features, etc. 

If you are planning to update your project and make it j2cl ready, you have two opportunities:
1. switch to [mvp4g2](https://github.com/mvp4g/mvp4g2)
2. switch to [Nalu](https://github.com/NaluKit/nalu) 

In case you are uncertain, which one of the frameworks to choose, ask inside the [Nalu Gitter room](https://gitter.im/Nalukit42/Lobby).
