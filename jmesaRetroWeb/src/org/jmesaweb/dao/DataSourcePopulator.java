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

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.jmesaweb.domain.Name;
import org.jmesaweb.domain.President;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate3.HibernateTemplate;

/**
 * @author Jeff Johnston
 */
public class DataSourcePopulator implements InitializingBean {
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    
    private HibernateTemplate hibernateTemplate;
    private JdbcTemplate jdbcTemplate;
    private Resource presidents;

    public void afterPropertiesSet() throws Exception {
        createPresidentTable();
        
        int id = 0;

        InputStream input = presidents.getInputStream();
        List lines = IOUtils.readLines(input);
        for (Iterator it = lines.iterator(); it.hasNext();) {
            String line = (String)it.next();
            id++;
            line = StringUtils.substringAfter(line, "\"");
            String[] data =  line.split("\",\"");

            President president = new President();
            president.setId(id);

            Name name = new Name();
            name.setFirstName(data[0]);
            name.setLastName(data[1]);
            name.setNickName(data[2]);
            president.setName(name);

            president.setTerm(data[3]);
            president.setBorn(simpleDateFormat.parse(data[4]));

            String died = data[5];
            if (StringUtils.isNotEmpty(died) && !died.equals("null")) {
                president.setDied(simpleDateFormat.parse(died));
            }

            president.setEducation(data[6]);
            president.setCareer(data[7]);
            president.setPoliticalParty(data[8]);

            hibernateTemplate.save(president);
        }
    }
    
    public void createPresidentTable() {
        jdbcTemplate.execute("CREATE TABLE president ( " 
                + " id    INTEGER NOT NULL PRIMARY KEY, " 
                + " first_name      VARCHAR(50) NOT NULL, " 
                + " last_name       VARCHAR(50) NOT NULL, "
                + " nick_name       VARCHAR(50) NOT NULL, " 
                + " term            VARCHAR(50) NOT NULL, " 
                + " born            DATE NOT NULL," 
                + " died            DATE NULL, "
                + " education       VARCHAR(100) NULL, " 
                + " career          VARCHAR(100) NOT NULL, " 
                + " political_party VARCHAR(100) NOT NULL, " 
                + " selected VARCHAR(1) NULL " 
                + " )");        
    }

    public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
        this.hibernateTemplate = hibernateTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void setPresidents(Resource presidents) {
        this.presidents = presidents;
    }
}
