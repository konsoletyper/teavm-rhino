package org.teavm.examples.rhino;

import org.mozilla.javascript.*;
import org.teavm.jso.dom.html.HTMLButtonElement;
import org.teavm.jso.dom.html.HTMLDocument;
import org.teavm.jso.dom.html.HTMLElement;
import org.teavm.jso.dom.html.HTMLTextAreaElement;

public class JsMain {
  public static void main(String[] args) {
    Context context = Context.enter();
    context.setOptimizationLevel(-1);
    context.setLanguageVersion(Context.VERSION_ES6);

    HTMLDocument document = HTMLDocument.current();
    HTMLElement outputElem = document.getElementById("output").cast();

    Scriptable scope = ScriptRuntime.initSafeStandardObjects(context, null, false);
    scope.put("log", scope, new BaseFunction() {
      @Override
      public Object call(Context cx, Scriptable scope, Scriptable thisObj, Object[] args) {
        outputElem.appendChild(document.createTextNode(Context.toString(args[0]) + "\n"));
        return Undefined.instance;
      }
    });
    Host.installCommon(scope);

    HTMLTextAreaElement scriptElem = document.getElementById("script").cast();
    HTMLButtonElement evaluateElem = document.getElementById("evaluate").cast();

    evaluateElem.addEventListener("click", event -> {
      outputElem.clear();
      String script = scriptElem.getValue();
      try {
        context.evaluateString(scope, script, "script.js", 1, null);
      } catch (Throwable e) {
        e.printStackTrace();
      }
    });
  }
}
