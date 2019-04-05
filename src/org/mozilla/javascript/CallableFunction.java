package org.mozilla.javascript;

public class CallableFunction extends BaseFunction {
  private Callable callable;

  public CallableFunction(Callable callable) {
    this.callable = callable;
  }

  @Override
  public Object call(Context cx, Scriptable scope, Scriptable thisObj, Object[] args) {
    return callable.call(cx, scope, thisObj, args);
  }
}
