/**
 * MIT License
 *
 * Copyright (c) 2017 Yurii Dubinka
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"),
 * to deal in the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom
 * the Software is  furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
 * ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE
 * OR OTHER DEALINGS IN THE SOFTWARE.
 */
package org.dgroup.dockertest.hamcrest;

import java.util.Collection;
import org.cactoos.BiProc;
import org.cactoos.Func;
import org.cactoos.list.Mapped;
import org.dgroup.dockertest.text.Joined;
import org.hamcrest.Description;

/**
 * Describe collection of items in `hamcrest` terms.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @param <T> Type of item.
 * @since 1.0
 */
public final class Describe<T> implements BiProc<Collection<T>, Description> {

    /**
     * Function to map the item to the string.
     */
    private final Func<T, String> fnc;

    /**
     * Ctor.
     */
    public Describe() {
        this(Object::toString);
    }

    /**
     * Ctor.
     * @param fnc Function to map item to the string.
     */
    public Describe(final Func<T, String> fnc) {
        this.fnc = fnc;
    }

    @Override
    public void exec(final Collection<T> items, final Description dsc) {
        dsc.appendValue(
            new Joined(
                new Mapped<>(this.fnc, items), ", "
            ).text()
        );
    }

}
