/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.rabbitframework.security.subject;

import com.rabbitframework.security.SecurityException;

/**
 * Exception wrapping any potential checked exception thrown when a {@code Subject} executes a
 * {@link java.util.concurrent.Callable}.  This is a nicer alternative than forcing calling code to catch
 * a normal checked {@code Exception} when it may not be necessary.
 * <p/>
 * If thrown, the causing exception will always be accessible via the {@link #getCause() getCause()} method.
 *
 * @since 1.0
 */
public class ExecutionException extends SecurityException {

    public ExecutionException(Throwable cause) {
        super(cause);
    }

    public ExecutionException(String message, Throwable cause) {
        super(message, cause);
    }
}
