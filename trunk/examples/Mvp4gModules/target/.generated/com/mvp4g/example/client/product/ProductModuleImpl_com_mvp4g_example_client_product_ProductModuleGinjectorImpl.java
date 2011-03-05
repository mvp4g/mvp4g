package com.mvp4g.example.client.product;

import com.google.gwt.core.client.GWT;

public class ProductModuleImpl_com_mvp4g_example_client_product_ProductModuleGinjectorImpl implements com.mvp4g.example.client.product.ProductModuleImpl.com_mvp4g_example_client_product_ProductModuleGinjector {
  public com.mvp4g.example.client.product.presenter.ProductCreationPresenter getcom_mvp4g_example_client_product_presenter_ProductCreationPresenter() {
    return get_Key$type$com$mvp4g$example$client$product$presenter$ProductCreationPresenter$_annotation$$none$$();
  }
  
  public com.mvp4g.example.client.product.view.ProductCreationView getcom_mvp4g_example_client_product_presenter_ProductCreationPresenterView() {
    return get_Key$type$com$mvp4g$example$client$product$view$ProductCreationView$_annotation$$none$$();
  }
  
  public com.mvp4g.example.client.product.presenter.ProductDisplayPresenter getcom_mvp4g_example_client_product_presenter_ProductDisplayPresenter() {
    return get_Key$type$com$mvp4g$example$client$product$presenter$ProductDisplayPresenter$_annotation$$none$$();
  }
  
  public com.mvp4g.example.client.product.view.ProductDisplayView getcom_mvp4g_example_client_product_presenter_ProductDisplayPresenterView() {
    return get_Key$type$com$mvp4g$example$client$product$view$ProductDisplayView$_annotation$$none$$();
  }
  
  public com.mvp4g.example.client.product.presenter.ProductEditPresenter getcom_mvp4g_example_client_product_presenter_ProductEditPresenter() {
    return get_Key$type$com$mvp4g$example$client$product$presenter$ProductEditPresenter$_annotation$$none$$();
  }
  
  public com.mvp4g.example.client.product.view.ProductEditView getcom_mvp4g_example_client_product_presenter_ProductEditPresenterView() {
    return get_Key$type$com$mvp4g$example$client$product$view$ProductEditView$_annotation$$none$$();
  }
  
  public com.mvp4g.example.client.product.presenter.ProductListPresenter getcom_mvp4g_example_client_product_presenter_ProductListPresenter() {
    return get_Key$type$com$mvp4g$example$client$product$presenter$ProductListPresenter$_annotation$$none$$();
  }
  
  public com.mvp4g.example.client.product.view.ProductListView getcom_mvp4g_example_client_product_presenter_ProductListPresenterView() {
    return get_Key$type$com$mvp4g$example$client$product$view$ProductListView$_annotation$$none$$();
  }
  
  
  /**
   * Binding for com.mvp4g.example.client.product.ProductServiceAsync declared at:
   *   Implicit binding for Key[type=com.mvp4g.example.client.product.ProductServiceAsync, annotation=[none]]
   */
  private void memberInject_Key$type$com$mvp4g$example$client$product$ProductServiceAsync$_annotation$$none$$(com.mvp4g.example.client.product.ProductServiceAsync injectee) {
    
  }
  
  private com.mvp4g.example.client.product.ProductServiceAsync create_Key$type$com$mvp4g$example$client$product$ProductServiceAsync$_annotation$$none$$() {
    Object created = GWT.create(com.mvp4g.example.client.product.ProductService.class);
    assert created instanceof com.mvp4g.example.client.product.ProductServiceAsync;
    com.mvp4g.example.client.product.ProductServiceAsync result = (com.mvp4g.example.client.product.ProductServiceAsync) created;
    
    memberInject_Key$type$com$mvp4g$example$client$product$ProductServiceAsync$_annotation$$none$$(result);
    return result;
  }
  
  private com.mvp4g.example.client.product.ProductServiceAsync singleton_Key$type$com$mvp4g$example$client$product$ProductServiceAsync$_annotation$$none$$ = null;
  
  
  /**
   * Singleton bound at:
   *   Implicit binding for Key[type=com.mvp4g.example.client.product.ProductServiceAsync, annotation=[none]]
   */
  private com.mvp4g.example.client.product.ProductServiceAsync get_Key$type$com$mvp4g$example$client$product$ProductServiceAsync$_annotation$$none$$() {
    if (singleton_Key$type$com$mvp4g$example$client$product$ProductServiceAsync$_annotation$$none$$ == null) {
      singleton_Key$type$com$mvp4g$example$client$product$ProductServiceAsync$_annotation$$none$$ = create_Key$type$com$mvp4g$example$client$product$ProductServiceAsync$_annotation$$none$$();
    }
    return singleton_Key$type$com$mvp4g$example$client$product$ProductServiceAsync$_annotation$$none$$;
  }
  
  
  /**
   * Binding for com.mvp4g.example.client.product.presenter.ProductListPresenter declared at:
   *   Implicit binding for Key[type=com.mvp4g.example.client.product.presenter.ProductListPresenter, annotation=[none]]
   */
  private native void com$mvp4g$example$client$product$presenter$ProductListPresenter_service_fieldInjection(com.mvp4g.example.client.product.presenter.ProductListPresenter injectee, com.mvp4g.example.client.product.ProductServiceAsync value) /*-{
    injectee.@com.mvp4g.example.client.product.presenter.ProductListPresenter::service = value;
  }-*/;
  
  private void memberInject_Key$type$com$mvp4g$example$client$product$presenter$ProductListPresenter$_annotation$$none$$(com.mvp4g.example.client.product.presenter.ProductListPresenter injectee) {
    com$mvp4g$example$client$product$presenter$ProductListPresenter_service_fieldInjection(injectee, get_Key$type$com$mvp4g$example$client$product$ProductServiceAsync$_annotation$$none$$());
    
  }
  
  private com.mvp4g.example.client.product.presenter.ProductListPresenter create_Key$type$com$mvp4g$example$client$product$presenter$ProductListPresenter$_annotation$$none$$() {
    Object created = GWT.create(com.mvp4g.example.client.product.presenter.ProductListPresenter.class);
    assert created instanceof com.mvp4g.example.client.product.presenter.ProductListPresenter;
    com.mvp4g.example.client.product.presenter.ProductListPresenter result = (com.mvp4g.example.client.product.presenter.ProductListPresenter) created;
    
    memberInject_Key$type$com$mvp4g$example$client$product$presenter$ProductListPresenter$_annotation$$none$$(result);
    return result;
  }
  
  
  /**
   * Binding for com.mvp4g.example.client.product.presenter.ProductListPresenter declared at:
   *   Implicit binding for Key[type=com.mvp4g.example.client.product.presenter.ProductListPresenter, annotation=[none]]
   */
  private com.mvp4g.example.client.product.presenter.ProductListPresenter get_Key$type$com$mvp4g$example$client$product$presenter$ProductListPresenter$_annotation$$none$$() {
    return create_Key$type$com$mvp4g$example$client$product$presenter$ProductListPresenter$_annotation$$none$$();
  }
  
  
  
  /**
   * Binding for com.mvp4g.example.client.product.view.ProductCreationView declared at:
   *   Implicit binding for Key[type=com.mvp4g.example.client.product.view.ProductCreationView, annotation=[none]]
   */
  private void memberInject_Key$type$com$mvp4g$example$client$product$view$ProductCreationView$_annotation$$none$$(com.mvp4g.example.client.product.view.ProductCreationView injectee) {
    
  }
  
  private com.mvp4g.example.client.product.view.ProductCreationView create_Key$type$com$mvp4g$example$client$product$view$ProductCreationView$_annotation$$none$$() {
    Object created = GWT.create(com.mvp4g.example.client.product.view.ProductCreationView.class);
    assert created instanceof com.mvp4g.example.client.product.view.ProductCreationView;
    com.mvp4g.example.client.product.view.ProductCreationView result = (com.mvp4g.example.client.product.view.ProductCreationView) created;
    
    memberInject_Key$type$com$mvp4g$example$client$product$view$ProductCreationView$_annotation$$none$$(result);
    return result;
  }
  
  
  /**
   * Binding for com.mvp4g.example.client.product.view.ProductCreationView declared at:
   *   Implicit binding for Key[type=com.mvp4g.example.client.product.view.ProductCreationView, annotation=[none]]
   */
  private com.mvp4g.example.client.product.view.ProductCreationView get_Key$type$com$mvp4g$example$client$product$view$ProductCreationView$_annotation$$none$$() {
    return create_Key$type$com$mvp4g$example$client$product$view$ProductCreationView$_annotation$$none$$();
  }
  
  
  
  /**
   * Binding for com.mvp4g.example.client.product.view.ProductEditView declared at:
   *   Implicit binding for Key[type=com.mvp4g.example.client.product.view.ProductEditView, annotation=[none]]
   */
  private void memberInject_Key$type$com$mvp4g$example$client$product$view$ProductEditView$_annotation$$none$$(com.mvp4g.example.client.product.view.ProductEditView injectee) {
    
  }
  
  private com.mvp4g.example.client.product.view.ProductEditView create_Key$type$com$mvp4g$example$client$product$view$ProductEditView$_annotation$$none$$() {
    Object created = GWT.create(com.mvp4g.example.client.product.view.ProductEditView.class);
    assert created instanceof com.mvp4g.example.client.product.view.ProductEditView;
    com.mvp4g.example.client.product.view.ProductEditView result = (com.mvp4g.example.client.product.view.ProductEditView) created;
    
    memberInject_Key$type$com$mvp4g$example$client$product$view$ProductEditView$_annotation$$none$$(result);
    return result;
  }
  
  
  /**
   * Binding for com.mvp4g.example.client.product.view.ProductEditView declared at:
   *   Implicit binding for Key[type=com.mvp4g.example.client.product.view.ProductEditView, annotation=[none]]
   */
  private com.mvp4g.example.client.product.view.ProductEditView get_Key$type$com$mvp4g$example$client$product$view$ProductEditView$_annotation$$none$$() {
    return create_Key$type$com$mvp4g$example$client$product$view$ProductEditView$_annotation$$none$$();
  }
  
  
  
  /**
   * Binding for com.mvp4g.example.client.product.presenter.ProductEditPresenter declared at:
   *   Implicit binding for Key[type=com.mvp4g.example.client.product.presenter.ProductEditPresenter, annotation=[none]]
   */
  private native void com$mvp4g$example$client$product$presenter$AbstractProductPresenter_service_fieldInjection(com.mvp4g.example.client.product.presenter.AbstractProductPresenter injectee, com.mvp4g.example.client.product.ProductServiceAsync value) /*-{
    injectee.@com.mvp4g.example.client.product.presenter.AbstractProductPresenter::service = value;
  }-*/;
  
  private void memberInject_Key$type$com$mvp4g$example$client$product$presenter$ProductEditPresenter$_annotation$$none$$(com.mvp4g.example.client.product.presenter.ProductEditPresenter injectee) {
    com$mvp4g$example$client$product$presenter$AbstractProductPresenter_service_fieldInjection(injectee, get_Key$type$com$mvp4g$example$client$product$ProductServiceAsync$_annotation$$none$$());
    
  }
  
  private com.mvp4g.example.client.product.presenter.ProductEditPresenter create_Key$type$com$mvp4g$example$client$product$presenter$ProductEditPresenter$_annotation$$none$$() {
    Object created = GWT.create(com.mvp4g.example.client.product.presenter.ProductEditPresenter.class);
    assert created instanceof com.mvp4g.example.client.product.presenter.ProductEditPresenter;
    com.mvp4g.example.client.product.presenter.ProductEditPresenter result = (com.mvp4g.example.client.product.presenter.ProductEditPresenter) created;
    
    memberInject_Key$type$com$mvp4g$example$client$product$presenter$ProductEditPresenter$_annotation$$none$$(result);
    return result;
  }
  
  
  /**
   * Binding for com.mvp4g.example.client.product.presenter.ProductEditPresenter declared at:
   *   Implicit binding for Key[type=com.mvp4g.example.client.product.presenter.ProductEditPresenter, annotation=[none]]
   */
  private com.mvp4g.example.client.product.presenter.ProductEditPresenter get_Key$type$com$mvp4g$example$client$product$presenter$ProductEditPresenter$_annotation$$none$$() {
    return create_Key$type$com$mvp4g$example$client$product$presenter$ProductEditPresenter$_annotation$$none$$();
  }
  
  
  
  /**
   * Binding for com.mvp4g.example.client.product.view.ProductDisplayView declared at:
   *   Implicit binding for Key[type=com.mvp4g.example.client.product.view.ProductDisplayView, annotation=[none]]
   */
  private void memberInject_Key$type$com$mvp4g$example$client$product$view$ProductDisplayView$_annotation$$none$$(com.mvp4g.example.client.product.view.ProductDisplayView injectee) {
    
  }
  
  private com.mvp4g.example.client.product.view.ProductDisplayView create_Key$type$com$mvp4g$example$client$product$view$ProductDisplayView$_annotation$$none$$() {
    Object created = GWT.create(com.mvp4g.example.client.product.view.ProductDisplayView.class);
    assert created instanceof com.mvp4g.example.client.product.view.ProductDisplayView;
    com.mvp4g.example.client.product.view.ProductDisplayView result = (com.mvp4g.example.client.product.view.ProductDisplayView) created;
    
    memberInject_Key$type$com$mvp4g$example$client$product$view$ProductDisplayView$_annotation$$none$$(result);
    return result;
  }
  
  
  /**
   * Binding for com.mvp4g.example.client.product.view.ProductDisplayView declared at:
   *   Implicit binding for Key[type=com.mvp4g.example.client.product.view.ProductDisplayView, annotation=[none]]
   */
  private com.mvp4g.example.client.product.view.ProductDisplayView get_Key$type$com$mvp4g$example$client$product$view$ProductDisplayView$_annotation$$none$$() {
    return create_Key$type$com$mvp4g$example$client$product$view$ProductDisplayView$_annotation$$none$$();
  }
  
  
  
  /**
   * Binding for com.mvp4g.example.client.product.presenter.ProductCreationPresenter declared at:
   *   Implicit binding for Key[type=com.mvp4g.example.client.product.presenter.ProductCreationPresenter, annotation=[none]]
   */
  private native void com$mvp4g$example$client$product$presenter$AbstractProductPresenter_service_fieldInjection_(com.mvp4g.example.client.product.presenter.AbstractProductPresenter injectee, com.mvp4g.example.client.product.ProductServiceAsync value) /*-{
    injectee.@com.mvp4g.example.client.product.presenter.AbstractProductPresenter::service = value;
  }-*/;
  
  private void memberInject_Key$type$com$mvp4g$example$client$product$presenter$ProductCreationPresenter$_annotation$$none$$(com.mvp4g.example.client.product.presenter.ProductCreationPresenter injectee) {
    com$mvp4g$example$client$product$presenter$AbstractProductPresenter_service_fieldInjection_(injectee, get_Key$type$com$mvp4g$example$client$product$ProductServiceAsync$_annotation$$none$$());
    
  }
  
  private com.mvp4g.example.client.product.presenter.ProductCreationPresenter create_Key$type$com$mvp4g$example$client$product$presenter$ProductCreationPresenter$_annotation$$none$$() {
    Object created = GWT.create(com.mvp4g.example.client.product.presenter.ProductCreationPresenter.class);
    assert created instanceof com.mvp4g.example.client.product.presenter.ProductCreationPresenter;
    com.mvp4g.example.client.product.presenter.ProductCreationPresenter result = (com.mvp4g.example.client.product.presenter.ProductCreationPresenter) created;
    
    memberInject_Key$type$com$mvp4g$example$client$product$presenter$ProductCreationPresenter$_annotation$$none$$(result);
    return result;
  }
  
  
  /**
   * Binding for com.mvp4g.example.client.product.presenter.ProductCreationPresenter declared at:
   *   Implicit binding for Key[type=com.mvp4g.example.client.product.presenter.ProductCreationPresenter, annotation=[none]]
   */
  private com.mvp4g.example.client.product.presenter.ProductCreationPresenter get_Key$type$com$mvp4g$example$client$product$presenter$ProductCreationPresenter$_annotation$$none$$() {
    return create_Key$type$com$mvp4g$example$client$product$presenter$ProductCreationPresenter$_annotation$$none$$();
  }
  
  
  
  /**
   * Binding for com.mvp4g.example.client.product.presenter.ProductDisplayPresenter declared at:
   *   Implicit binding for Key[type=com.mvp4g.example.client.product.presenter.ProductDisplayPresenter, annotation=[none]]
   */
  private native void com$mvp4g$example$client$product$presenter$AbstractProductPresenter_service_fieldInjection__(com.mvp4g.example.client.product.presenter.AbstractProductPresenter injectee, com.mvp4g.example.client.product.ProductServiceAsync value) /*-{
    injectee.@com.mvp4g.example.client.product.presenter.AbstractProductPresenter::service = value;
  }-*/;
  
  private void memberInject_Key$type$com$mvp4g$example$client$product$presenter$ProductDisplayPresenter$_annotation$$none$$(com.mvp4g.example.client.product.presenter.ProductDisplayPresenter injectee) {
    com$mvp4g$example$client$product$presenter$AbstractProductPresenter_service_fieldInjection__(injectee, get_Key$type$com$mvp4g$example$client$product$ProductServiceAsync$_annotation$$none$$());
    
  }
  
  private com.mvp4g.example.client.product.presenter.ProductDisplayPresenter create_Key$type$com$mvp4g$example$client$product$presenter$ProductDisplayPresenter$_annotation$$none$$() {
    Object created = GWT.create(com.mvp4g.example.client.product.presenter.ProductDisplayPresenter.class);
    assert created instanceof com.mvp4g.example.client.product.presenter.ProductDisplayPresenter;
    com.mvp4g.example.client.product.presenter.ProductDisplayPresenter result = (com.mvp4g.example.client.product.presenter.ProductDisplayPresenter) created;
    
    memberInject_Key$type$com$mvp4g$example$client$product$presenter$ProductDisplayPresenter$_annotation$$none$$(result);
    return result;
  }
  
  
  /**
   * Binding for com.mvp4g.example.client.product.presenter.ProductDisplayPresenter declared at:
   *   Implicit binding for Key[type=com.mvp4g.example.client.product.presenter.ProductDisplayPresenter, annotation=[none]]
   */
  private com.mvp4g.example.client.product.presenter.ProductDisplayPresenter get_Key$type$com$mvp4g$example$client$product$presenter$ProductDisplayPresenter$_annotation$$none$$() {
    return create_Key$type$com$mvp4g$example$client$product$presenter$ProductDisplayPresenter$_annotation$$none$$();
  }
  
  
  
  /**
   * Binding for com.mvp4g.example.client.product.view.ProductListView declared at:
   *   Implicit binding for Key[type=com.mvp4g.example.client.product.view.ProductListView, annotation=[none]]
   */
  private void memberInject_Key$type$com$mvp4g$example$client$product$view$ProductListView$_annotation$$none$$(com.mvp4g.example.client.product.view.ProductListView injectee) {
    
  }
  
  private com.mvp4g.example.client.product.view.ProductListView create_Key$type$com$mvp4g$example$client$product$view$ProductListView$_annotation$$none$$() {
    Object created = GWT.create(com.mvp4g.example.client.product.view.ProductListView.class);
    assert created instanceof com.mvp4g.example.client.product.view.ProductListView;
    com.mvp4g.example.client.product.view.ProductListView result = (com.mvp4g.example.client.product.view.ProductListView) created;
    
    memberInject_Key$type$com$mvp4g$example$client$product$view$ProductListView$_annotation$$none$$(result);
    return result;
  }
  
  
  /**
   * Binding for com.mvp4g.example.client.product.view.ProductListView declared at:
   *   Implicit binding for Key[type=com.mvp4g.example.client.product.view.ProductListView, annotation=[none]]
   */
  private com.mvp4g.example.client.product.view.ProductListView get_Key$type$com$mvp4g$example$client$product$view$ProductListView$_annotation$$none$$() {
    return create_Key$type$com$mvp4g$example$client$product$view$ProductListView$_annotation$$none$$();
  }
  
  
  public ProductModuleImpl_com_mvp4g_example_client_product_ProductModuleGinjectorImpl() {
    
  }
  
}
