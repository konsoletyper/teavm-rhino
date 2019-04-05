/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.mozilla.javascript.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mozilla.javascript.*;

/**
 * @author André Bargull
 *
 */
public class Bug685403Test {
    private Context cx;
    private ScriptableObject scope;

    @Before
    public void setUp() {
        cx = Context.enter();
        cx.setOptimizationLevel(-1);
        scope = cx.initStandardObjects();
    }

    @After
    public void tearDown() {
        Context.exit();
    }

    public static Object continuation(Context cx) {
        ContinuationPending pending = cx.captureContinuation();
        throw pending;
    }

    @Test
    public void test() {
        String source = "var state = '';";
        source += "function A(){state += 'A'}";
        source += "function B(){state += 'B'}";
        source += "function C(){state += 'C'}";
        source += "try { A(); continuation(); B() } finally { C() }";
        source += "state";

        scope.defineProperty("continuation", (Callable) (cx, scope, thisObj, args) -> continuation(cx),
            ScriptableObject.DONTENUM);

        Object state = null;
        Script script = cx.compileString(source, "", 1, null);
        try {
            cx.executeScriptWithContinuations(script, scope);
            fail("expected ContinuationPending exception");
        } catch (ContinuationPending pending) {
            state = cx.resumeContinuation(pending.getContinuation(), scope, "");
        }
        assertEquals("ABC", state);
    }

}
