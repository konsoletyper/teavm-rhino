package org.mozilla.javascript.tests;

import org.mozilla.javascript.*;
import org.mozilla.javascript.Evaluator;

import java.io.*;
import java.nio.charset.StandardCharsets;

public final class TestScopeUtil {
  private TestScopeUtil() {
  }

  public static Scriptable createScope(Context context) {
    ScriptableObject result = context.initSafeStandardObjects();

    result.defineProperty("load", new CallableFunction((cx, scope, thisObj, args) -> {
      for (Object arg : args) {
        evalFile(cx, scope, arg.toString());
      }
      return Undefined.instance;
    }), ScriptableObject.READONLY);

    result.defineProperty("print", new CallableFunction((cx, scope, thisObj, args) -> {
      for (Object arg : args) {
        System.out.println(arg);
      }
      return Undefined.instance;
    }), ScriptableObject.READONLY);

    return result;
  }

  private static void evalFile(Context context, Scriptable scope, String fileName) {
    StringBuilder sb = new StringBuilder();

    try (BufferedReader reader = new BufferedReader(new InputStreamReader(
        new FileInputStream(fileName), StandardCharsets.UTF_8))) {
      while (true) {
        String line = reader.readLine();
        if (line == null) {
          break;
        }
        sb.append(line).append("\n");
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    ErrorReporter reporter;
    reporter = DefaultErrorReporter.forEval(context.getErrorReporter());

    Evaluator evaluator = Context.createInterpreter();
    if (evaluator == null) {
      throw new JavaScriptException("Interpreter not present", fileName, 0);
    }

    Script script = context.compileString(sb.toString(), evaluator, reporter, fileName, 1);
    script.exec(context, scope);
  }
}
