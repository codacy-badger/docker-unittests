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
package org.dgroup.dockertest.test;

import org.dgroup.dockertest.cmd.Arg;
import org.dgroup.dockertest.docker.DefaultDockerContainer;
import org.dgroup.dockertest.docker.DockerContainer;
import org.dgroup.dockertest.docker.StatelessDockerContainerCommand;
import org.dgroup.dockertest.yml.YmlTagTest;

/**
 * .
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 0.1.0
 */
public final class DefaultTestBasedOnYml implements Test {

    private final YmlTagTest test;
    private final DockerContainer container;

    public DefaultTestBasedOnYml(final Arg image, final YmlTagTest test) {
        this.test = test;
        this.container = new DefaultDockerContainer(
            new StatelessDockerContainerCommand(
                image.value(), test.dockerCmdAsArray()
            )
        );
    }

    @Override
    public TestingOutcome execute() {
        return new DefaultTestingOutcome(
            this.test.assume(),
            this.test.cmd(),
            this.container.run().asText(),
            this.test.output()
        );
    }
}
