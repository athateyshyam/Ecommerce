/**
 * 
 */
package org.finance.fundtransfer.repository;

import org.finance.fundtransfer.entity.BankEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Athatey Shyam
 *
 */
@Repository
public interface BankDetailsRepository extends JpaRepository<BankEntity, Long> {

	/**
	 * @param branchCode
	 * @return BankDetails
	 */
	public BankEntity findByBranchCode(String branchCode);

	/**
	 * @param ifsc
	 * @return BankDetails
	 */
	public BankEntity findByIfsc(String ifsc);

	/**
	 * @param bankName
	 * @param branchCode
	 * @return BankDetails
	 */
	public BankEntity findByBankNameAndBranchCode(String bankName, String branchCode);

}
