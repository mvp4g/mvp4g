package com.mvp4g.example.client.presenters;

import com.mvp4g.example.client.events.Event1;
import com.mvp4g.example.client.events.Event10;
import com.mvp4g.example.client.events.Event2;
import com.mvp4g.example.client.events.Event3;
import com.mvp4g.example.client.events.Event4;
import com.mvp4g.example.client.events.Event5;
import com.mvp4g.example.client.events.Event6;
import com.mvp4g.example.client.events.Event7;
import com.mvp4g.example.client.events.Event8;
import com.mvp4g.example.client.events.Event9;
import com.mvp4g.example.client.events.Event1.Event1Handler;
import com.mvp4g.example.client.events.Event10.Event10Handler;
import com.mvp4g.example.client.events.Event2.Event2Handler;
import com.mvp4g.example.client.events.Event3.Event3Handler;
import com.mvp4g.example.client.events.Event4.Event4Handler;
import com.mvp4g.example.client.events.Event5.Event5Handler;
import com.mvp4g.example.client.events.Event6.Event6Handler;
import com.mvp4g.example.client.events.Event7.Event7Handler;
import com.mvp4g.example.client.events.Event8.Event8Handler;
import com.mvp4g.example.client.events.Event9.Event9Handler;

public class TestHandler implements Event1Handler, Event2Handler, Event3Handler, Event4Handler, Event5Handler, Event6Handler, Event7Handler, Event8Handler, Event9Handler, Event10Handler {

	public void handle( Event1 event ) {
		//Do nothing
	}

	public void handle( Event2 event ) {
		//Do nothing
	}

	public void handle( Event3 event ) {
		//Do nothing
	}

	public void handle( Event4 event ) {
		//Do nothing
	}

	public void handle( Event10 event ) {
		//Do nothing
	}

	public void handle( Event5 event ) {
		//Do nothing
	}

	public void handle( Event6 event ) {
		//Do nothing
	}

	public void handle( Event7 event ) {
		//Do nothing
	}

	public void handle( Event8 event ) {
		//Do nothing
	}

	public void handle( Event9 event ) {
		//Do nothing
	}

}
