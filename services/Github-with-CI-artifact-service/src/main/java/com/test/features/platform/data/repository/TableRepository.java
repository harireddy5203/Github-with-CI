/*
 * Copyright (c) 2020-2021 Innominds inc. All Rights Reserved. This software is
 * confidential and proprietary information of Innominds inc. You shall not disclose
 * Confidential Information and shall use it only in accordance with the terms
 *
 */
package com.test.features.platform.data.repository;

import com.test.commons.data.jpa.repository.ExtendedJpaRepository;
import com.test.features.platform.data.model.persistence.TableEntity;
import org.springframework.stereotype.Repository;

/**
 * Repository interface to handle the operations pertaining to domain models of type "TableEntity".
 *
 * @author Mahalingam Iyer
 */
@Repository
public interface TableRepository extends ExtendedJpaRepository<TableEntity, Integer> {
    // Any custom methods can be added here.
}
