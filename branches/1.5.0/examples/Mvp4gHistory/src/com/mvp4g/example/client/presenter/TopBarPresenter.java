package com.mvp4g.example.client.presenter;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasValue;
import com.google.inject.Inject;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.LazyPresenter;
import com.mvp4g.client.view.LazyView;
import com.mvp4g.example.client.MyEventBus;
import com.mvp4g.example.client.ServiceAsync;
import com.mvp4g.example.client.bean.BasicBean;
import com.mvp4g.example.client.bean.DealBean;
import com.mvp4g.example.client.bean.ProductBean;
import com.mvp4g.example.client.view.TopBarView;
import com.mvp4g.example.client.widget.IView;

import java.util.List;

@Presenter(view = TopBarView.class)
public class TopBarPresenter
  extends LazyPresenter<TopBarPresenter.TopBarViewInterface, MyEventBus> {

  public interface TopBarViewInterface
    extends LazyView,
            IView {

    String getSelectedProduct();

    void setSelectedProduct(int index);

    void addProduct(String text,
                    String value);

    String getSelectedDeal();

    void setSelectedDeal(int index);

    void addDeal(String text,
                 String value);

    HasClickHandlers getShowProductButton();

    HasClickHandlers getShowDealButton();

    HasValue<Boolean> getSave();

  }

  @Inject
  private ServiceAsync service = null;

  private List<BasicBean> products;
  private ProductBean productSelected = null;

  private List<BasicBean> deals;
  private DealBean dealSelected = null;

  public void bindView() {
    view.getShowDealButton().addClickHandler(new ClickHandler() {

      public void onClick(ClickEvent event) {
        String dealId = view.getSelectedDeal();
        service.getDealDetails(dealId,
                               new AsyncCallback<DealBean>() {

                                 public void onFailure(Throwable caught) {
                                   //do sthg
                                 }

                                 public void onSuccess(DealBean deal) {
                                   dealSelected = deal;
                                   eventBus.displayDeal(deal);
                                 }

                               });
      }

    });

    view.getShowProductButton().addClickHandler(new ClickHandler() {

      public void onClick(ClickEvent event) {
        String productId = view.getSelectedProduct();
        service.getProductDetails(productId,
                                  new AsyncCallback<ProductBean>() {

                                    public void onFailure(Throwable caught) {
                                      //do sthg
                                    }

                                    public void onSuccess(ProductBean product) {
                                      productSelected = product;
                                      eventBus.displayProduct(product);
                                    }

                                  });
      }

    });
    view.getSave().addValueChangeHandler(new ValueChangeHandler<Boolean>() {

      public void onValueChange(ValueChangeEvent<Boolean> event) {
        eventBus.setHistoryStored(event.getValue());
      }
    });

  }

  public void onStart() {
    service.getDeals(new AsyncCallback<List<BasicBean>>() {

      public void onFailure(Throwable caught) {
        //do sthg
      }

      public void onSuccess(List<BasicBean> deals) {
        service.getProducts(new AsyncCallback<List<BasicBean>>() {

          public void onFailure(Throwable caught) {
            //do sthg
          }

          public void onSuccess(List<BasicBean> products) {
            TopBarPresenter.this.products = products;
            for (BasicBean product : products) {
              view.addProduct(product.getName(),
                              product.getId());
            }

            if (productSelected != null) {
              view.setSelectedProduct(products.indexOf(productSelected));
            }
            eventBus.changeTopWidget(view);
          }

        });

        TopBarPresenter.this.deals = deals;
        for (BasicBean deal : deals) {
          view.addDeal(deal.getName(),
                       deal.getId());
        }

        if (dealSelected != null) {
          view.setSelectedDeal(deals.indexOf(dealSelected));
        }

      }

    });
  }

  public void onDisplayDeal(DealBean bean) {
    if ((bean != null) && (!bean.equals(dealSelected))) {
      dealSelected = bean;
      if (deals != null) {
        view.setSelectedDeal(deals.indexOf(dealSelected));
      }
    }
  }

  public void onDisplayProduct(ProductBean bean) {
    if ((bean != null) && (!bean.equals(productSelected))) {
      productSelected = bean;
      if (products != null) {
        view.setSelectedDeal(products.indexOf(productSelected));
      }
    }
  }

  public void onInit() {
    view.setSelectedProduct(0);
    view.setSelectedDeal(0);
  }

}
