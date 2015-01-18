package com.mvp4g.rebind.test_tools.annotation;

import com.mvp4g.client.annotation.Events;
import com.mvp4g.rebind.test_tools.annotation.presenters.SimplePresenter01;

@Events( startPresenter = SimplePresenter01.class )
public interface OneEvents {

}
