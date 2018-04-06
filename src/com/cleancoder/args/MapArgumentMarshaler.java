package com.cleancoder.args;

import java.util.*;

import static com.cleancoder.args.ArgsException.ErrorCode.*;

public class MapArgumentMarshaler implements ArgumentMarshaler<Map<String, String>>{
  private Map<String, String> map = new HashMap<>();

  @Override
  public void setValue(Optional<String> arg) throws ArgsException {
    if (!arg.isPresent()) {
      throw new ArgsException(MISSING_MAP);
    }
    String[] mapEntries = arg.get().split(",");
    for (String entry : mapEntries) {
      String[] entryComponents = entry.split(":");
      if (entryComponents.length != 2)
        throw new ArgsException(MALFORMED_MAP);
      map.put(entryComponents[0], entryComponents[1]);
    }
  }

  @Override
  public Map<String, String> getValue() {
    return map;
  }

}
