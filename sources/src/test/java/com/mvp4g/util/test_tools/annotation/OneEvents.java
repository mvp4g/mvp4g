package com.mvp4g.util.test_tools.annotation;

import com.mvp4g.client.annotation.Events;
import com.mvp4g.util.test_tools.annotation.presenters.SimplePresenter;

@Events( startPresenter = SimplePresenter.class )
public interface OneEvents {

}
