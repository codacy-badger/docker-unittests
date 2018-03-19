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
package org.dgroup.dockertest.yml.tag;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.cactoos.collection.Filtered;
import org.cactoos.list.ListOf;
import org.dgroup.dockertest.yml.IllegalYmlFormatException;

/**
 * Represents single yml tag in *.yml file.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 1.0
 * @todo #/DEV Move defaults methods outside of interface.
 */
public interface YmlTag {

    /**
     * Represent tag name as string.
     * @return Name.
     */
    String name();

    /**
     * Represent tag value as string.
     * @return Value.
     * @throws IllegalYmlFormatException in case if tag is null/missing
     *  or has no value.
     */
    default String asString() throws IllegalYmlFormatException {
        return this.asObject().toString();
    }

    /**
     * Represent tag value as list.
     * @return List.
     * @throws IllegalYmlFormatException in case if tag is null/missing
     *  or has no value.
     */
    @SuppressWarnings("unchecked")
    default List<Object> asList() throws IllegalYmlFormatException {
        return new ListOf<>(
            new Filtered<>(
                Objects::nonNull,
                (List<Object>) this.asObject()
            )
        );
    }

    /**
     * Represent tag value as map.
     * @return Map.
     * @throws IllegalYmlFormatException in case if tag is null/missing
     *  or has no value.
     */
    @SuppressWarnings("unchecked")
    default Map<Object, Object> asMap() throws IllegalYmlFormatException {
        return (Map<Object, Object>) this.asObject();
    }

    /**
     * YML tag value.
     * @return Non-null tag value.
     * @throws IllegalYmlFormatException in case if tag is null/missing
     *  or has no value.
     */
    Object asObject() throws IllegalYmlFormatException;

    /**
     * Check that current tag has children tags with values.
     * @param child Tag name.
     * @throws IllegalYmlFormatException in case if child tag
     *  is null/missing or has no value.
     */
    default void assertThatTagContain(final String child)
        throws IllegalYmlFormatException {
        if (this.asMap().get(child) == null) {
            throw new IllegalYmlFormatException(this, child);
        }
    }

}
