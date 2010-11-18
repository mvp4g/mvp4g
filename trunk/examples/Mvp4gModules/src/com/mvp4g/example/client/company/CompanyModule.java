package com.mvp4g.example.client.company;

import com.mvp4g.client.Mvp4gModule;
import com.mvp4g.client.annotation.module.HistoryName;
import com.mvp4g.example.client.util.HasBeenThereHandler;

@HistoryName( "company" )
public interface CompanyModule extends Mvp4gModule, HasBeenThereHandler {

}
