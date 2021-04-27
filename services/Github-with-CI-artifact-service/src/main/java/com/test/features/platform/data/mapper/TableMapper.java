/*
 * Copyright (c) 2020-2021 Innominds inc. All Rights Reserved. This software is
 * confidential and proprietary information of Innominds inc. You shall not disclose
 * Confidential Information and shall use it only in accordance with the terms
 *
 */
package com.test.features.platform.data.mapper;

import com.test.features.platform.data.model.experience.table.CreateTableRequest;
import com.test.features.platform.data.model.experience.table.Table;
import com.test.features.platform.data.model.experience.table.UpdateTableRequest;
import com.test.features.platform.data.model.persistence.TableEntity;
import java.util.Collection;
import java.util.stream.Collectors;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

/**
 * Mapper contract that maps / transforms data from an instance of type {@link TableEntity to {@link Table and vice-versa.
 *
 * @author Mahalingam Iyer
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN)
public interface TableMapper {

    /**
     * This method transforms an instance of type {@link CreateTableRequest} to an instance of type
     * {@link TableEntity}.
     *
     * @param source Instance of type {@link CreateTableRequest} that needs to be transformed to
     *     {@link TableEntity}.
     * @return Instance of type {@link TableEntity}.
     */
    TableEntity transform(CreateTableRequest source);

    /**
     * This method transforms an instance of type {@link TableEntity} to an instance of type {@link
     * Table}.
     *
     * @param source Instance of type {@link TableEntity} that needs to be transformed to {@link
     *     Table}.
     * @return Instance of type {@link Table}.
     */
    Table transform(TableEntity source);

    /**
     * This method converts / transforms the provided collection of {@link TableEntity} instances to
     * a collection of instances of type {@link Table}.
     *
     * @param source Instance of type {@link TableEntity} that needs to be transformed to {@link
     *     Table}.
     * @return Collection of instance of type {@link Table}.
     */
    default Collection<Table> transformListTo(Collection<TableEntity> source) {
        return source.stream().map(this::transform).collect(Collectors.toSet());
    }
    /**
     * This method transforms an instance of type {@link UpdateTableRequest} to an instance of type
     * {@link TableEntity}.
     *
     * <p>The provided instance ({@code target}) will be updated instead of creating a new instance.
     *
     * @param source Instance of type {@link UpdateTableRequest} that needs to be transformed to
     *     {@link TableEntity}.
     * @param target Instance of type {@link TableEntity} that will be updated instead of creating
     *     and returning a new instance.
     */
    void transform(UpdateTableRequest source, @MappingTarget TableEntity target);

    /**
     * This method transforms an instance of type {@link UpdateTableRequest} to an instance of type
     * {@link TableEntity}.
     *
     * @param source Instance of type {@link UpdateTableRequest} that needs to be transformed to
     *     {@link TableEntity}.
     * @return Instance of type {@link TableEntity}.
     */
    TableEntity transform(UpdateTableRequest source);

    /**
     * This method converts / transforms the provided collection of {@link UpdateTableRequest}
     * instances to a collection of instances of type {@link TableEntity}.
     *
     * @param source Instance of type {@link UpdateTableRequest} that needs to be transformed to
     *     {@link TableEntity}.
     * @return Instance of type {@link TableEntity}.
     */
    default Collection<TableEntity> transformList(Collection<UpdateTableRequest> source) {
        return source.stream().map(this::transform).collect(Collectors.toSet());
    }
}
