package com.eita.signup.utils.di.qualifires;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;

import javax.inject.Qualifier;

@Qualifier
@Retention(RUNTIME)
public @interface ResponseInterceptor {
}
