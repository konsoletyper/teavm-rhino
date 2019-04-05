package org.teavm.examples.rhino;

import org.mozilla.javascript.*;

public final class Host {
  private Host() {
  }

  public static void installNonJs(Scriptable scope) {
    scope.put("log", scope, (Callable) (cx, scope1, thisObj, args) -> {
      System.out.println(Context.toString(args[0]));
      return Undefined.instance;
    });
  }

  public static void installCommon(Scriptable scope) {
    long start = System.currentTimeMillis();
    scope.put("time", scope, (Callable) (cx, scope1, thisObj, args) -> (int) (System.currentTimeMillis() - start));
  }
}
