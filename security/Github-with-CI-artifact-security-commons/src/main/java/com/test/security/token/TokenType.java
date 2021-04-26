/*
 * Copyright (c) 2021 REPLACE_CUSTOMER_NAME. All rights reserved.
 *
 * This file is part of Github-with-CI.
 *
 * Github-with-CI project and associated code cannot be copied
 * and/or distributed without a written permission of REPLACE_CUSTOMER_NAME,
 * and/or its subsidiaries.
 */
package com.test.security.token;

import lombok.AllArgsConstructor;
import lombok.Getter;

import com.fasterxml.jackson.annotation.JsonProperty;

import com.test.commons.IEnumValueProvider;

/**
 * Enumerated data type that captures the different types of access tokens (e.g. Bearer, Basic, etc.).
 *
 * @author Mahalingam Iyer
 */
@Getter
@AllArgsConstructor
public enum TokenType implements IEnumValueProvider<TokenType> {
    @JsonProperty("Basic")
    BASIC("Basic"),

    @JsonProperty("Bearer")
    BEARER("Bearer");

    /** Value for this enum constant. */
    private final String value;
}
