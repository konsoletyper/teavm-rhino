package org.teavm.examples.rhino;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.ScriptRuntime;
import org.mozilla.javascript.Scriptable;
import org.teavm.interop.Address;
import org.teavm.interop.Import;
import org.teavm.interop.Structure;
import org.teavm.interop.c.Include;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class CMain {
  public static void main(String[] args) {
    if (args.length != 1) {
      System.err.println("Single parameter expected");
      return;
    }


    try {
      String script = readFile(args[0]);
      if (script == null) {
        return;
      }

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

  private static String readFile(String fileName) {
    CFile file = fopen(fileName, "r");
    if (file == null) {
      System.err.println("Could not open file " + fileName);
      return null;
    }

    byte[] data = new byte[4096];
    int sz = 0;
    while (true) {
      int toRead = data.length - sz;
      int bytesRead = fread(Address.ofData(data).add(sz), 1, toRead, file);
      if (bytesRead < toRead) {
        if (feof(file)) {
          sz += bytesRead;
          break;
        } else {
          System.err.println("Error occurred reading file " + fileName);
          return null;
        }
      }
      sz += bytesRead;
      data = Arrays.copyOf(data, data.length * 2);
    }

    fclose(file);

    return new String(data, 0, sz, StandardCharsets.UTF_8);
  }

  private static class CFile extends Structure {
  }

  @Import(name = "fopen")
  @Include("stdio.h")
  private static native CFile fopen(String fileName, String mode);

  @Import(name = "fclose")
  @Include("stdio.h")
  private static native int fclose(CFile file);

  @Import(name = "fread")
  @Include("stdio.h")
  private static native int fread(Address buf, int size, int count, CFile file);

  @Import(name = "feof")
  @Include("stdio.h")
  private static native boolean feof(CFile file);
}
