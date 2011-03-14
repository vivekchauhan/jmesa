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

import java.util.ArrayList;
import java.util.Collection;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.jmesaweb.dao.PresidentDao;
import org.jmesaweb.dao.PresidentFilter;
import org.jmesaweb.dao.PresidentSort;
import org.jmesaweb.domain.President;

/**
 * @since 2.0
 * @author Jeff Johnston
 */
public class PresidentServiceImpl implements PresidentService {

    private PresidentDao presidentDao;

    public Collection<President> getPresidents() {
        return presidentDao.getPresidents();
    }

    public Collection<Map> getPresidentsAsMaps() {

        List<Map> results = new ArrayList<Map>();

        Collection<President> presidents = getPresidents();
        for (President president : presidents) {
            Map result = new HashMap();
            result.put("id", president.getId());
            result.put("firstName", president.getName().getFirstName());
            result.put("lastName", president.getName().getLastName());
            result.put("term", president.getTerm());
            result.put("born", president.getBorn());
            result.put("died", president.getDied());
            result.put("education", president.getEducation());
            result.put("career", president.getCareer());
            result.put("politicalParty", president.getPoliticalParty());
            result.put("selected", president.getSelected());
            results.add(result);
        }

        return results;
    }

    public int getPresidentsCountWithFilter(PresidentFilter filter) {
        return presidentDao.getPresidentsCountWithFilter(filter);
    }

    public Collection<President> getPresidentsWithFilterAndSort(PresidentFilter filter, PresidentSort sort, int rowStart, int rowEnd) {
        return presidentDao.getPresidentsWithFilterAndSort(filter, sort, rowStart, rowEnd);
    }

    public Map<String, President> getPresidentsByUniqueIds(String property, List<String> uniqueIds) {
        return presidentDao.getPresidentsByUniqueIds(property, uniqueIds);
    }

    public void save(President president) {
        presidentDao.save(president);
    }

    public void setPresidentDao(PresidentDao presidentsDao) {
        this.presidentDao = presidentsDao;
    }
}
