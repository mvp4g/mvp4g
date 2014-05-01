package com.mvp4g.example.client.company;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.mvp4g.example.client.company.bean.CompanyBean;

import java.util.List;

public interface CompanyServiceAsync {

  void getCompanies(int start,
                    int end,
                    AsyncCallback<List<CompanyBean>> async);

  void deleteCompany(CompanyBean company,
                     AsyncCallback<Void> callback);

  void updateCompany(CompanyBean company,
                     AsyncCallback<Void> callback);

  void createCompany(CompanyBean company,
                     AsyncCallback<Void> callback);
}
