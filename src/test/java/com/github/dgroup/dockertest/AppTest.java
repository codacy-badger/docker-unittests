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
package com.github.dgroup.dockertest;

import com.github.dgroup.dockertest.hamcrest.HasItems;
import com.github.dgroup.dockertest.hamcrest.ItemsEndsWith;
import com.github.dgroup.dockertest.test.output.std.StdOutput;
import com.github.dgroup.dockertest.text.Text;
import com.github.dgroup.dockertest.text.TextOf;
import java.io.File;
import java.util.ArrayList;
import org.cactoos.list.ListOf;
import org.hamcrest.MatcherAssert;
import org.hamcrest.core.IsEqual;
import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for class {@link App}.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 1.0
 * @checkstyle MagicNumberCheck (500 lines)
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle ClassDataAbstractionCouplingCheck (500 lines)
 */
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public final class AppTest {

    @Test
    public void run() throws AppException {
        new Assume().that(new DockerWasInstalled());
        final Text path = new TextOf("docs%simage-tests.yml", File.separator);
        final StdOutput.Fake std = new StdOutput.Fake(new ArrayList<>(10));
        std.print(new TextOf("File: %s.", path));
        new App(
            new ListOf<>(
                "-f", path.text(),
                "-i", "openjdk:9.0.1-11"
            ),
            std
        ).start();
        MatcherAssert.assertThat(
            std.details(),
            new HasItems<>("Testing successfully completed.")
        );
    }

    @Test
    public void dueToIllegalYmlFormatException() {
        final StdOutput std = new StdOutput.Fake(new ArrayList<>(10));
        final String path = new YmlResource("tag-tests-is-missing.yml").path();
        std.print(new TextOf("File: %s.", path));
        try {
            new App(
                new ListOf<>(
                    "-f", path,
                    "-i", "openjdk:9.0.1-11"
                ),
                std
            ).start();
            Assert.fail("Exception is expected here.");
        } catch (final AppException exp) {
            MatcherAssert.assertThat(
                exp.exitCode(), new IsEqual<>(-2)
            );
            MatcherAssert.assertThat(
                exp.message(),
                new ItemsEndsWith(
                    "tag-tests-is-missing.yml` has the wrong format:",
                    "mapping values are not allowed here"
                )
            );
        }
    }

}
