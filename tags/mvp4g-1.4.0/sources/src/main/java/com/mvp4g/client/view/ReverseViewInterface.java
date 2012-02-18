package com.mvp4g.client.view;

public interface ReverseViewInterface<T> {
	
	void setPresenter(T presenter);
	
	T getPresenter();

}
