package com.mvp4g.example.client.presenter;

import com.google.gwt.user.client.ui.IsWidget;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.BasePresenter;
import com.mvp4g.example.client.MailEventBus;
import com.mvp4g.example.client.bean.Contact;
import com.mvp4g.example.client.presenter.interfaces.IShortCutsView;
import com.mvp4g.example.client.presenter.interfaces.IShortCutsView.IShortCutsPresenter;
import com.mvp4g.example.client.view.ShortcutsView;

@Presenter( view = ShortcutsView.class )
public class ShortCutsPresenter extends BasePresenter<IShortCutsView, MailEventBus> implements IShortCutsPresenter {

	public enum FOLDER_TYPE {
		INBOX, DRAFS, TEMPLATES, SENT, TRASH
	};

	private Contact[] contacts = new Contact[] { new Contact( "Benoit Mandelbrot", "benoit@example.com" ),
			new Contact( "Albert Einstein", "albert@example.com" ), new Contact( "Rene Descartes", "rene@example.com" ),
			new Contact( "Bob Saget", "bob@example.com" ), new Contact( "Ludwig von Beethoven", "ludwig@example.com" ),
			new Contact( "Richard Feynman", "richard@example.com" ), new Contact( "Alan Turing", "alan@example.com" ),
			new Contact( "John von Neumann", "john@example.com" ) };

	private String[] tasks = { "Get groceries", "Walk the dog", "Start Web 2.0 company", "Write cool app in GWT", "Get funding", "Take a vacation" };

	private FOLDER_TYPE[] folders = new FOLDER_TYPE[] { FOLDER_TYPE.INBOX, FOLDER_TYPE.DRAFS, FOLDER_TYPE.TEMPLATES, FOLDER_TYPE.SENT,
			FOLDER_TYPE.TRASH };

	public void onStart() {
		int i = 0;

		for ( i = 0; i < contacts.length; i++ ) {
			view.addContact( contacts[i].getName(), i );
		}

		for ( i = 0; i < tasks.length; i++ ) {
			view.addTask( tasks[i] );
		}

		for ( i = 0; i < folders.length; i++ ) {
			view.addFolder( folders[i] );
		}

	}

	@Override
	public void onContactClick( int index, IsWidget contactWidget ) {
		Contact contact = contacts[index];
		view.showContactPopup( contact.getName(), contact.getEmail(), contactWidget );
	}

}
