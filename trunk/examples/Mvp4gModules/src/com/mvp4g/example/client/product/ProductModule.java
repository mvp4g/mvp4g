package com.mvp4g.example.client.product;

import com.mvp4g.client.Mvp4gModule;
import com.mvp4g.client.annotation.module.HistoryName;
import com.mvp4g.example.client.util.HasBeenThereHandler;

@HistoryName( "product" )
public interface ProductModule extends Mvp4gModule, HasBeenThereHandler {

}
