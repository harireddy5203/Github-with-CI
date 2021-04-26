/*
 * Copyright (c) 2020-2021 Innominds inc. All Rights Reserved. This software is
 * confidential and proprietary information of Innominds inc. You shall not disclose
 * Confidential Information and shall use it only in accordance with the terms
 *
 */
package com.test.features.platform.web.service;

import com.test.commons.data.utils.PageUtils;
import com.test.commons.instrumentation.Instrument;
import com.test.features.platform.data.mapper.TableMapper;
import com.test.features.platform.data.model.experience.table.CreateTableRequest;
import com.test.features.platform.data.model.experience.table.Table;
import com.test.features.platform.data.model.experience.table.UpdateTableRequest;
import com.test.features.platform.data.model.persistence.TableEntity;
import com.test.features.platform.data.repository.TableRepository;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

/**
 * Service implementation that provides CRUD (Create, Read, Update, Delete) capabilities for
 * entities of type {@link TableEntity}.
 *
 * @author Mahalingam Iyer
 */
@Slf4j
@Validated
@Service
public class TableService {
    /** Repository implementation of type {@link TableRepository}. */
    private final TableRepository tableRepository;

    /** Mapper implementation of type {@link TableMapper} to transform between different types. */
    private final TableMapper tableMapper;

    /**
     * Constructor.
     *
     * @param tableRepository Repository instance of type {@link TableRepository}.
     * @param tableMapper Mapper instance of type {@link TableMapper}.
     */
    public TableService(final TableRepository tableRepository, final TableMapper tableMapper) {
        this.tableRepository = tableRepository;
        this.tableMapper = tableMapper;
    }

    /**
     * This method attempts to create an instance of type {@link TableEntity} in the system based on
     * the provided payload.
     *
     * @param payload Payload containing the details required to create an instance of type {@link
     *     TableEntity}.
     * @return An experience model of type {@link Table} that represents the newly created entity of
     *     type {@link TableEntity}.
     */
    @Instrument
    @Transactional
    public Table createTable(@Valid final CreateTableRequest payload) {
        // 1. Transform the experience model to a persistence model.
        final TableEntity tableEntity = tableMapper.transform(payload);

        // 2. Save the entity.
        TableService.LOGGER.debug("Saving a new instance of type - TableEntity");
        final TableEntity newInstance = tableRepository.save(tableEntity);

        // 3. Transform the created entity to an experience model and return it.
        return tableMapper.transform(newInstance);
    }

    /**
     * This method attempts to update an existing instance of type {@link TableEntity} using the
     * details from the provided input, which is an instance of type {@link UpdateTableRequest}.
     *
     * @param tableId Unique identifier of Table in the system, which needs to be updated.
     * @param payload Request payload containing the details of an existing Table, which needs to be
     *     updated in the system.
     * @return A instance of type {@link Table} containing the updated details.
     */
    @Instrument
    @Transactional
    public Table updateTable(final Integer tableId, @Valid final UpdateTableRequest payload) {
        // 1. Verify that the entity being updated truly exists.
        final TableEntity matchingInstance = tableRepository.findByIdOrThrow(tableId);

        // 2. Transform the experience model to a persistence model and delegate to the save()
        // method.
        tableMapper.transform(payload, matchingInstance);

        // 3. Save the entity
        TableService.LOGGER.debug("Saving the updated entity - TableEntity");
        final TableEntity updatedInstance = tableRepository.save(matchingInstance);

        // 4. Transform updated entity to output object
        return tableMapper.transform(updatedInstance);
    }

    /**
     * This method attempts to find a {@link TableEntity} whose unique identifier matches the
     * provided identifier.
     *
     * @param tableId Unique identifier of Table in the system, whose details have to be retrieved.
     * @return Matching entity of type {@link Table} if found, else returns null.
     */
    @Instrument
    @Transactional(readOnly = true)
    public Table findTable(final Integer tableId) {
        // 1. Find a matching entity and throw an exception if not found.
        final TableEntity matchingInstance = tableRepository.findByIdOrThrow(tableId);

        // 2. Transform the matching entity to the desired output.
        return tableMapper.transform(matchingInstance);
    }

    /**
     * This method attempts to find instances of type TableEntity based on the provided page
     * definition. If the page definition is null or contains invalid values, this method attempts
     * to return the data for the first page (i.e. page index is 0) with a default page size as 20.
     *
     * @return Returns a page of objects based on the provided page definition. Each object in the
     *     returned page is an instance of type {@link Table}.
     */
    @Instrument
    @Transactional(readOnly = true)
    public Page<Table> findAllTables(final Pageable page) {
        // 1. Validate the provided pagination settings.
        final Pageable pageSettings = PageUtils.validateAndUpdatePaginationConfiguration(page);
        TableService.LOGGER.debug(
                "Page settings: page number {}, page size {}",
                pageSettings.getPageNumber(),
                pageSettings.getPageSize());

        // 2. Delegate to the super class method to find the data (page settings are verified in
        // that method).
        final Page<TableEntity> pageData = tableRepository.findAll(pageSettings);

        // 3. If the page has data, transform each element into target type.
        if (pageData.hasContent()) {
            final List<Table> dataToReturn =
                    pageData.getContent().stream()
                            .map(tableMapper::transform)
                            .collect(Collectors.toList());

            return PageUtils.createPage(dataToReturn, pageSettings, pageData.getTotalElements());
        }

        // Return empty page.
        return PageUtils.emptyPage(pageSettings);
    }

    /**
     * This method attempts to delete an existing instance of type {@link TableEntity} whose unique
     * identifier matches the provided identifier.
     *
     * @param tableId Unique identifier of Table in the system, which needs to be deleted.
     * @return Unique identifier of the instance of type TableEntity that was deleted.
     */
    @Instrument
    @Transactional
    public Integer deleteTable(final Integer tableId) {
        // 1. Delegate to our repository method to handle the deletion.
        return tableRepository.deleteOne(tableId);
    }
}
