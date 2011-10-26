package com.mvp4g.client;

import com.mvp4g.client.event.EventHandlerInterface;

public abstract class AbstractMvp4gSplitter {

	@SuppressWarnings( "rawtypes" )
	protected EventHandlerInterface[] handlers;

	private boolean[] activated;

	protected AbstractMvp4gSplitter( int size ) {
		handlers = new EventHandlerInterface[size];
		activated = new boolean[size];
		for ( int i = 0; i < size; i++ ) {
			activated[i] = true;
		}
	}

	protected boolean isActivated( boolean passive, int[] indexes ) {
		boolean isActivated = false;
		for ( int i = 0; ( i < indexes.length ) && !isActivated; i++ ) {
			// don't handle a regular event if all presenter have been deactivated
			// for passive event, also check if at least one of the handler is already built
			isActivated = activated[indexes[i]] && ( !passive || ( handlers[indexes[i]] != null ) );
		}
		return isActivated;
	}

	public void setActivated( boolean isActivated, int[] indexes ) {
		for ( int index : indexes ) {
			activated[index] = isActivated;
			if ( handlers[index] != null ) {
				handlers[index].setActivated( isActivated );
			}
		}
	}

}
