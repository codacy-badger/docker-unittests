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
package com.github.dgroup.dockertest.concurrent;

import com.github.dgroup.dockertest.Assume;
import com.github.dgroup.dockertest.DockerWasInstalled;
import com.github.dgroup.dockertest.cmd.Arg;
import com.github.dgroup.dockertest.cmd.ConcurrentTreads;
import com.github.dgroup.dockertest.cmd.TimeoutPerThread;
import com.github.dgroup.dockertest.hamcrest.HasItems;
import com.github.dgroup.dockertest.test.Test.Sleeping;
import com.github.dgroup.dockertest.test.TestsOf;
import com.github.dgroup.dockertest.test.output.std.StdOutput;
import com.github.dgroup.dockertest.text.Text;
import com.github.dgroup.dockertest.text.TextOf;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.cactoos.list.ListOf;
import org.hamcrest.MatcherAssert;

/**
 * Unit tests for class {@link Concurrent}.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 1.0
 * @checkstyle MagicNumberCheck (500 lines)
 * @checkstyle OperatorWrapCheck (500 lines)
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle JavadocVariableCheck (500 lines)
 * @checkstyle RegexpSinglelineCheck (500 lines)
 * @checkstyle StringLiteralsConcatenationCheck (500 lines)
 * @checkstyle ClassDataAbstractionCouplingCheck (500 lines)
 */
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public final class ConcurrentTest {

    @org.junit.Test(timeout = 20 * 1000)
    public void executeConcurrently() throws Exception {
        final StdOutput.Fake out = new StdOutput.Fake(new ArrayList<>(12));
        final List<String> args = new ListOf<>(
            "--threads", "5",
            "--timeout-per-test", "10"
        );
        final Arg<Timeout> tmt = new TimeoutPerThread(args);
        final Arg<Integer> threads = new ConcurrentTreads(args);
        try (Concurrent concurrently = new Concurrent(tmt, threads)) {
            concurrently.execute(
                new Sleeping(new TimeoutOf(5, TimeUnit.SECONDS)),
                new Sleeping(new TimeoutOf(5, TimeUnit.SECONDS)),
                new Sleeping(new TimeoutOf(5, TimeUnit.SECONDS)),
                new Sleeping(new TimeoutOf(5, TimeUnit.SECONDS)),
                new Sleeping(new TimeoutOf(5, TimeUnit.SECONDS)),
                new Sleeping(new TimeoutOf(5, TimeUnit.SECONDS)),
                new Sleeping(new TimeoutOf(5, TimeUnit.SECONDS)),
                new Sleeping(new TimeoutOf(5, TimeUnit.SECONDS)),
                new Sleeping(new TimeoutOf(5, TimeUnit.SECONDS)),
                new Sleeping(new TimeoutOf(5, TimeUnit.SECONDS)),
                new Sleeping(new TimeoutOf(5, TimeUnit.SECONDS)),
                new Sleeping(new TimeoutOf(5, TimeUnit.SECONDS)),
                new Sleeping(new TimeoutOf(5, TimeUnit.SECONDS)),
                new Sleeping(new TimeoutOf(5, TimeUnit.SECONDS)),
                new Sleeping(new TimeoutOf(5, TimeUnit.SECONDS))
            ).report(out);
        }
        MatcherAssert.assertThat(
            "15 tasks (5 seconds each) within 5 threads should take less then" +
                " 20 seconds",
            out.details(),
            new HasItems<>("Testing successfully completed.")
        );
    }

    @org.junit.Test
    public void executeConsequentially() throws Exception {
        new Assume().that(new DockerWasInstalled());
        final Text path = new TextOf("docs%simage-tests.yml", File.separator);
        final StdOutput.Fake out = new StdOutput.Fake(new ArrayList<>(12));
        out.print(new TextOf("File: %s.", path));
        new Concurrent(
            new Arg.Fake<>("-timeout", new TimeoutOf(5, TimeUnit.SECONDS)),
            new Arg.Fake<>("-threads", 1)
        ).execute(
            new TestsOf(
                new Arg.Fake<>("-i", "openjdk:9.0.1-11"),
                new Arg.Fake<>("-f", path.text())
            )
        ).report(out);
        MatcherAssert.assertThat(
            out.details(),
            new HasItems<>("Testing successfully completed.")
        );
    }

}
