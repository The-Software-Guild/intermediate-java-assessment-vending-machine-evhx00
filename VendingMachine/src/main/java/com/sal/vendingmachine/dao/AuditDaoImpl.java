
package com.sal.vendingmachine.dao;

import com.sal.vendingmachine.dao.AuditDao;
import com.sal.vendingmachine.dao.VendingMachineException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

/**
 *
 * @author salajrawi
 */
public class AuditDaoImpl implements AuditDao {

    public static final String AUDIT_FILE = "audit.txt";
    //Writes information to .txt file when called
    @Override
    public void writeAuditEntry(String entry) throws VendingMachineException {
        PrintWriter output;
        try {
            output = new PrintWriter(new FileWriter(AUDIT_FILE, true));

            LocalDateTime timestamp = LocalDateTime.now();
            output.println(timestamp + " : " + entry);
            output.flush();
            output.close();
        }
        catch (IOException e) {
            throw new VendingMachineException("Could not write audit information.");
        }
    }
}
