package org.mozilla.javascript.tests;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mozilla.javascript.*;

import java.io.FileReader;
import java.io.IOException;

import static org.junit.Assert.assertFalse;

public class StackTraceExtensionTest
{
    @BeforeClass
    public static void init()
    {
        RhinoException.setStackStyle(StackStyle.V8);
    }

    @AfterClass
    public static void terminate()
    {
        RhinoException.setStackStyle(StackStyle.RHINO);
    }

    private void testTraces(int opt)
    {
        final ContextFactory factory = new ContextFactory() {
            @Override
            protected boolean hasFeature(Context cx, int featureIndex)
            {
                switch (featureIndex) {
                case Context.FEATURE_LOCATION_INFORMATION_IN_ERROR:
                    return true;
                default:
                    return super.hasFeature(cx, featureIndex);
                }
            }
        };

        Context cx = factory.enterContext();
        try {
            cx.setLanguageVersion(Context.VERSION_1_8);
            cx.setOptimizationLevel(opt);
            cx.setGeneratingDebug(true);

            Scriptable global = TestScopeUtil.createScope(cx);
            Scriptable root = cx.newObject(global);

            FileReader rdr = new FileReader("testsrc/jstests/extensions/stack-traces.js");

            try {
                cx.evaluateReader(root, rdr, "stack-traces.js", 1, null);
            } finally {
                rdr.close();
            }
        } catch (IOException ioe) {
            assertFalse("I/O Error: " + ioe, true);
        } finally {
            Context.exit();
        }
    }

    @Test
    public void testStackTrace0()
    {
        testTraces(0);
    }

    @Test
    public void testStackTrace9()
    {
        testTraces(9);
    }

    @Test
    public void testStackTraceInt()
    {
        testTraces(-1);
    }
}
