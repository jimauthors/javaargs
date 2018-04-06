package com.cleancoder.args;

import static com.cleancoder.args.ArgsException.ErrorCode.*;

import java.util.*;

public class StringArrayArgumentMarshaler implements ArgumentMarshaler<String[]> {
  private  List<String> strings = new ArrayList<>();

  @Override
  public void setValue(final Optional<String> arg) throws ArgsException {
      if (!arg.isPresent()) {
        throw new ArgsException(MISSING_STRING);
      }
      strings.add(arg.get());
  }

  @Override
  public String[] getValue() {
    return strings.toArray(new String[0]);
  }

}
