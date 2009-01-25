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
package org.jmesaweb.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import java.util.Map;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.jmesa.util.ItemUtils;
import org.jmesaweb.domain.President;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * @since 2.0
 * @author Jeff Johnston
 */
public class PresidentDaoImpl extends HibernateDaoSupport implements PresidentDao {
    public List<President> getPresidents() {
        return getHibernateTemplate().find("from President");
    }

    public int getPresidentsCountWithFilter(final PresidentFilter filter) {
        Integer count = (Integer) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session)
                    throws HibernateException, SQLException {
                Criteria criteria = session.createCriteria(President.class);
                criteria = filter.execute(criteria);
                criteria.setProjection(Projections.rowCount()).uniqueResult();
                return criteria.uniqueResult();
            }
        });

        return count.intValue();
    }

    public List<President> getPresidentsWithFilterAndSort(final PresidentFilter filter, final PresidentSort sort, final int rowStart, final int rowEnd) {
        List applications = (List) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session)
                    throws HibernateException, SQLException {
                Criteria criteria = session.createCriteria(President.class);
                criteria = filter.execute(criteria);
                criteria = sort.execute(criteria);
                criteria.setFirstResult(rowStart);
                criteria.setMaxResults(rowEnd - rowStart);
                return criteria.list();
            }
        });

        return applications;
    }
    
    public Map<String, President> getPresidentsByUniqueIds(String property, List<String> uniqueIds) {
        Map<String, President> result = new HashMap<String, President>();
        
        String sql = "from President";
        
        String in = "";
        for (String uniqueId : uniqueIds) {
            in += uniqueId + ",";
        }
        
        in = in.substring(0, in.length() - 1);

        sql += " where id in (" + in + ")";
        
        List<President> presidents = getHibernateTemplate().find(sql);
        for (President president : presidents) {
            Object key = ItemUtils.getItemValue(president, property);
            result.put(String.valueOf(key), president);
        }
        
        return result;
    }
    
    public void save(President president) {
        getHibernateTemplate().saveOrUpdate(president);
    }
}
