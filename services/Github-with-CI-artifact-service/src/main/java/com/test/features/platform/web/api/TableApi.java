/*
 * Copyright (c) 2020-2021 Innominds inc. All Rights Reserved. This software is
 * confidential and proprietary information of Innominds inc. You shall not disclose
 * Confidential Information and shall use it only in accordance with the terms
 *
 */
package com.test.features.platform.web.api;

import com.test.commons.data.utils.PageUtils;
import com.test.commons.web.api.AbstractApi;
import com.test.commons.web.configuration.properties.ApiDocumentationSettings;
import com.test.features.platform.data.model.experience.table.CreateTableRequest;
import com.test.features.platform.data.model.experience.table.Table;
import com.test.features.platform.data.model.experience.table.UpdateTableRequest;
import com.test.features.platform.web.service.TableService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Implementation of APIs that provide CRUD (Create, Read, Update and Delete) functionality for
 * persistence models of type {@link com.test.features.platform.data.model.persistence.TableEntity}.
 *
 * @author Mahalingam Iyer
 */
@Slf4j
@RestController
public class TableApi extends AbstractApi {
    /** Tag for this API. */
    public static final String API_TAG = "Tables";

    /** Service implementation of type {@link TableService}. */
    private final TableService tableService;

    /**
     * Constructor.
     *
     * @param tableService Service instance of type {@link TableService}.
     */
    public TableApi(final TableService tableService) {
        this.tableService = tableService;
    }

    /**
     * This API provides the capability to add a new instance of type {@link
     * com.test.features.platform.data.model.persistence.TableEntity} into the system.
     *
     * @param payload Payload containing the details required to create an instance of type {@link
     *     com.test.features.platform.data.model.persistence.TableEntity}.
     * @return Response of type {@link ResponseEntity} that wraps an instance of type {@link Table}.
     */
    @Operation(
            method = "createTable",
            summary = "Create a new Table.",
            description = "This API is used to create a new Table in the system.",
            tags = {TableApi.API_TAG},
            security = {
                @SecurityRequirement(
                        name =
                                ApiDocumentationSettings.ApiSecurityScheme
                                        .DEFAULT_SECURITY_SCHEME_NAME)
            })
    @ApiResponses(
            value = {
                @ApiResponse(
                        responseCode = "201",
                        description = "Successfully created a new Table in the system.",
                        content = @Content),
                @ApiResponse(
                        responseCode = "403",
                        description = "You do not have permissions to perform this operation.",
                        content = @Content)
            })
    @PreAuthorize(value = "isAuthenticated()")
    @PostMapping(value = "/tables")
    public ResponseEntity<Table> createTable(@Valid @RequestBody final CreateTableRequest payload) {
        // Delegate to the service layer.
        final Table newInstance = tableService.createTable(payload);

        // Build a response entity object and return it.
        return ResponseEntity.status(HttpStatus.CREATED).body(newInstance);
    }

    /**
     * This API provides the capability to update an existing instance of type {@link
     * com.test.features.platform.data.model.persistence.TableEntity} in the system.
     *
     * @param tableId Unique identifier of Table in the system, which needs to be updated.
     * @param payload Request payload containing the details of an existing Table, which needs to be
     *     updated in the system.
     * @return Response of type {@link ResponseEntity} that wraps an instance of type {@link Table}.
     */
    @Operation(
            method = "updateTable",
            summary = "Update an existing Table.",
            description = "This API is used to update an existing Table in the system.",
            tags = {TableApi.API_TAG},
            security = {
                @SecurityRequirement(
                        name =
                                ApiDocumentationSettings.ApiSecurityScheme
                                        .DEFAULT_SECURITY_SCHEME_NAME)
            })
    @ApiResponses(
            value = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Successfully updated an existing Table in the system.",
                        content = @Content),
                @ApiResponse(
                        responseCode = "403",
                        description = "You do not have permissions to perform this operation.",
                        content = @Content)
            })
    @PreAuthorize(value = "isAuthenticated()")
    @PutMapping(value = "/tables/{tableId}")
    public ResponseEntity<Table> updateTable(
            @PathVariable(name = "tableId") final Integer tableId,
            @Valid @RequestBody final UpdateTableRequest payload) {
        // Delegate to the service layer.
        final Table updatedInstance = tableService.updateTable(tableId, payload);

        // Build a response entity object and return it.
        return ResponseEntity.ok(updatedInstance);
    }

    /**
     * This API provides the capability to retrieve the details of an existing {@link
     * com.test.features.platform.data.model.persistence.TableEntity} in the system.
     *
     * @param tableId Unique identifier of Table in the system, whose details have to be retrieved.
     * @return Response of type {@link ResponseEntity} that wraps an instance of type {@link Table}.
     */
    @Operation(
            method = "findTable",
            summary = "Find an existing Table.",
            description = "This API is used to find an existing Table in the system.",
            tags = {TableApi.API_TAG},
            security = {
                @SecurityRequirement(
                        name =
                                ApiDocumentationSettings.ApiSecurityScheme
                                        .DEFAULT_SECURITY_SCHEME_NAME)
            })
    @ApiResponses(
            value = {
                @ApiResponse(
                        responseCode = "200",
                        description =
                                "Successfully retrieved the details of an existing Table in the system.",
                        content = @Content),
                @ApiResponse(
                        responseCode = "403",
                        description = "You do not have permissions to perform this operation.",
                        content = @Content)
            })
    @PreAuthorize(value = "isAuthenticated()")
    @GetMapping(value = "/tables/{tableId}")
    public ResponseEntity<Table> findTable(@PathVariable(name = "tableId") final Integer tableId) {
        // Delegate to the service layer.
        final Table matchingInstance = tableService.findTable(tableId);

        // Build a response entity object and return it.
        return ResponseEntity.ok(matchingInstance);
    }

    /**
     * This API provides the capability to retrieve all instances of type {@link
     * com.test.features.platform.data.model.persistence.TableEntity} in the system in a paginated
     * manner.
     *
     * @param page Page number.
     * @param size Page size.
     * @return Response of type {@link ResponseEntity} that holds a page of instances of type Table
     *     based on the provided pagination settings.
     */
    @Operation(
            method = "findAllTables",
            summary = "Find all Tables.",
            description = "This API is used to find all Tables in the system.",
            tags = {TableApi.API_TAG},
            security = {
                @SecurityRequirement(
                        name =
                                ApiDocumentationSettings.ApiSecurityScheme
                                        .DEFAULT_SECURITY_SCHEME_NAME)
            })
    @ApiResponses(
            value = {
                @ApiResponse(
                        responseCode = "200",
                        description =
                                "Successfully retrieved the Tables in the system based on the provided pagination settings.",
                        content = @Content),
                @ApiResponse(
                        responseCode = "403",
                        description = "You do not have permissions to perform this operation.",
                        content = @Content)
            })
    @PreAuthorize(value = "isAuthenticated()")
    @GetMapping(value = "/tables")
    public ResponseEntity<Page<Table>> findAllTables(
            @RequestParam(name = "page", required = false, defaultValue = "0") final Integer page,
            @RequestParam(name = "size", required = false, defaultValue = "20")
                    final Integer size) {
        // Delegate to the service layer.
        final Pageable pageSettings = PageUtils.createPaginationConfiguration(page, size);
        final Page<Table> matchingInstances = tableService.findAllTables(pageSettings);

        // Build a response entity object and return it.
        return ResponseEntity.ok(matchingInstances);
    }

    /**
     * This API provides the capability to delete an existing instance of type {@link
     * com.test.features.platform.data.model.persistence.TableEntity} in the system.
     *
     * @param tableId Unique identifier of Table in the system, which needs to be deleted.
     * @return Response of type {@link ResponseEntity} that holds the unique identifier of the
     *     {@link com.test.features.platform.data.model.persistence.TableEntity} that was deleted
     *     from the system.
     */
    @Operation(
            method = "deleteTable",
            summary = "Delete an existing Table.",
            description = "This API is used to delete an existing Table in the system.",
            tags = {TableApi.API_TAG},
            security = {
                @SecurityRequirement(
                        name =
                                ApiDocumentationSettings.ApiSecurityScheme
                                        .DEFAULT_SECURITY_SCHEME_NAME)
            })
    @ApiResponses(
            value = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Successfully deleted an existing Table in the system.",
                        content = @Content),
                @ApiResponse(
                        responseCode = "403",
                        description = "You do not have permissions to perform this operation.",
                        content = @Content)
            })
    @PreAuthorize(value = "isAuthenticated()")
    @DeleteMapping(value = "/tables/{tableId}")
    public ResponseEntity<Integer> deleteTable(
            @PathVariable(name = "tableId") final Integer tableId) {
        // Delegate to the service layer.
        final Integer deletedInstance = tableService.deleteTable(tableId);

        // Build a response entity object and return it.
        return ResponseEntity.ok(deletedInstance);
    }
}
