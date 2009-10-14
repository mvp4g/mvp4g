package com.mvp4g.example.client.mock.event;

import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.event.GridEvent;

public class MockGridEvent<M extends ModelData> extends GridEvent<M> {

	public MockGridEvent(){
		super(null);
	}
	
	@Override
	public int getRowIndex() {
		return 0;
	}

}
