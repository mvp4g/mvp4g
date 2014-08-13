package com.mvp4g.example.client.ui.shortcuts;

import com.google.gwt.user.client.ui.IsWidget;
import com.mvp4g.client.view.ReverseViewInterface;
import com.mvp4g.example.client.ui.shortcuts.ShortCutsPresenter.FOLDER_TYPE;

public interface IShortCutsView
  extends IsWidget,
          ReverseViewInterface<IShortCutsView.IShortCutsPresenter> {

  void addContact(String name,
                  int index);

  void addTask(String task);

  void addFolder(FOLDER_TYPE folder);

  void showContactPopup(String name,
                        String email,
                        IsWidget widget);

  interface IShortCutsPresenter {
    void onContactClick(int index,
                        IsWidget contactWidget);
  }
}
