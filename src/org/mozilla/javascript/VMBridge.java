/* -*- Mode: java; tab-width: 8; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

// API class

package org.mozilla.javascript;

import org.mozilla.javascript.jdk18.VMBridge_jdk18;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;
import java.util.Iterator;

public abstract class VMBridge
{

    static final VMBridge instance = new VMBridge_jdk18();

    /**
     * Return a helper object to optimize {@link Context} access.
     * <p>
     * The runtime will pass the resulting helper object to the subsequent
     * calls to {@link #getContext(Object contextHelper)} and
     * {@link #setContext(Object contextHelper, Context cx)} methods.
     * In this way the implementation can use the helper to cache
     * information about current thread to make {@link Context} access faster.
     */
    protected abstract Object getThreadContextHelper();

    /**
     * Get {@link Context} instance associated with the current thread
     * or null if none.
     *
     * @param contextHelper The result of {@link #getThreadContextHelper()}
     *                      called from the current thread.
     */
    protected abstract Context getContext(Object contextHelper);

    /**
     * Associate {@link Context} instance with the current thread or remove
     * the current association if <tt>cx</tt> is null.
     *
     * @param contextHelper The result of {@link #getThreadContextHelper()}
     *                      called from the current thread.
     */
    protected abstract void setContext(Object contextHelper, Context cx);

    /**
     * If "obj" is a java.util.Iterator or a java.lang.Iterable, return a
     * wrapping as a JavaScript Iterator. Otherwise, return null.
     * This method is in VMBridge since Iterable is a JDK 1.5 addition.
     */
    protected abstract Iterator<?> getJavaIterator(Context cx, Scriptable scope, Object obj);
}
