package com.mvp4g.rebind.test_tools;

import com.mvp4g.client.Mvp4gModule;
import com.mvp4g.client.annotation.module.HistoryName;
import com.mvp4g.client.annotation.module.Loader;
import com.mvp4g.rebind.test_tools.annotation.TestBroadcast;
import com.mvp4g.rebind.test_tools.annotation.TestBroadcast2;

public class Modules {

  public static interface Module01
    extends Mvp4gModule {

  }

  public static interface ChildModule01
    extends Mvp4gModule {

  }

  public static interface ChildModule02
    extends Mvp4gModule {

  }

  public static interface ChildModule03
    extends Mvp4gModule {

  }

  @HistoryName("moduleWithParent01")
  public static interface ModuleWithParent01
    extends Mvp4gModule {

    public void setParentModule(Mvp4gModule module);

  }

  @HistoryName("moduleWithParent02")
  public static interface ModuleWithParent02
    extends Mvp4gModule {

    public void setParentModule(Mvp4gModule module);

  }

  public static interface ModuleWithParentNoName
    extends Mvp4gModule {

  }

  public static interface BroadcastModule
    extends Mvp4gModule,
            TestBroadcast {

  }

  public static interface BroadcastModule2
    extends Mvp4gModule,
            TestBroadcast,
            TestBroadcast2 {

  }

  @Loader(Loaders.Loader1.class)
  public static interface ModuleWithLoader
    extends Mvp4gModule,
            TestBroadcast,
            TestBroadcast2 {

  }

  @Loader(Loaders.Loader2.class)
  public static interface ModuleWithSameLoader1
    extends Mvp4gModule,
            TestBroadcast,
            TestBroadcast2 {

  }

  @Loader(Loaders.Loader2.class)
  public static interface ModuleWithSameLoader2
    extends Mvp4gModule,
            TestBroadcast,
            TestBroadcast2 {

  }

}
