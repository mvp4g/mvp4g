package com.mvp4g.example.client.validators;

import com.sencha.gxt.widget.core.client.form.validator.RegExValidator;

/**
 * Created by hoss on 09.08.14.
 */
public class EmailValidator
    extends RegExValidator {

  private final static String regex = "^([\\w\\-\\.]+)@((\\[([0-9]{1,3}\\.){3}[0-9]{1,3}\\])|(([\\w\\-]+\\.)+)([A-Za-z]{2,4}))$";

  public EmailValidator() {
    super(regex,
          "Please enter a valid email address");
  }
}
