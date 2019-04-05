package org.teavm.examples.rhino;

import org.mozilla.javascript.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

public class Main {
  public static void main(String[] args) {
    try {
      String script = readFile(args[0]);

      Context context = Context.enter();
      context.setOptimizationLevel(-1);
      context.setLanguageVersion(Context.VERSION_ES6);

      Scriptable scope = ScriptRuntime.initSafeStandardObjects(context, null, false);
      Host.installNonJs(scope);
      Host.installCommon(scope);

      context.evaluateString(scope, script, args[0], 1, null);
    } catch (Throwable e) {
      e.printStackTrace();
    }
  }

  private static String readFile(String name) throws IOException {
    StringBuilder sb = new StringBuilder();
    char[] buf = new char[2048];
    try (Reader reader = new InputStreamReader(new FileInputStream(name), StandardCharsets.UTF_8)) {
      while (true) {
        int charsRead = reader.read(buf);
        if (charsRead == -1) {
          break;
        }
        sb.append(buf, 0, charsRead);
      }
    }
    return sb.toString();
  }
}
