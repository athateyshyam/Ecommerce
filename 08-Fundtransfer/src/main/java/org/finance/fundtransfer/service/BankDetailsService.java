/**
 * 
 */
package org.finance.fundtransfer.service;

import org.finance.fundtransfer.dto.BankDTO;

/**
 * @author Athatey Shyam
 *
 */
public interface BankDetailsService {
	public BankDTO addBankDetails(BankDTO bank);
	public BankDTO getBankDetailsByBranchCode(String branchCode);
	public BankDTO getBankDetailsByIfscCode(String ifsc);
	public BankDTO getBankDetailsByBankNameAndBranchCode(String bankName,String branchCode);
}
