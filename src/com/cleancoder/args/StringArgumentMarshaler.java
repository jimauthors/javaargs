package com.cleancoder.args;

import java.util.Optional;

import static com.cleancoder.args.ArgsException.ErrorCode.MISSING_STRING;

public class StringArgumentMarshaler implements ArgumentMarshaler<String> {
  private String stringValue = "";

  @Override
  public void setValue(final Optional<String> arg) throws ArgsException {
    if (!arg.isPresent()) {
      throw new ArgsException(MISSING_STRING);
    }
    stringValue = arg.get();
  }

  @Override
  public String getValue() {
    return stringValue;
  }

}
