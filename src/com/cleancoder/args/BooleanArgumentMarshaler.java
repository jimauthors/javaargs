package com.cleancoder.args;

import java.util.Optional;

public class BooleanArgumentMarshaler implements ArgumentMarshaler<Boolean> {
  private Boolean booleanValue = false;

  @Override
  public void setValue(final Optional<String> arg) throws ArgsException {
    booleanValue = true;
  }

  @Override
  public boolean needsValue() {
    return false;
  }

  @Override
  public Boolean getValue() {
    return booleanValue;
  }

}
