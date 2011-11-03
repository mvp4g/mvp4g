package com.mvp4g.client;

import static junit.framework.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.mvp4g.client.event.EventBus;
import com.mvp4g.client.presenter.BasePresenter;

public class Mvp4gSplitterTest {
	
	private class Mvp4gSplitterImpl extends AbstractMvp4gSplitter {

		protected Mvp4gSplitterImpl( int size ) {
			super( size );			
		}
		
	}
	
	private Mvp4gSplitterImpl splitter;
	
	@Before
	public void setup(){
		splitter = new Mvp4gSplitterImpl( 2 );
	}
	
	@Test
	public void testIsActivated(){
		assertFalse(splitter.isActivated( false, new int[0] ));
		assertTrue(splitter.isActivated( false, new int[]{0,1} ));
		assertFalse(splitter.isActivated( true, new int[]{0,1} ));
		splitter.handlers[0] = new BasePresenter<Object, EventBus>();
		assertTrue(splitter.isActivated( true, new int[]{0,1} ));
	}
	
	@Test
	public void testSetActivated(){
		assertTrue(splitter.isActivated( false, new int[]{0,1} ));
		splitter.setActivated( false, new int[]{0,1} );
		assertFalse(splitter.isActivated( false, new int[]{0,1} ));
		splitter.setActivated( true, new int[]{0} );
		assertTrue(splitter.isActivated( false, new int[]{0,1} ));
		assertFalse(splitter.isActivated( false, new int[]{1} ));
		assertTrue(splitter.isActivated( false, new int[]{0} ));
	}
	
	@Test
	public void testSetActivatedWithImpl(){
		splitter.handlers[0] = new BasePresenter<Object, EventBus>();
		assertTrue( splitter.handlers[0].isActivated( false, "event") );
		splitter.setActivated( false, new int[]{0} );
		assertFalse( splitter.handlers[0].isActivated( false, "event") );
		splitter.setActivated( true, new int[]{0} );
		assertTrue( splitter.handlers[0].isActivated( false, "event") );
	}

}
