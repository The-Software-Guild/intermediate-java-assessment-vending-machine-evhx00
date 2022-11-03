
package com.sal.vendingmachine.dao;

import com.sal.vendingmachine.dao.VendingMachineException;

/**
 *
 * @author salajrawi
 */
public interface AuditDao {
    void writeAuditEntry(String entry) throws VendingMachineException;
}
