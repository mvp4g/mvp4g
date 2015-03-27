package com.mvp4g.client.history;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.mvp4g.client.test_tools.EventBusWithLookUpStub;

public class NavigationEventCommandTest {
	
	private NavigationEventCommand cmd;
	
	private EventBusWithLookUpStub eventBus;
	
	private NavigationConfirmationInterface conf;
	
	@Before
	public void setUp(){
		eventBus = new EventBusWithLookUpStub();
		cmd = new NavigationEventCommand(eventBus) {
			
			@Override
			protected void execute() {
				//nothing to do
			}
		};
		conf = new NavigationConfirmationInterface() {
			
			public void confirm( NavigationEventCommand event ) {
				//nothing to do
			}
		};
	}
	
	@Test
	public void testFireEvent(){
		eventBus.setNavigationConfirmation( conf );
		cmd.fireEvent();
		assertNull( eventBus.getLastNavigationConfirmation() );
		
		eventBus.setNavigationConfirmation( conf );
		cmd.fireEvent(true);
		assertNull( eventBus.getLastNavigationConfirmation() );
		
		eventBus.setNavigationConfirmation( conf );
		cmd.fireEvent(false);
		assertSame( conf, eventBus.getLastNavigationConfirmation() );
	}

}
