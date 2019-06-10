/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.mozilla.javascript.tests;

import java.io.InputStreamReader;

import org.mozilla.javascript.*;

import junit.framework.TestCase;

public class Issue176Test extends TestCase {


    Context cx;
    Scriptable scope;

    public void ignoreThrowing() throws Exception {
        cx = Context.enter();
        try {
            Script script = cx.compileReader(new InputStreamReader(
                    Bug482203Test.class.getResourceAsStream("Issue176.js")),
                    "Issue176.js", 1, null);
            scope = cx.initStandardObjects();
            scope.put("host", scope, this);
            script.exec(cx, scope); // calls our methods
        } finally {
            Context.exit();
        }
    }


    public class Host extends ScriptableObject {
        public Host() {
            defineProperty(
                "throwError",
                new CallableFunction((cx, scope, thisObj, args) -> {
                    throwError(args[0].toString());
                    return Undefined.instance;
                }),
                READONLY
            );
            defineProperty(
                "throwCustomError",
                new CallableFunction((cx, scope, thisObj, args) -> {
                    throwCustomError(args[0].toString(), args[1].toString());
                    return Undefined.instance;
                }),
                READONLY
            );
        }

        @Override
        public String getClassName() {
            return "";
        }

        public void throwError(String msg) {
            throw ScriptRuntime.throwError(cx, scope, msg);
        }


        public void throwCustomError(String constr, String msg) {
            throw ScriptRuntime.throwCustomError(cx, scope, constr, msg);
        }
    }

}
