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
package com.rabbitframework.security.io;

import com.rabbitframework.commons.utils.ClassUtils;
import com.rabbitframework.security.util.UnknownClassException;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;

/**
 * Enables correct ClassLoader lookup in various environments (e.g. JEE Servers, etc).
 *
 * @since 1.2
 * @see <a href="https://issues.apache.org/jira/browse/SHIRO-334">SHIRO-334</a>
 */
public class ClassResolvingObjectInputStream extends ObjectInputStream {

    public ClassResolvingObjectInputStream(InputStream inputStream) throws IOException {
        super(inputStream);
    }

    @Override
    protected Class<?> resolveClass(ObjectStreamClass osc) throws IOException, ClassNotFoundException {
        try {
            return ClassUtils.classForName(osc.getName());
        } catch (UnknownClassException e) {
            throw new ClassNotFoundException("Unable to load ObjectStreamClass [" + osc + "]: ", e);
        }
    }
}