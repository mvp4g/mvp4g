package com.mvp4g.example.client.mock;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyUpEvent;

public class MockGwtEvent {

	public static final ClickEvent CLICK_EVENT = new MockClickEvent();
	public static final KeyUpEvent KEY_UP_EVENT = new MockKeyUpEvent();

}

class MockClickEvent extends ClickEvent {
}

class MockKeyUpEvent extends KeyUpEvent {
}
