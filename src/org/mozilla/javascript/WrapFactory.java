/* -*- Mode: java; tab-width: 8; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

// API class

package org.mozilla.javascript;

/**
 * Embeddings that wish to provide their own custom wrappings for Java
 * objects may extend this class and call
 * {@link Context#setWrapFactory(WrapFactory)}
 * Once an instance of this class or an extension of this class is enabled
 * for a given context (by calling setWrapFactory on that context), Rhino
 * will call the methods of this class whenever it needs to wrap a value
 * resulting from a call to a Java method or an access to a Java field.
 *
 * @see org.mozilla.javascript.Context#setWrapFactory(WrapFactory)
 * @since 1.5 Release 4
 */
public class WrapFactory
{
    /**
     * Wrap the object.
     * <p>
     * The value returned must be one of
     * <UL>
     * <LI>java.lang.Boolean</LI>
     * <LI>java.lang.String</LI>
     * <LI>java.lang.Number</LI>
     * <LI>org.mozilla.javascript.Scriptable objects</LI>
     * <LI>The value returned by Context.getUndefinedValue()</LI>
     * <LI>null</LI>
     * </UL>
     * @param cx the current Context for this thread
     * @param scope the scope of the executing script
     * @param obj the object to be wrapped. Note it can be null.
     * @param staticType type hint. If security restrictions prevent to wrap
              object based on its class, staticType will be used instead.
     * @return the wrapped value.
     */
    public Object wrap(Context cx, Scriptable scope,
                       Object obj, Class<?> staticType)
    {
        if (obj == null || obj == Undefined.instance
            || obj instanceof Scriptable)
        {
            return obj;
        }
        if (staticType != null && staticType.isPrimitive()) {
            if (staticType == Void.TYPE)
                return Undefined.instance;
            if (staticType == Character.TYPE)
                return Integer.valueOf(((Character) obj).charValue());
            return obj;
        }
        if (!isJavaPrimitiveWrap()) {
            if (obj instanceof String ||
                obj instanceof Boolean ||
                obj instanceof Integer ||
                obj instanceof Short ||
                obj instanceof Long ||
                obj instanceof Float ||
                obj instanceof Double)
            {
                return obj;
            } else if (obj instanceof Character) {
                return String.valueOf(((Character)obj).charValue());
            }
        }
        return obj;
    }

    /**
     * Return <code>false</code> if result of Java method, which is instance of
     * <code>String</code>, <code>Number</code>, <code>Boolean</code> and
     * <code>Character</code>, should be used directly as JavaScript primitive
     * type.
     * By default the method returns true to indicate that instances of
     * <code>String</code>, <code>Number</code>, <code>Boolean</code> and
     * <code>Character</code> should be wrapped as any other Java object and
     * scripts can access any Java method available in these objects.
     * Use {@link #setJavaPrimitiveWrap(boolean)} to change this.
     */
    public final boolean isJavaPrimitiveWrap()
    {
        return javaPrimitiveWrap;
    }

    /**
     * @see #isJavaPrimitiveWrap()
     */
    public final void setJavaPrimitiveWrap(boolean value)
    {
        Context cx = Context.getCurrentContext();
        if (cx != null && cx.isSealed()) {
            Context.onSealedMutation();
        }
        javaPrimitiveWrap = value;
    }

    private boolean javaPrimitiveWrap = true;

}
