/* -*- Mode: java; tab-width: 8; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.mozilla.javascript;

import java.io.Serializable;

/**
 * This class implements the Undefined value in JavaScript.
 */
public class Undefined implements Serializable
{
    static final long serialVersionUID = 9195680630202616767L;

    public static final Object instance = new Undefined();

    private Undefined()
    {
    }

    public Object readResolve()
    {
        return instance;
    }

    @Override
    public boolean equals(Object obj) {
        return isUndefined(obj) || super.equals(obj);
    }

    @Override
    public int hashCode() {
        // All instances of Undefined are equivalent!
        return 0;
    }

    public static final Scriptable SCRIPTABLE_UNDEFINED;

    static {
        SCRIPTABLE_UNDEFINED = new Scriptable() {
            @Override
            public String toString() {
                return "undefined";
            }

            @Override
            public String getClassName() {
                unsupported();
                return null;
            }

            @Override
            public Object get(String name, Scriptable start) {
                unsupported();
                return null;
            }

            @Override
            public Object get(int index, Scriptable start) {
                unsupported();
                return null;
            }

            @Override
            public boolean has(String name, Scriptable start) {
                unsupported();
                return false;
            }

            @Override
            public boolean has(int index, Scriptable start) {
                unsupported();
                return false;
            }

            @Override
            public void put(String name, Scriptable start, Object value) {
                unsupported();
            }

            @Override
            public void put(int index, Scriptable start, Object value) {
                unsupported();
            }

            @Override
            public void delete(String name) {
                unsupported();
            }

            @Override
            public void delete(int index) {
                unsupported();
            }

            @Override
            public Scriptable getPrototype() {
                unsupported();
                return null;
            }

            @Override
            public void setPrototype(Scriptable prototype) {
                unsupported();
            }

            @Override
            public Scriptable getParentScope() {
                unsupported();
                return null;
            }

            @Override
            public void setParentScope(Scriptable parent) {
                unsupported();
            }

            @Override
            public Object[] getIds() {
                unsupported();
                return null;
            }

            @Override
            public Object getDefaultValue(Class<?> hint) {
                unsupported();
                return null;
            }

            @Override
            public boolean hasInstance(Scriptable instance) {
                unsupported();
                return false;
            }

            private void unsupported() {
                throw new UnsupportedOperationException("undefined doesn't support this method");
            }
        };
    }

    public static boolean isUndefined(Object obj)
    {
        return Undefined.instance == obj || Undefined.SCRIPTABLE_UNDEFINED == obj;
    }
}
