package com.mvp4g.example.client;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.mvp4g.example.client.MainPresenter.MainViewInterface;

public class MainView extends Composite implements MainViewInterface {

	Label handlerManagerResult = new Label();
	Label mvp4gResult = new Label();
	Button start = new Button("Start");
	ListBox numbers = new ListBox();
	Label message = new Label();
	
	public MainView(){
		FlexTable table = new FlexTable();
		initWidget( table );
		
		table.setWidget( 0, 0, numbers );
		table.setWidget( 0, 2, start );
		table.setWidget( 1, 1, handlerManagerResult );
		table.setWidget( 2, 1, mvp4gResult );
		
		table.setText( 0, 1, " x10 events " );
		
		table.setText( 1, 0, "Handler Manager result: " );
		table.setText( 1, 2, " ms" );
		
		table.setText( 2, 0, "Mvp4g result: " );
		table.setText( 2, 2, " ms" );
		
		table.getFlexCellFormatter().setColSpan( 3, 0, 3 );
		table.setWidget( 3, 0, message );
	}

	public Label getHandlerManagerResult() {
		return handlerManagerResult;
	}

	public Label getMvp4gResult() {
		return mvp4gResult;
	}

	public HasClickHandlers getStartButton() {
		return start;
	}

	public ListBox getNumbers() {
		return numbers;
	}

	public Label getMessageBar() {
		return message;
	}

}
