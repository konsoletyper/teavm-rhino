package org.mozilla.javascript.tests;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

import org.junit.Ignore;
import org.junit.Test;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

/**
 *
 * @author hatanaka
 */
public class WrapFactoryTest {
  /** javascript code */
  private static String script = "this.result = typeof test;";

  /**
   * for your reference
   * default setting (javaPrimitiveWrap = true)
   */
  @Test
  public void primitiveWrapTrue() {
    test(true, "text", "string");
    test(true, Boolean.FALSE, "boolean");
    test(true, new Integer(1), "number");
    test(true, new Long(2L), "number");
    test(true, new BigInteger("3"), "number");
    test(true, new BigDecimal("4.0"), "number");
  }

  /**
   * javaPrimitiveWrap = false
   */
  @Test
  public void primitiveWrapFalse() {
    test(false, "text", "string"); // Great! I want to do this.
    test(false, Boolean.FALSE, "boolean");
    test(false, new Integer(1), "number");
    test(false, new Long(2L), "number");

    // I want to treat BigInteger / BigDecimal as BigInteger / BigDecimal. But fails.
    test(false, new BigInteger("30"), "number");
    test(false, new BigDecimal("4.0"), "number");

    // This is the best. I want not to convert to number.
    //test(false, new BigInteger("30"), "object", "object", "object");
    //test(false, new BigDecimal("4.0"), "object", "object", "object");
  }

  private void test(boolean javaPrimitiveWrap, Object object, String result) {
    Context cx = Context.enter();
    try {
      cx.getWrapFactory().setJavaPrimitiveWrap(javaPrimitiveWrap);
      Scriptable scope = cx.initStandardObjects(cx.initStandardObjects());

      //execute script
      ScriptableObject.putProperty(scope, "test", object);
      cx.evaluateString(scope, script, "", 1, null);

      //evaluate result
      assertEquals(result, ScriptableObject.getProperty(scope, "result"));
    } finally {
      Context.exit();
    }
  }
}