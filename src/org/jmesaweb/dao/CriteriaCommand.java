/*
 * Project: CareerAdmin
 * @(#)$Id: Command.java,v 1.1 2006/09/21 22:05:17 jjohnston Exp $
 * 
 * This Software is confidential and copyrighted.
 * Copyright  2003, 2005 Room & Board, Inc.  All Rights Reserved.
 * 
 * This software is the proprietary information of Room & Board, Inc.  Use 
 * is subject to license terms.
 * 
 * Title to Software and all associated intellectual property rights is retained 
 * by Room & Board, Inc. and/or its licensors.
 * 
 * Except as specifically authorized in any Supplemental License Terms, this 
 * software may not be copied.  Unless enforcement is prohibited by 
 * applicable law, you may not modify, decompile, reverse engineer this 
 * Software without the express written permission of Room & Board, Inc.
 * 
 * No right, title or interest in or to any trademark, service mark, logo or 
 * trade name of Room & Board, Inc. or its licensors is granted under 
 * this Agreement.
 */
package org.jmesaweb.dao;

import org.hibernate.Criteria;

/**
 * Creates a command to wrap the Hibernate criteria API.
 * 
 * @since 2.0
 * @author Jeff Johnston
 */
public interface CriteriaCommand {
	public Criteria execute(Criteria criteria);
}
