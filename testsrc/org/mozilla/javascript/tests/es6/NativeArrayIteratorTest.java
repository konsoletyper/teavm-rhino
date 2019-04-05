/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.mozilla.javascript.tests.es6;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.NativeArrayIterator;
import org.mozilla.javascript.NativeArrayIterator.ARRAY_ITERATOR_TYPE;
import org.mozilla.javascript.Scriptable;

import java.io.*;

import static org.junit.Assert.assertEquals;

public class NativeArrayIteratorTest
{
    private Context cx;
    private Scriptable root;

    @Before
    public void init()
    {
        cx = Context.enter();
        cx.setLanguageVersion(Context.VERSION_ES6);
        cx.setGeneratingDebug(true);

        root = cx.newObject(cx.initStandardObjects());
    }

    @After
    public void terminate()
    {
        Context.exit();
    }

    /**
     * Test serialization of an empty object.
     */
    @Test
    public void testSerialization()
        throws IOException, ClassNotFoundException {

        NativeArrayIterator iter = new NativeArrayIterator(root, null, ARRAY_ITERATOR_TYPE.VALUES);

        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
                ObjectOutputStream oout = new ObjectOutputStream(bos)) {
            oout.writeObject(iter);

            try (ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
                    ObjectInputStream oin = new ObjectInputStream(bis)) {
                NativeArrayIterator result = (NativeArrayIterator)oin.readObject();
                assertEquals(0, result.getIds().length);
            }
        }
    }
}
