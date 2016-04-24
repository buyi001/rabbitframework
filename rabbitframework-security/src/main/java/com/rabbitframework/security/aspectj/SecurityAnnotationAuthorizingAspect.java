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
package com.rabbitframework.security.aspectj;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

/**
 * Aspect that adds a before advice for each invocation of an annotated method.
 *
 */
@Aspect()
public class SecurityAnnotationAuthorizingAspect {

    private static final String pointCupExpression =
            "execution(@org.apache.shiro.authz.annotation.RequiresAuthentication * *(..)) || " +
                    "execution(@com.rabbitframework.security.authz.annotation.RequiresGuest * *(..)) || " +
                    "execution(@com.rabbitframework.security.authz.annotation.RequiresPermissions * *(..)) || " +
                    "execution(@com.rabbitframework.security.authz.annotation.RequiresRoles * *(..)) || " +
                    "execution(@com.rabbitframework.security.authz.annotation.RequiresUser * *(..))";

    @Pointcut(pointCupExpression)
    public void anyShiroAnnotatedMethod(){}

    @Pointcut(pointCupExpression)
    void anyShiroAnnotatedMethodCall(JoinPoint thisJoinPoint) {
    }

    private AspectjAnnotationsAuthorizingMethodInterceptor interceptor =
            new AspectjAnnotationsAuthorizingMethodInterceptor();

    @Before("anyShiroAnnotatedMethodCall(thisJoinPoint)")
    public void executeAnnotatedMethod(JoinPoint thisJoinPoint) throws Throwable {
        interceptor.performBeforeInterception(thisJoinPoint);
    }
}