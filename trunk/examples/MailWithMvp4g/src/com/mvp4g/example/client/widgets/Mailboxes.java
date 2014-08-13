/*
 * Copyright 2007 Google Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.mvp4g.example.client.widgets;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import com.mvp4g.example.client.ui.shortcuts.ShortCutsPresenter.FOLDER_TYPE;

import java.util.HashMap;
import java.util.Map;

/**
 * A tree displaying a set of email folders.
 */
public class Mailboxes
    extends Composite {

  private static Images                     images     = GWT.create(Images.class);
  private static Map<String, ImageResource> itemImages = new HashMap<String, ImageResource>();
  static {
    itemImages.put("Inbox",
                   images.inbox());
    itemImages.put("Drafts",
                   images.drafts());
    itemImages.put("Templates",
                   images.templates());
    itemImages.put("Sent",
                   images.sent());
    itemImages.put("Trash",
                   images.trash());
  }
  private Tree     tree;
  private TreeItem root;
  /**
   * Constructs a new mailboxes widget with a bundle of images.
   */
  public Mailboxes() {
    tree = new Tree(images);
    TreeItem root = new TreeItem(imageItemHTML(images.home(),
                                               "foo@example.com"));
    tree.addItem(root);
    this.root = root;
    root.setState(true);
    initWidget(tree);
  }

  /**
   * Generates HTML for a tree item with an attached icon.
   *
   * @param imageProto the image prototype to use
   * @param title      the title of the item
   * @return the resultant HTML
   */
  private SafeHtml imageItemHTML(ImageResource imageProto,
                                 String title) {
    return SafeHtmlUtils.fromTrustedString(AbstractImagePrototype.create(imageProto)
                                                                 .getHTML() + " " + title);
  }

  /**
   * A helper method to simplify adding tree items that have attached images.
   */
  public void addImageItem(FOLDER_TYPE folder) {

    TreeItem item;
    switch (folder) {
      case INBOX:
        item = new TreeItem(imageItemHTML(images.inbox(),
                                          "Inbox"));
        break;
      case DRAFS:
        item = new TreeItem(imageItemHTML(images.drafts(),
                                          "Drafts"));
        break;
      case TEMPLATES:
        item = new TreeItem(imageItemHTML(images.templates(),
                                          "Templates"));
        break;
      case SENT:
        item = new TreeItem(imageItemHTML(images.sent(),
                                          "Sent"));
        break;
      default:
        item = new TreeItem(imageItemHTML(images.trash(),
                                          "Trash"));
        break;

    }

    root.addItem(item);
  }

  /**
   * Specifies the images that will be bundled for this Composite and specify that tree's images
   * should also be included in the same bundle.
   */
  public interface Images
      extends ClientBundle,
              Tree.Resources {
    ImageResource drafts();

    ImageResource home();

    ImageResource inbox();

    ImageResource sent();

    ImageResource templates();

    ImageResource trash();

    @Source("noimage.png")
    ImageResource treeLeaf();
  }

}
