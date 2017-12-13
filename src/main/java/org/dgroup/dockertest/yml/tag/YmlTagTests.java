/**
 * MIT License
 *
 * Copyright (c) 2017 Yurii Dubinka
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package org.dgroup.dockertest.yml.tag;

import java.util.Iterator;
import java.util.Map;
import org.cactoos.iterator.Mapped;

/**
 * Represents yml tag {@code /tests}.
 * Tag can contain list of {@link YmlTagTest}.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 0.1.0
 */
public class YmlTagTests implements Iterable<YmlTagTest> {

    /**
     * Single yml tag as object.
     */
    private final YmlTag tag;

    /**
     * Ctor.
     *
     * @param tree Yml object tree loaded from *.yml file with tests.
     */
    public YmlTagTests(final Map<String, Object> tree) {
        this.tag = new YmlTag(tree, "tests");
    }

    @Override
    public final Iterator<YmlTagTest> iterator() {
        this.tag.verifyExistence();
        return new Mapped<>(
            YmlTagTest::new,
            new Mapped<>(
                test -> (Map<String, Object>) test,
                this.tag.list().iterator()
            )
        );
    }

}