package com.mvp4g.example.client.presenter;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Widget;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.BasePresenter;
import com.mvp4g.example.client.MailEventBus;
import com.mvp4g.example.client.bean.Contact;
import com.mvp4g.example.client.view.ShortcutsView;

@Presenter(view=ShortcutsView.class)
public class ShortCutsPresenter extends
		BasePresenter<ShortCutsPresenter.IShortCutsView, MailEventBus> {

	public enum FOLDER_TYPE{ INBOX, DRAFS, TEMPLATES, SENT, TRASH};
	
	public interface IShortCutsView {
		public Anchor addContact(String name);

		public void addTask(String task);

		public void addFolder(FOLDER_TYPE folder);

		public Widget getViewWidget();
		
		public void showContactPopup(String name, String email, int left, int top);
	}

	private Contact[] contacts = new Contact[] {
			new Contact("Benoit Mandelbrot", "benoit@example.com"),
			new Contact("Albert Einstein", "albert@example.com"),
			new Contact("Rene Descartes", "rene@example.com"),
			new Contact("Bob Saget", "bob@example.com"),
			new Contact("Ludwig von Beethoven", "ludwig@example.com"),
			new Contact("Richard Feynman", "richard@example.com"),
			new Contact("Alan Turing", "alan@example.com"),
			new Contact("John von Neumann", "john@example.com") };

	private String[] tasks = { "Get groceries", "Walk the dog",
			"Start Web 2.0 company", "Write cool app in GWT", "Get funding",
			"Take a vacation" };

	private FOLDER_TYPE[] folders = new FOLDER_TYPE[]{ FOLDER_TYPE.INBOX, FOLDER_TYPE.DRAFS, FOLDER_TYPE.TEMPLATES, FOLDER_TYPE.SENT, FOLDER_TYPE.TRASH};
	
	
	
	public void onStart() {
		int i = 0;
		
		for (i = 0; i < contacts.length; i++) {
			final Contact contact = contacts[i];
			final Anchor link = view.addContact(contact.getName());
			link.addClickHandler(new ClickHandler() {
				
				public void onClick(ClickEvent event) {
					int left = link.getAbsoluteLeft() + 14;
			        int top = link.getAbsoluteTop() + 14;
			        view.showContactPopup(contact.getName(), contact.getEmail(), left, top);
				}
			});
		}

		for (i = 0; i < tasks.length; i++) {
			view.addTask(tasks[i]);
		}
		
		for (i = 0; i < folders.length; i++) {
			view.addFolder(folders[i]);
		}

		eventBus.setMiddleWest(view.getViewWidget());
	}

}
