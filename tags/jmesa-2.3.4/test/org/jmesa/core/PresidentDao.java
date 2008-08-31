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
package org.jmesa.core;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @since 2.0
 * @author Jeff Johnston
 */
public class PresidentDao {

    public static Collection<President> getPresidents() {
        List<President> presidents = new ArrayList<President>();

        President president = new President();
        Name name = new Name("George", "Washington", "Father of His Country");
        president.setId(1);
        president.setName(name);
        president.setTerm("1789-1797");
        president.setBorn(getDate("02/22/1732"));
        president.setDied(getDate("12/14/1799"));
        president.setEducation("The equivalent of an elementary school education");
        president.setCareer("Soldier, Planter");
        president.setPoliticalParty("Federalist");
        president.setSalary(new Double(1000.01));
        presidents.add(president);

        president = new President();
        president.setId(2);
        name = new Name("John", "Adams", "Atlas of Independence");
        president.setName(name);
        president.setTerm("1797-1801");
        president.setBorn(getDate("10/25/1764"));
        president.setDied(getDate("07/04/1826"));
        president.setEducation("Harvard College (graduated 1755)");
        president.setCareer("Lawyer");
        president.setPoliticalParty("Federalist");
        president.setSalary(new Double(2000.02));
        presidents.add(president);

        president = new President();
        president.setId(3);
        name = new Name("Thomas", "Jefferson", "Man of the People, Sage of Monticello");
        president.setName(name);
        president.setTerm("1801-09");
        president.setBorn(getDate("04/13/1743"));
        president.setDied(getDate("07/04/1826"));
        president.setEducation("College of William and Mary (graduated 1762)");
        president.setCareer("Lawyer, Planter");
        president.setPoliticalParty("Democratic-Republican ");
        president.setSalary(new Double(3000.03));
        presidents.add(president);

        president = new President();
        president.setId(4);
        name = new Name("James", "Madison", "Father of the Constitution");
        president.setName(name);
        president.setTerm("1809-17");
        president.setBorn(getDate("03/16/1751"));
        president.setDied(getDate("06/28/1836"));
        president.setEducation("College of New Jersey (now Princeton University, graduated 1771)");
        president.setCareer("Lawyer");
        president.setPoliticalParty("Democratic-Republican");
        president.setSalary(new Double(4000.04));
        presidents.add(president);

        president = new President();
        president.setId(5);
        name = new Name("James", "Monroe", "The Last Cocked Hat, Era-of-Good-Feelings President");
        president.setName(name);
        president.setTerm("1817-25");
        president.setBorn(getDate("04/28/1758"));
        president.setDied(getDate("07/04/1831"));
        president.setEducation("College of William and Mary (graduated 1776)");
        president.setCareer("Lawyer");
        president.setPoliticalParty("Democratic-Republican");
        president.setSalary(new Double(5000.05));
        presidents.add(president);

        president = new President();
        president.setId(6);
        name = new Name("John", "Adams", "Old Man Eloquent");
        president.setName(name);
        president.setTerm("1825-29");
        president.setBorn(getDate("07/11/1767"));
        president.setDied(getDate("02/23/1848"));
        president.setEducation("Harvard College (graduated 1787)");
        president.setCareer("Lawyer, Senator, Diplomat");
        president.setPoliticalParty("Federalist, Democratic-Republican, Whig ");
        president.setSalary(new Double(6000.06));
        presidents.add(president);

        president = new President();
        president.setId(7);
        name = new Name("Andrew", "Jackson", "Old Hickory");
        president.setName(name);
        president.setTerm("1829-37");
        president.setBorn(getDate("03/15/1767"));
        president.setDied(getDate("06/08/1845"));
        president.setEducation("");
        president.setCareer("Lawyer, Soldier");
        president.setPoliticalParty("Democrat");
        president.setSalary(new Double(7000.07));
        presidents.add(president);

        president = new President();
        president.setId(8);
        name = new Name("Martin", "Van Buren", "The Little Magician, The Red Fox of Kinderhook");
        president.setName(name);
        president.setTerm("1837-41");
        president.setBorn(getDate("12/05/1782"));
        president.setDied(getDate("08/24/1862"));
        president.setEducation("Kinderhook Academy (graduated 1796)");
        president.setCareer("Lawyer");
        president.setPoliticalParty("Democrat");
        president.setSalary(new Double(8000.08));
        presidents.add(president);

        president = new President();
        president.setId(9);
        name = new Name("William Henry", "Harrison", "Old Tippecanoe, Old Tip");
        president.setName(name);
        president.setTerm("1841");
        president.setBorn(getDate("02/09/1773"));
        president.setDied(getDate("05/04/1841"));
        president.setEducation("Hampden-Sydney College");
        president.setCareer("Soldier");
        president.setPoliticalParty("Whig");
        president.setSalary(new Double(9000.09));
        presidents.add(president);

        president = new President();
        president.setId(10);
        name = new Name("John", "Tyler", "Accidental President, His Accidency");
        president.setName(name);
        president.setTerm("1841-45");
        president.setBorn(getDate("03/29/1790"));
        president.setDied(getDate("01/18/1862"));
        president.setEducation("College of William and Mary (graduated 1807)");
        president.setCareer("Lawyer");
        president.setPoliticalParty("Democrat, Whig");
        president.setSalary(new Double(10000.10));
        presidents.add(president);

        president = new President();
        president.setId(11);
        name = new Name("James", "Polk", "Young Hickory");
        president.setName(name);
        president.setTerm("1845-49");
        president.setBorn(getDate("11/02/1795"));
        president.setDied(getDate("06/15/1849"));
        president.setEducation("University of North Carolina (graduated 1818)");
        president.setCareer("Lawyer");
        president.setPoliticalParty("Democrat");
        president.setSalary(new Double(11000.11));
        presidents.add(president);

        president = new President();
        president.setId(12);
        name = new Name("Zachary", "Taylor", "Old Rough and Ready");
        president.setName(name);
        president.setTerm("1849-50");
        president.setBorn(getDate("06/21/1810"));
        president.setDied(getDate("07/09/1850"));
        president.setEducation("");
        president.setCareer("Soldier");
        president.setPoliticalParty("Whig");
        president.setSalary(new Double(12000.12));
        presidents.add(president);

        president = new President();
        president.setId(13);
        name = new Name("Millard", "Fillmore", "The American Louis Philippe");
        president.setName(name);
        president.setTerm("1850-53");
        president.setBorn(getDate("01/07/1800"));
        president.setDied(getDate("03/08/1874"));
        president.setEducation("Six months of grade school; read law in 1822");
        president.setCareer("Lawyer");
        president.setPoliticalParty("Whig");
        president.setSalary(new Double(13000.13));
        presidents.add(president);

        president = new President();
        president.setId(14);
        name = new Name("Franklin", "Pierce", "Young Hickory of the Granite Hills");
        president.setName(name);
        president.setTerm("1853-57");
        president.setBorn(getDate("11/23/1804"));
        president.setDied(getDate("10/08/1869"));
        president.setEducation("Bowdoin College (graduated 1824)");
        president.setCareer("Lawyer, Public Official");
        president.setPoliticalParty("Democrat");
        president.setSalary(new Double(14000.14));
        presidents.add(president);

        president = new President();
        president.setId(15);
        name = new Name("James", "Buchanan", "Old Buck");
        president.setName(name);
        president.setTerm("1857-61");
        president.setBorn(getDate("05/23/1791"));
        president.setDied(getDate("06/01/1868"));
        president.setEducation("Dickinson College (graduated 1809)");
        president.setCareer("Lawyer");
        president.setPoliticalParty("Democrat");
        president.setSalary(new Double(15000.15));
        presidents.add(president);

        president = new President();
        president.setId(16);
        name = new Name("Abraham", "Lincoln", "Honest Abe, Illinois Rail-Splitter");
        president.setName(name);
        president.setTerm("1861-65");
        president.setBorn(getDate("02/12/1809"));
        president.setDied(getDate("05/15/1865"));
        president.setEducation("");
        president.setCareer("Lawyer");
        president.setPoliticalParty("Whig, Republican");
        president.setSalary(new Double(16000.16));
        presidents.add(president);

        president = new President();
        president.setId(17);
        name = new Name("Andrew", "Johnson", "");
        president.setName(name);
        president.setTerm("1865-69");
        president.setBorn(getDate("12/29/1808"));
        president.setDied(getDate("07/31/1875"));
        president.setEducation("");
        president.setCareer("Tailor, Public Official");
        president.setPoliticalParty("emocrat; Unionist");
        president.setSalary(new Double(17000.17));
        presidents.add(president);

        president = new President();
        president.setId(18);
        name = new Name("Ulysses S.", "Grant", "Hero of Appomattox");
        president.setName(name);
        president.setTerm("1869-77");
        president.setBorn(getDate("04/27/1822"));
        president.setDied(getDate("07/23/1885"));
        president.setEducation("U.S. Military Academy, West Point, New York (graduated 1843)");
        president.setCareer("Soldier");
        president.setPoliticalParty("Republican");
        president.setSalary(new Double(18000.18));
        presidents.add(president);

        president = new President();
        president.setId(19);
        name = new Name("Rutherford B.", "Hayes", "Dark-Horse President");
        president.setName(name);
        president.setTerm("1877-81");
        president.setBorn(getDate("10/04/1822"));
        president.setDied(getDate("01/17/1893"));
        president.setEducation("Kenyon College (graduated 1842), Harvard Law School (graduated 1845)");
        president.setCareer("Lawyer");
        president.setPoliticalParty("Republican");
        president.setSalary(new Double(19000.19));
        presidents.add(president);

        president = new President();
        president.setId(20);
        name = new Name("James", "Garfield", "");
        president.setName(name);
        president.setTerm("1881");
        president.setBorn(getDate("11/19/1831"));
        president.setDied(getDate("09/19/1881"));
        president.setEducation("Western Reserve Eclectic Institute (now Hiram College), Williams College (graduated 1856)");
        president.setCareer("Teacher, Public Official");
        president.setPoliticalParty("Republican");
        president.setSalary(new Double(20000.20));
        presidents.add(president);

        president = new President();
        president.setId(21);
        name = new Name("Chester", "Arthur", "The Gentleman Boss");
        president.setName(name);
        president.setTerm("1881-85");
        president.setBorn(getDate("10/05/1829"));
        president.setDied(getDate("11/18/1886"));
        president.setEducation("Union College (graduated 1848)");
        president.setCareer("Lawyer");
        president.setPoliticalParty("Republican");
        president.setSalary(new Double(21000.21));
        presidents.add(president);

        president = new President();
        president.setId(22);
        name = new Name("Grover", "Cleveland", "Big Steve, Uncle Jumbo");
        president.setName(name);
        president.setTerm("1885-89");
        president.setBorn(getDate("03/18/1837"));
        president.setDied(getDate("06/24/1908"));
        president.setEducation("Some common school; Read law (1855-59)");
        president.setCareer("Lawyer");
        president.setPoliticalParty("Democrat");
        president.setSalary(new Double(22000.22));
        presidents.add(president);

        president = new President();
        president.setId(23);
        name = new Name("Benjamin", "Harrison", "Kid Gloves Harrison, Little Ben");
        president.setName(name);
        president.setTerm("1889-93");
        president.setBorn(getDate("08/20/1833"));
        president.setDied(getDate("03/13/1901"));
        president.setEducation("Miami University (Ohio), graduated 1852");
        president.setCareer("Lawyer");
        president.setPoliticalParty("Republican");
        president.setSalary(new Double(23000.23));
        presidents.add(president);

        president = new President();
        president.setId(24);
        name = new Name("Grover", "Cleveland", "Big Steve, Uncle Jumbo");
        president.setName(name);
        president.setTerm("1893-97");
        president.setBorn(getDate("03/18/1837"));
        president.setDied(getDate("06/24/1908"));
        president.setEducation("Some common school; Read law (1855-59)");
        president.setCareer("Lawyer");
        president.setPoliticalParty("Democrat");
        president.setSalary(new Double(24000.24));
        presidents.add(president);

        president = new President();
        president.setId(25);
        name = new Name("William", "McKinley", "Idol of Ohio");
        president.setName(name);
        president.setTerm("1897-1901");
        president.setBorn(getDate("01/29/1843"));
        president.setDied(getDate("09/14/1901"));
        president.setEducation("Allegheny College");
        president.setCareer("Lawyer");
        president.setPoliticalParty("Republican");
        president.setSalary(new Double(25000.25));
        presidents.add(president);

        president = new President();
        president.setId(26);
        name = new Name("Theodore", "Roosevelt", "TR, Trust-Buster, Teddy");
        president.setName(name);
        president.setTerm("1901-09");
        president.setBorn(getDate("10/27/1858"));
        president.setDied(getDate("01/06/1919"));
        president.setEducation("Harvard College (graduated 1880)");
        president.setCareer("Author, Lawyer, Public Official");
        president.setPoliticalParty("Republican");
        president.setSalary(new Double(26000.26));
        presidents.add(president);

        president = new President();
        president.setId(27);
        name = new Name("William H.", "Taft", "");
        president.setName(name);
        president.setTerm("1909-13");
        president.setBorn(getDate("10/15/1857"));
        president.setDied(getDate("03/08/1930"));
        president.setEducation("Yale College (graduated 1878), Cincinnati Law School (LL.B., 1880)");
        president.setCareer("Lawyer, Public Official");
        president.setPoliticalParty("Republican");
        president.setSalary(new Double(27000.27));
        presidents.add(president);

        president = new President();
        president.setId(28);
        name = new Name("Woodrow", "Wilson", "Schoolmaster in Politics");
        president.setName(name);
        president.setTerm("1913-21");
        president.setBorn(getDate("12/28/1856"));
        president.setDied(getDate("02/03/1924"));
        president.setEducation("College of New Jersey (now Princeton University), graduated 1879");
        president.setCareer("Professor, College Administration, Public Official");
        president.setPoliticalParty("Democrat");
        president.setSalary(new Double(28000.28));
        presidents.add(president);

        president = new President();
        president.setId(29);
        name = new Name("Warren", "Harding", "");
        president.setName(name);
        president.setTerm("1921-23");
        president.setBorn(getDate("11/02/1865"));
        president.setDied(getDate("08/02/1923"));
        president.setEducation("Ohio Central College (graduated 1882)");
        president.setCareer("Editor-Publisher");
        president.setPoliticalParty("Republican");
        president.setSalary(new Double(29000.29));
        presidents.add(president);

        president = new President();
        president.setId(30);
        name = new Name("Calvin", "Coolidge", "Silent Ca");
        president.setName(name);
        president.setTerm("1923-29");
        president.setBorn(getDate("07/04/1872"));
        president.setDied(getDate("01/05/1933"));
        president.setEducation("Amherst College (graduated 1895)");
        president.setCareer("Lawyer");
        president.setPoliticalParty("Republican");
        president.setSalary(new Double(30000.30));
        presidents.add(president);

        president = new President();
        president.setId(31);
        name = new Name("Herbert", "Hoover", "");
        president.setName(name);
        president.setTerm("1929-33");
        president.setBorn(getDate("08/10/1874"));
        president.setDied(getDate("10/20/ 1964"));
        president.setEducation("Stanford University (graduated 1895)");
        president.setCareer("Engineer");
        president.setPoliticalParty("Republican");
        president.setSalary(new Double(31000.31));
        presidents.add(president);

        president = new President();
        president.setId(32);
        name = new Name("Franklin D.", "Roosevelt", "FDR");
        president.setName(name);
        president.setTerm("1933-45");
        president.setBorn(getDate("01/30/1882"));
        president.setDied(getDate("04/12/1945"));
        president.setEducation("Harvard College (graduated 1903), Columbia Law School");
        president.setCareer("Public Official, Lawyer");
        president.setPoliticalParty("Democrat");
        president.setSalary(new Double(32000.32));
        presidents.add(president);

        president = new President();
        president.setId(33);
        name = new Name("Harry", "Truman", "Give 'Em Hell Harry");
        president.setName(name);
        president.setTerm("1945-53");
        president.setBorn(getDate("05/08/1884"));
        president.setDied(getDate("12/26/1972"));
        president.setEducation("University of Kansas City Law School");
        president.setCareer("Farmer, Businessman, Public Official");
        president.setPoliticalParty("Democrat");
        president.setSalary(new Double(33000.33));
        presidents.add(president);

        president = new President();
        president.setId(34);
        name = new Name("Dwight", "Eisenhower", "Ike");
        president.setName(name);
        president.setTerm("1953-61");
        president.setBorn(getDate("10/14/1890"));
        president.setDied(getDate("03/28/1969"));
        president.setEducation(" U.S. Military Academy, West Point, New York (graduated 1915)");
        president.setCareer("Soldier");
        president.setPoliticalParty("Republican");
        president.setSalary(new Double(34000.34));
        presidents.add(president);

        president = new President();
        president.setId(35);
        name = new Name("John F.", "Kennedy", "JFK, Jack");
        president.setName(name);
        president.setTerm("1961-63");
        president.setBorn(getDate("05/29/1917"));
        president.setDied(getDate("11/22/1963"));
        president.setEducation("Harvard College (graduated 1940)");
        president.setCareer("Author, officer, U.S. Navy");
        president.setPoliticalParty("Democrat");
        president.setSalary(new Double(35000.35));
        presidents.add(president);

        president = new President();
        president.setId(36);
        name = new Name("Lyndon", "Johnson", "LBJ");
        president.setName(name);
        president.setTerm("1963-69");
        president.setBorn(getDate("08/27/1908"));
        president.setDied(getDate("01/22/1973"));
        president.setEducation("Southwest Texas State Teachers College (graduated 1930); Georgetown Law School (attended, 1934)");
        president.setCareer("Teacher, Public Official");
        president.setPoliticalParty("Democrat");
        president.setSalary(new Double(36000.36));
        presidents.add(president);

        president = new President();
        president.setId(37);
        name = new Name("Richard", "Nixon", "");
        president.setName(name);
        president.setTerm("1969-74");
        president.setBorn(getDate("01/09/1913"));
        president.setDied(getDate("04/22/1994"));
        president.setEducation("Whittier College (1934); Duke University Law School (1937)");
        president.setCareer("Lawyer, public official");
        president.setPoliticalParty("Republican");
        president.setSalary(new Double(37000.37));
        presidents.add(president);

        president = new President();
        president.setId(38);
        name = new Name("Gerald", "Ford", "Jerry");
        president.setName(name);
        president.setTerm("1974-77");
        president.setBorn(getDate("07/14/1913"));
        president.setEducation("University of Michigan (1935); Yale University Law School (1941)");
        president.setCareer("Lawyer, Public Official");
        president.setPoliticalParty("Republican");
        president.setSalary(new Double(38000.38));
        presidents.add(president);

        president = new President();
        president.setId(39);
        name = new Name("Jimmy", "Carter", "Jimmy");
        president.setName(name);
        president.setTerm("1977-81");
        president.setBorn(getDate("10/01/1924"));
        president.setEducation("Georgia Southwestern College, 1941-1942; Georgia Institute of Technology, 1942-1943; United States Naval Academy, 1943-1946 (class of 1947);");
        president.setCareer(" Soldier; Farmer, Warehouseman, Public Official, Professor");
        president.setSalary(new Double(39000.39));
        presidents.add(president);

        president = new President();
        president.setId(40);
        name = new Name("Ronald", "Reagan", "The Great Communicator");
        president.setName(name);
        president.setTerm("1981-89");
        president.setBorn(getDate("02/06/1911"));
        president.setDied(getDate("06/05/2004"));
        president.setEducation("Eureka College (1932)");
        president.setCareer("Actor, public official");
        president.setPoliticalParty("Republican");
        president.setSalary(new Double(40000.40));
        presidents.add(president);

        president = new President();
        president.setId(41);
        name = new Name("George H.W.", "Bush", "Poppy");
        president.setName(name);
        president.setTerm("1989-93");
        president.setBorn(getDate("06/12/1924"));
        president.setEducation("Yale University (1948)");
        president.setCareer("Businessman, public official");
        president.setPoliticalParty("Republican");
        president.setSalary(new Double(41000.41));
        presidents.add(president);

        president = new President();
        president.setId(42);
        name = new Name("William J.", "Clinton", "Bill");
        president.setName(name);
        president.setTerm("1993-2001");
        president.setBorn(getDate("08/19/1946"));
        president.setEducation("Georgetown University (1968), attended Oxford University (1968-1970), Yale Law School (1973)");
        president.setCareer("Lawyer, public official");
        president.setPoliticalParty("Democrat");
        president.setSalary(new Double(42000.42));
        presidents.add(president);

        president = new President();
        president.setId(43);
        name = new Name("George W.", "Bush", "Dubya");
        president.setName(name);
        president.setTerm("2001-present");
        president.setBorn(getDate("07/06/1946"));
        president.setEducation("Yale (B.S., 1968), Harvard (M.B.A., 1975)");
        president.setCareer("Businessman, public official");
        president.setPoliticalParty("Republican");
        president.setSalary(new Double(43000.43));
        presidents.add(president);

        return presidents;
    }

    public static Collection<Map> getPresidentsAsListOfMaps() {

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
            results.add(result);
        }

        return results;
    }

    public static Date getDate(String date) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(simpleDateFormat.parse(date));
            return new Date(calendar.getTimeInMillis());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }
}
