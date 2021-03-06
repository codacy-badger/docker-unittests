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
package com.github.dgroup.dockertest.docker;

import com.github.dgroup.dockertest.text.TextOf;
import com.github.dgroup.dockertest.text.highlighted.BlueText;

/**
 * Raise when docker tool can't find an image.
 * Mostly because of wrong image name specified by user.
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @version $Id$
 * @since 1.0
 */
public final class DockerImageNotFoundException extends
    DockerProcessExecutionException {

    /**
     * Ctor.
     * @param image Docker image name.
     * @checkstyle OperatorWrapCheck (20 lines)
     * @checkstyle RegexpSinglelineCheck (20 lines)
     * @checkstyle StringLiteralsConcatenationCheck (20 lines)
     */
    public DockerImageNotFoundException(final String image) {
        super(
            new TextOf(
                "Unable to pull image \"%s\" from the remote repository. " +
                    "Possible reasons:\n" +
                    " - incorrect name (you may verify by shell command " +
                    "\"%s\");\n" +
                    " - the remote repository doesn't exist or " +
                    "network\\firewall connectivity issue;\n" +
                    " - pull operation may require 'docker login'.\n",
                new BlueText(image),
                new BlueText(new TextOf("docker pull %s", image))
            ).text()
        );
    }

}
