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

import org.springframework.beans.factory.InitializingBean;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @author Jeff Johnston
 */
public class DataSourcePopulator implements InitializingBean {
    JdbcTemplate jdbcTemplate;

    public void afterPropertiesSet() throws Exception {
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
                + " political_party VARCHAR(100) NOT NULL " 
                + " )");

        jdbcTemplate.execute("INSERT INTO president(id, first_name, last_name, nick_name, term, born, died, education, career, political_party)"
                        + " VALUES(1, 'George', 'Washington', 'Father of His Country', '1789-1797', '1732-02-22 00:00:00.0', '1799-12-14 00:00:00.0', 'The equivalent of an elementary school education', 'Soldier, Planter', 'Federalist')");

        jdbcTemplate.execute("INSERT INTO president(id, first_name, last_name, nick_name, term, born, died, education, career, political_party)"
                        + " VALUES(2, 'John', 'Adams', 'Atlas of Independence', '1797-1801', '1764-10-25 00:00:00.0', '1826-07-04 00:00:00.0', 'Harvard College (graduated 1755)', 'Lawyer', 'Federalist')");

        jdbcTemplate.execute("INSERT INTO president(id, first_name, last_name, nick_name, term, born, died, education, career, political_party)"
                        + " VALUES(3, 'Thomas', 'Jefferson', 'Man of the People, Sage of Monticello', '1801-09', '1743-04-13 00:00:00.0', '1826-07-04 00:00:00.0', 'College of William and Mary (graduated 1762)', 'Lawyer, Planter', 'Democratic-Republican ')");

        jdbcTemplate.execute("INSERT INTO president(id, first_name, last_name, nick_name, term, born, died, education, career, political_party)"
                        + " VALUES(4, 'James', 'Madison', 'Father of the Constitution', '1809-17', '1751-03-16 00:00:00.0', '1836-06-28 00:00:00.0', 'College of New Jersey (now Princeton University, graduated 1771)', 'Lawyer', 'Democratic-Republican')");

        jdbcTemplate.execute("INSERT INTO president(id, first_name, last_name, nick_name, term, born, died, education, career, political_party)"
                        + " VALUES(5, 'James', 'Monroe', 'The Last Cocked Hat, Era-of-Good-Feelings President', '1817-25', '1758-04-28 00:00:00.0', '1831-07-04 00:00:00.0', 'College of William and Mary (graduated 1776)', 'Lawyer', 'Democratic-Republican')");

        jdbcTemplate.execute("INSERT INTO president(id, first_name, last_name, nick_name, term, born, died, education, career, political_party)"
                        + " VALUES(6, 'John', 'Adams', 'Old Man Eloquent', '1825-29', '1767-07-11 00:00:00.0', '1848-02-23 00:00:00.0', 'Harvard College (graduated 1787)', 'Lawyer, Senator, Diplomat', 'Federalist, Democratic-Republican, Whig ')");

        jdbcTemplate.execute("INSERT INTO president(id, first_name, last_name, nick_name, term, born, died, education, career, political_party)"
                        + " VALUES(7, 'Andrew', 'Jackson', 'Old Hickory', '1829-37', '1767-03-15 00:00:00.0', '1845-06-08 00:00:00.0', '', 'Lawyer, Soldier', 'Democrat')");

        jdbcTemplate.execute("INSERT INTO president(id, first_name, last_name, nick_name, term, born, died, education, career, political_party)"
                        + " VALUES(8, 'Martin', 'Van Buren', 'The Little Magician, The Red Fox of Kinderhook', '1837-41', '1782-12-05 00:00:00.0', '1862-08-24 00:00:00.0', 'Kinderhook Academy (graduated 1796)', 'Lawyer', 'Democrat')");

        jdbcTemplate.execute("INSERT INTO president(id, first_name, last_name, nick_name, term, born, died, education, career, political_party)"
                        + " VALUES(9, 'William Henry', 'Harrison', 'Old Tippecanoe, Old Tip', '1841', '1773-02-09 00:00:00.0', '1841-05-04 00:00:00.0', 'Hampden-Sydney College', 'Soldier', 'Whig')");

        jdbcTemplate.execute("INSERT INTO president(id, first_name, last_name, nick_name, term, born, died, education, career, political_party)"
                        + " VALUES(10, 'John', 'Tyler', 'Accidental President, His Accidency', '1841-45', '1790-03-29 00:00:00.0', '1862-01-18 00:00:00.0', 'College of William and Mary (graduated 1807)', 'Lawyer', 'Democrat, Whig')");

        jdbcTemplate.execute("INSERT INTO president(id, first_name, last_name, nick_name, term, born, died, education, career, political_party)"
                        + " VALUES(11, 'James', 'Polk', 'Young Hickory', '1845-49', '1795-11-02 00:00:00.0', '1849-06-15 00:00:00.0', 'University of North Carolina (graduated 1818)', 'Lawyer', 'Democrat')");

        jdbcTemplate.execute("INSERT INTO president(id, first_name, last_name, nick_name, term, born, died, education, career, political_party)"
                        + " VALUES(12, 'Zachary', 'Taylor', 'Old Rough and Ready', '1849-50', '1810-06-21 00:00:00.0', '1850-07-09 00:00:00.0', '', 'Soldier', 'Whig')");

        jdbcTemplate.execute("INSERT INTO president(id, first_name, last_name, nick_name, term, born, died, education, career, political_party)"
                        + " VALUES(13, 'Millard', 'Fillmore', 'The American Louis Philippe', '1850-53', '1800-01-07 00:00:00.0', '1874-03-08 00:00:00.0', 'Six months of grade school; read law in 1822', 'Lawyer', 'Whig')");

        jdbcTemplate.execute("INSERT INTO president(id, first_name, last_name, nick_name, term, born, died, education, career, political_party)"
                        + " VALUES(14, 'Franklin', 'Pierce', 'Young Hickory of the Granite Hills', '1853-57', '1804-11-23 00:00:00.0', '1869-10-08 00:00:00.0', 'Bowdoin College (graduated 1824)', 'Lawyer, Public Official', 'Democrat')");

        jdbcTemplate.execute("INSERT INTO president(id, first_name, last_name, nick_name, term, born, died, education, career, political_party)"
                        + " VALUES(15, 'James', 'Buchanan', 'Old Buck', '1857-61', '1791-05-23 00:00:00.0', '1868-06-01 00:00:00.0', 'Dickinson College (graduated 1809)', 'Lawyer', 'Democrat')");

        jdbcTemplate.execute("INSERT INTO president(id, first_name, last_name, nick_name, term, born, died, education, career, political_party)"
                        + " VALUES(16, 'Abraham', 'Lincoln', 'Honest Abe, Illinois Rail-Splitter', '1861-65', '1809-02-12 00:00:00.0', '1865-05-15 00:00:00.0', '', 'Lawyer', 'Whig, Republican')");

        jdbcTemplate.execute("INSERT INTO president(id, first_name, last_name, nick_name, term, born, died, education, career, political_party)"
                        + " VALUES(17, 'Andrew', 'Johnson', '', '1865-69', '1808-12-29 00:00:00.0', '1875-07-31 00:00:00.0', '', 'Tailor, Public Official', 'emocrat; Unionist')");

        jdbcTemplate.execute("INSERT INTO president(id, first_name, last_name, nick_name, term, born, died, education, career, political_party)"
                        + " VALUES(18, 'Ulysses S.', 'Grant', 'Hero of Appomattox', '1869-77', '1822-04-27 00:00:00.0', '1885-07-23 00:00:00.0', 'U.S. Military Academy, West Point, New York (graduated 1843)', 'Soldier', 'Republican')");

        jdbcTemplate.execute("INSERT INTO president(id, first_name, last_name, nick_name, term, born, died, education, career, political_party)"
                        + " VALUES(19, 'Rutherford B.', 'Hayes', 'Dark-Horse President', '1877-81', '1822-10-04 00:00:00.0', '1893-01-17 00:00:00.0', 'Kenyon College (graduated 1842), Harvard Law School (graduated 1845)', 'Lawyer', 'Republican')");

        jdbcTemplate.execute("INSERT INTO president(id, first_name, last_name, nick_name, term, born, died, education, career, political_party)"
                        + " VALUES(20, 'James', 'Garfield', '', '1881', '1831-11-19 00:00:00.0', '1881-09-19 00:00:00.0', 'Western Reserve Eclectic Institute (now Hiram College), Williams College (graduated 1856)', 'Teacher, Public Official', 'Republican')");

        jdbcTemplate.execute("INSERT INTO president(id, first_name, last_name, nick_name, term, born, died, education, career, political_party)"
                        + " VALUES(21, 'Chester', 'Arthur', 'The Gentleman Boss', '1881-85', '1829-10-05 00:00:00.0', '1886-11-18 00:00:00.0', 'Union College (graduated 1848)', 'Lawyer', 'Republican')");

        jdbcTemplate.execute("INSERT INTO president(id, first_name, last_name, nick_name, term, born, died, education, career, political_party)"
                        + " VALUES(22, 'Grover', 'Cleveland', 'Big Steve, Uncle Jumbo', '1885-89', '1837-03-18 00:00:00.0', '1908-06-24 00:00:00.0', 'Some common school; Read law (1855-59)', 'Lawyer', 'Democrat')");

        jdbcTemplate.execute("INSERT INTO president(id, first_name, last_name, nick_name, term, born, died, education, career, political_party)"
                        + " VALUES(23, 'Benjamin', 'Harrison', 'Kid Gloves Harrison, Little Ben', '1889-93', '1833-08-20 00:00:00.0', '1901-03-13 00:00:00.0', 'Miami University (Ohio), graduated 1852', 'Lawyer', 'Republican')");

        jdbcTemplate.execute("INSERT INTO president(id, first_name, last_name, nick_name, term, born, died, education, career, political_party)"
                        + " VALUES(24, 'Grover', 'Cleveland', 'Big Steve, Uncle Jumbo', '1893-97', '1837-03-18 00:00:00.0', '1908-06-24 00:00:00.0', 'Some common school; Read law (1855-59)', 'Lawyer', 'Democrat')");

        jdbcTemplate.execute("INSERT INTO president(id, first_name, last_name, nick_name, term, born, died, education, career, political_party)"
                        + " VALUES(25, 'William', 'McKinley', 'Idol of Ohio', '1897-1901', '1843-01-29 00:00:00.0', '1901-09-14 00:00:00.0', 'Allegheny College', 'Lawyer', 'Republican')");

        jdbcTemplate.execute("INSERT INTO president(id, first_name, last_name, nick_name, term, born, died, education, career, political_party)"
                        + " VALUES(26, 'Theodore', 'Roosevelt', 'TR, Trust-Buster, Teddy', '1901-09', '1858-10-27 00:00:00.0', '1919-01-06 00:00:00.0', 'Harvard College (graduated 1880)', 'Author, Lawyer, Public Official', 'Republican')");

        jdbcTemplate.execute("INSERT INTO president(id, first_name, last_name, nick_name, term, born, died, education, career, political_party)"
                        + " VALUES(27, 'William H.', 'Taft', '', '1909-13', '1857-10-15 00:00:00.0', '1930-03-08 00:00:00.0', 'Yale College (graduated 1878), Cincinnati Law School (LL.B., 1880)', 'Lawyer, Public Official', 'Republican')");

        jdbcTemplate.execute("INSERT INTO president(id, first_name, last_name, nick_name, term, born, died, education, career, political_party)"
                        + " VALUES(28, 'Woodrow', 'Wilson', 'Schoolmaster in Politics', '1913-21', '1856-12-28 00:00:00.0', '1924-02-03 00:00:00.0', 'College of New Jersey (now Princeton University), graduated 1879', 'Professor, College Administration, Public Official', 'Democrat')");

        jdbcTemplate.execute("INSERT INTO president(id, first_name, last_name, nick_name, term, born, died, education, career, political_party)"
                        + " VALUES(29, 'Warren', 'Harding', '', '1921-23', '1865-11-02 00:00:00.0', '1923-08-02 00:00:00.0', 'Ohio Central College (graduated 1882)', 'Editor-Publisher', 'Republican')");

        jdbcTemplate.execute("INSERT INTO president(id, first_name, last_name, nick_name, term, born, died, education, career, political_party)"
                        + " VALUES(30, 'Calvin', 'Coolidge', 'Silent Ca', '1923-29', '1872-07-04 00:00:00.0', '1933-01-05 00:00:00.0', 'Amherst College (graduated 1895)', 'Lawyer', 'Republican')");

        jdbcTemplate.execute("INSERT INTO president(id, first_name, last_name, nick_name, term, born, died, education, career, political_party)"
                        + " VALUES(31, 'Herbert', 'Hoover', '', '1929-33', '1874-08-10 00:00:00.0', '1964-10-20 00:00:00.0', 'Stanford University (graduated 1895)', 'Engineer', 'Republican')");

        jdbcTemplate.execute("INSERT INTO president(id, first_name, last_name, nick_name, term, born, died, education, career, political_party)"
                        + " VALUES(32, 'Franklin D.', 'Roosevelt', 'FDR', '1933-45', '1882-01-30 00:00:00.0', '1945-04-12 00:00:00.0', 'Harvard College (graduated 1903), Columbia Law School', 'Public Official, Lawyer', 'Democrat')");

        jdbcTemplate.execute("INSERT INTO president(id, first_name, last_name, nick_name, term, born, died, education, career, political_party)"
                        + " VALUES(33, 'Harry', 'Truman', 'Give ''Em Hell Harry', '1945-53', '1884-05-08 00:00:00.0', '1972-12-26 00:00:00.0', 'University of Kansas City Law School', 'Farmer, Businessman, Public Official', 'Democrat')");

        jdbcTemplate.execute("INSERT INTO president(id, first_name, last_name, nick_name, term, born, died, education, career, political_party)"
                        + " VALUES(34, 'Dwight', 'Eisenhower', 'Ike', '1953-61', '1890-10-14 00:00:00.0', '1969-03-28 00:00:00.0', ' U.S. Military Academy, West Point, New York (graduated 1915)', 'Soldier', 'Republican')");

        jdbcTemplate.execute("INSERT INTO president(id, first_name, last_name, nick_name, term, born, died, education, career, political_party)"
                        + " VALUES(35, 'John F.', 'Kennedy', 'JFK, Jack', '1961-63', '1917-05-29 00:00:00.0', '1963-11-22 00:00:00.0', 'Harvard College (graduated 1940)', 'Author, officer, U.S. Navy', 'Democrat')");

        jdbcTemplate.execute("INSERT INTO president(id, first_name, last_name, nick_name, term, born, died, education, career, political_party)"
                        + " VALUES(36, 'Lyndon', 'Johnson', 'LBJ', '1963-69', '1908-08-27 00:00:00.0', '1973-01-22 00:00:00.0', 'Southwest Texas State Teachers College (graduated 1930); Georgetown Law School (attended, 1934)', 'Teacher, Public Official', 'Democrat')");

        jdbcTemplate.execute("INSERT INTO president(id, first_name, last_name, nick_name, term, born, died, education, career, political_party)"
                        + " VALUES(37, 'Richard', 'Nixon', '', '1969-74', '1913-01-09 00:00:00.0', '1994-04-22 00:00:00.0', 'Whittier College (1934); Duke University Law School (1937)', 'Lawyer, public official', 'Republican')");

        jdbcTemplate.execute("INSERT INTO president(id, first_name, last_name, nick_name, term, born, died, education, career, political_party)"
                        + " VALUES(38, 'Gerald', 'Ford', 'Jerry', '1974-77', '1913-07-14 00:00:00.0', NULL, 'University of Michigan (1935); Yale University Law School (1941)', 'Lawyer, Public Official', 'Republican')");

        jdbcTemplate.execute("INSERT INTO president(id, first_name, last_name, nick_name, term, born, died, education, career, political_party)"
                        + " VALUES(39, 'Jimmy', 'Carter', 'Jimmy', '1977-81', '1924-10-01 00:00:00.0', NULL, 'Georgia Southwestern College, 1941-1942; Georgia Institute of Technology, 1942-1943; United States Naval Academy, 1943-1946 (class of 1947);', ' Soldier; Farmer, Warehouseman, Public Official, Professor', '')");

        jdbcTemplate.execute("INSERT INTO president(id, first_name, last_name, nick_name, term, born, died, education, career, political_party)"
                        + " VALUES(40, 'Ronald', 'Reagan', 'The Great Communicator', '1981-89', '1911-02-06 00:00:00.0', '2004-06-05 00:00:00.0', 'Eureka College (1932)', 'Actor, public official', 'Republican')");

        jdbcTemplate.execute("INSERT INTO president(id, first_name, last_name, nick_name, term, born, died, education, career, political_party)"
                        + " VALUES(41, 'George H.W.', 'Bush', 'Poppy', '1989-93', '1924-06-12 00:00:00.0', NULL, 'Yale University (1948)', 'Businessman, public official', 'Republican')");

        jdbcTemplate.execute("INSERT INTO president(id, first_name, last_name, nick_name, term, born, died, education, career, political_party)"
                        + " VALUES(42, 'William J.', 'Clinton', 'Bill', '1993-2001', '1946-08-19 00:00:00.0', NULL, 'Georgetown University (1968), attended Oxford University (1968-1970), Yale Law School (1973)', 'Lawyer, public official', 'Democrat')");

        jdbcTemplate.execute("INSERT INTO president(id, first_name, last_name, nick_name, term, born, died, education, career, political_party)"
                        + " VALUES(43, 'George W.', 'Bush', 'Dubya', '2001-present', '1946-07-06 00:00:00.0', NULL, 'Yale (B.S., 1968), Harvard (M.B.A., 1975)', 'Businessman, public official', 'Republican')");
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
