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
package com.github.dgroup.dockertest.test.output.std;

import com.github.dgroup.dockertest.scalar.If;
import com.github.dgroup.dockertest.test.outcome.TestOutcome;
import com.github.dgroup.dockertest.test.outcome.TestingOutcome;
import com.github.dgroup.dockertest.text.Text;
import com.github.dgroup.dockertest.text.highlighted.GreenText;
import com.github.dgroup.dockertest.text.highlighted.RedText;
import java.io.PrintStream;
import java.util.Objects;
import org.cactoos.Proc;
import org.cactoos.iterable.IterableOf;
import org.cactoos.list.Joined;
import org.cactoos.list.ListOf;
import org.cactoos.list.Mapped;
import org.cactoos.scalar.And;
import org.cactoos.scalar.UncheckedScalar;

/**
 * Standard output for printing app progress and testing results.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 1.0
 * @checkstyle ClassDataAbstractionCouplingCheck (200 lines)
 */
public final class StdOutputOf implements StdOutput {

    /**
     * Standard indent from left side of screen.
     */
    private final String indent;
    /**
     * Standard output.
     */
    private final PrintStream out;

    /**
     * Ctor.
     */
    public StdOutputOf() {
        this(System.out, "    ");
    }

    /**
     * Ctor.
     * @param out Instance for print procedure.
     * @param indent Default indent from left side of screen.
     */
    public StdOutputOf(final PrintStream out, final String indent) {
        this.out = out;
        this.indent = indent;
    }

    @Override
    public void print(final TestingOutcome outcome) {
        this.printTestingStatus(
            new UncheckedScalar<>(
                new And(
                    (Proc<TestOutcome>) this::print,
                    outcome
                )
            ).value() && outcome.successful()
        );
    }

    @Override
    public void print(final Text msg) {
        this.print(msg.text());
    }

    @Override
    public void print(final String msg) {
        this.print(new ListOf<>(msg));
    }

    @Override
    public void print(final String msg, final Exception exp) {
        this.print(
            new Joined<>(
                new ListOf<>(msg),
                new Mapped<>(
                    StackTraceElement::toString,
                    new IterableOf<>(exp.getStackTrace())
                )
            )
        );
    }

    @Override
    public void print(final Iterable<String> messages) {
        for (final String msg : messages) {
            this.out.printf("%s%s%n", this.indent, msg);
        }
    }

    @Override
    @SuppressWarnings("PMD.OnlyOneReturn")
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        final StdOutputOf that = (StdOutputOf) other;
        return Objects.equals(this.indent, that.indent)
            && Objects.equals(this.out, that.out);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.indent, this.out);
    }

    /**
     * Print testing results based on status.
     * @param status Of testing.
     */
    private void printTestingStatus(final boolean status) {
        this.out.println();
        this.print(
            new If<Text>(
                () -> status,
                () -> new GreenText("Testing successful."),
                () -> new RedText("Testing failed.")
            ).value().text()
        );
        this.out.println();
    }

    /**
     * Print the test details.
     * @param outcome The single test result.
     */
    private void print(final TestOutcome outcome) {
        this.print(
            new If<>(
                outcome.successful(),
                new MsgPassed(outcome),
                new MsgFailed(outcome)
            ).value()
        );
    }

}
