package com.mvp4g.util.test_tools;

import com.mvp4g.client.Mvp4gModule;
import com.mvp4g.client.annotation.module.HistoryName;
import com.mvp4g.util.test_tools.annotation.Presenters.TestBroadcast;

public class Modules {

	public static interface Module1 extends Mvp4gModule {

	}

	@HistoryName( "moduleWithParent" )
	public static interface ModuleWithParent extends Mvp4gModule {

		public void setParentModule( Mvp4gModule module );

	}

	public static interface ModuleWithParentNoName extends Mvp4gModule {

	}
	
	public static interface BroadcastModule extends Mvp4gModule, TestBroadcast {

	}

}
