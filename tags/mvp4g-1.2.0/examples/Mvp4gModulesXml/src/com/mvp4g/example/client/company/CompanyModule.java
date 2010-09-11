package com.mvp4g.example.client.company;

import com.mvp4g.client.Mvp4gModule;
import com.mvp4g.client.annotation.XmlFilePath;
import com.mvp4g.client.annotation.module.HistoryName;

@HistoryName( "company" )
@XmlFilePath( "xmlConfig/company-mvp4g.xml" )
public interface CompanyModule extends Mvp4gModule {

	public void setParentModule( Mvp4gModule eventBus );

}
