/* -*- Mode: java; tab-width: 8; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.mozilla.javascript.jdk18;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.VMBridge;
import org.mozilla.javascript.Wrapper;

import java.util.Iterator;

public class VMBridge_jdk18 extends VMBridge
{
    private Object[] contextLocal;

    @Override
    protected Object getThreadContextHelper()
    {
        // To make subsequent batch calls to getContext/setContext faster
        // associate permanently one element array with contextLocal
        // so getContext/setContext would need just to read/write the first
        // array element.
        // Note that it is necessary to use Object[], not Context[] to allow
        // garbage collection of Rhino classes. For details see comments
        // by Attila Szegedi in
        // https://bugzilla.mozilla.org/show_bug.cgi?id=281067#c5

        Object[] storage = contextLocal;
        if (storage == null) {
            storage = new Object[1];
            contextLocal = storage;
        }
        return storage;
    }

    @Override
    protected Context getContext(Object contextHelper)
    {
        Object[] storage = (Object[])contextHelper;
        return (Context)storage[0];
    }

    @Override
    protected void setContext(Object contextHelper, Context cx)
    {
        Object[] storage = (Object[])contextHelper;
        storage[0] = cx;
    }

    /**
     * If "obj" is a java.util.Iterator or a java.lang.Iterable, return a
     * wrapping as a JavaScript Iterator. Otherwise, return null.
     * This method is in VMBridge since Iterable is a JDK 1.5 addition.
     */
    @Override
    protected Iterator<?> getJavaIterator(Context cx, Scriptable scope, Object obj) {
        if (obj instanceof Wrapper) {
            Object unwrapped = ((Wrapper) obj).unwrap();
            Iterator<?> iterator = null;
            if (unwrapped instanceof Iterator)
                iterator = (Iterator<?>) unwrapped;
            if (unwrapped instanceof Iterable)
                iterator = ((Iterable<?>)unwrapped).iterator();
            return iterator;
        }
        return null;
    }
}
