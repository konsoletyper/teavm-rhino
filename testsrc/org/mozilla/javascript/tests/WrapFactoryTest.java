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
  @Ignore
  public void primitiveWrapTrue() {
    test(true, "text", "string", "object", "object");
    test(true, Boolean.FALSE, "boolean", "object", "object");
    test(true, new Integer(1), "number", "object", "object");
    test(true, new Long(2L), "number", "object", "object");
    test(true, new BigInteger("3"), "number", "object", "object");
    test(true, new BigDecimal("4.0"), "number", "object", "object");
  }

  /**
   * javaPrimitiveWrap = false
   */
  @Test
  @Ignore
  public void primitiveWrapFalse() {
    test(false, "text", "string", "string", "string"); // Great! I want to do this.
    test(false, Boolean.FALSE, "boolean", "boolean", "boolean");
    test(false, new Integer(1), "number", "number", "number");
    test(false, new Long(2L), "number", "number", "number");

    // I want to treat BigInteger / BigDecimal as BigInteger / BigDecimal. But fails.
    test(false, new BigInteger("30"), "number", "object", "object");
    test(false, new BigDecimal("4.0"), "number", "object", "object");

    // This is the best. I want not to convert to number.
    //test(false, new BigInteger("30"), "object", "object", "object");
    //test(false, new BigDecimal("4.0"), "object", "object", "object");
  }

  /**
   * @param javaPrimitiveWrap
   * @param object
   * @param result typeof value
   * @param mapResult typeof map value
   * @param getResult typeof getter value
   */
  private void test(boolean javaPrimitiveWrap, Object object, String result,
      String mapResult, String getResult) {
    Context cx = Context.enter();
    try {
      cx.getWrapFactory().setJavaPrimitiveWrap(javaPrimitiveWrap);
      Scriptable scope = cx.initStandardObjects(cx.initStandardObjects());

      //register object
      Map<String, Object> map = new LinkedHashMap<>();
      map.put("test", object);

      //execute script
      cx.evaluateString(scope, script, "", 1, null);

      //evaluate result
      assertEquals(result, ScriptableObject.getProperty(scope, "result"));
    } finally {
      Context.exit();
    }
  }
}