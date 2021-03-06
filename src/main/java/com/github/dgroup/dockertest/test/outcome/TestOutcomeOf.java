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
package com.github.dgroup.dockertest.test.outcome;

import com.github.dgroup.dockertest.yml.tag.UncheckedYmlTagTest;
import com.github.dgroup.dockertest.yml.tag.YmlTagOutputPredicate;
import com.github.dgroup.dockertest.yml.tag.YmlTagTest;
import java.util.Collection;
import org.cactoos.Scalar;
import org.cactoos.scalar.UncheckedScalar;

/**
 * Default implementation of single test result.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 1.0
 */
public final class TestOutcomeOf implements TestOutcome {

    /**
     * The original test.
     */
    private final UncheckedYmlTagTest test;
    /**
     * Status of test scenario.
     */
    private final Collection<YmlTagOutputPredicate> failed;
    /**
     * The message/details regarding testing scenario.
     */
    private final UncheckedScalar<String> raw;

    /**
     * Ctor.
     * @param test The test to be executed within docker container.
     * @param raw The raw output from docker container.
     * @param failed The failed output predicates.
     */
    public TestOutcomeOf(final YmlTagTest test, final String raw,
        final Collection<YmlTagOutputPredicate> failed) {
        this(test, () -> raw, failed);
    }

    /**
     * Ctor.
     * @param test The test to be executed within docker container.
     * @param raw The raw output from docker container.
     * @param failed The failed output predicates.
     */
    public TestOutcomeOf(final YmlTagTest test, final Scalar<String> raw,
        final Collection<YmlTagOutputPredicate> failed) {
        this.test = new UncheckedYmlTagTest(test);
        this.raw = new UncheckedScalar<>(raw);
        this.failed = failed;
    }

    @Override
    public boolean successful() {
        return this.failedConditions().isEmpty();
    }

    @Override
    public String scenario() {
        return this.test.assume();
    }

    @Override
    public String cmd() {
        return this.test.cmd();
    }

    @Override
    public String rawOutput() {
        return this.raw.value();
    }

    @Override
    public Collection<YmlTagOutputPredicate> expectedConditions() {
        return this.test.output();
    }

    @Override
    public Collection<YmlTagOutputPredicate> failedConditions() {
        return this.failed;
    }

}
