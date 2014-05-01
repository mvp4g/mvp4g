package com.mvp4g.example.client.company;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.mvp4g.example.client.company.bean.CompanyBean;

import java.util.List;

@RemoteServiceRelativePath("company")
public interface CompanyService
  extends RemoteService {

  public List<CompanyBean> getCompanies(int start,
                                        int end);

  public void createCompany(CompanyBean company);

  public void deleteCompany(CompanyBean company);

  public void updateCompany(CompanyBean company);

}
