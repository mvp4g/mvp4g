package com.mvp4g.example.client.company;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.mvp4g.example.client.company.bean.CompanyBean;

public interface CompanyServiceAsync {

	void getCompanies( AsyncCallback<List<CompanyBean>> async );

	void deleteCompany( CompanyBean company, AsyncCallback<Void> callback );

	void updateCompany( CompanyBean company, AsyncCallback<Void> callback );

	void createCompany( CompanyBean company, AsyncCallback<Void> callback );
}
