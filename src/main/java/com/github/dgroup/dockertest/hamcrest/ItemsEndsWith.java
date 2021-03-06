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
package com.github.dgroup.dockertest.hamcrest;

import com.github.dgroup.dockertest.iterator.Duplex;
import java.util.Collection;
import org.cactoos.Scalar;
import org.cactoos.iterator.LengthOf;
import org.cactoos.list.ListOf;

/**
 * Hamcrest matcher to compare two collections of {@link String} and ensure that
 *  the elements from collection A ends with elements from collection B.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 1.0
 */
public final class ItemsEndsWith extends HamcrestCollectionEnvelope<String> {

    /**
     * Ctor.
     * @param expected The expected ending of the actual elements.
     */
    public ItemsEndsWith(final String... expected) {
        this(() -> new ListOf<>(expected));
    }

    /**
     * Ctor.
     * @param expected The expected ending of the actual elements.
     * @checkstyle IndentationCheck (10 lines)
     */
    public ItemsEndsWith(final Scalar<Collection<String>> expected) {
        super(
            (exp, act) -> new LengthOf(
                new Duplex<>(
                    (eval, aval) -> aval.endsWith(eval),
                    exp, act
                )
            ).value() > 0,
            expected
        );
    }

}
