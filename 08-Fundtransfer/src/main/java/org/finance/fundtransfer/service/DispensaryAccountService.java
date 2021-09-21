/**
 * 
 */
package org.finance.fundtransfer.service;

import org.finance.fundtransfer.dto.DispensaryAccountDTO;
import org.finance.fundtransfer.request.AmountTransferRequest;

/**
 * @author Athatey Shyam
 *
 */
public interface DispensaryAccountService {
	public DispensaryAccountDTO addDispensaryAccount(String username,DispensaryAccountDTO dto);
	public String depositAmountInDispensary(String username,AmountTransferRequest request);
}
