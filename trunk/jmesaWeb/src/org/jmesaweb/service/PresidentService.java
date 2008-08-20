/*
 * Copyright 2004 original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jmesaweb.service;

import java.util.Collection;

import java.util.List;
import java.util.Map;
import org.jmesaweb.dao.PresidentFilter;
import org.jmesaweb.dao.PresidentSort;
import org.jmesaweb.domain.President;

/**
 * @since 2.0
 * @author Jeff Johnston
 */
public interface PresidentService {

    Collection<President> getPresidents();

    Collection<Map> getPresidentsAsMaps();

    int getPresidentsCountWithFilter(PresidentFilter filter);

    Collection<President> getPresidentsWithFilterAndSort(PresidentFilter filter, PresidentSort sort, int rowStart, int rowEnd);

    Map<String, President> getPresidentsByUniqueIds(String property, List<String> uniqueIds);

    void save(President president);
}
