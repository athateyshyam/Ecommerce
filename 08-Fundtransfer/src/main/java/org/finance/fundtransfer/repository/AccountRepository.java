/**
 * 
 */
package org.finance.fundtransfer.repository;

import org.finance.fundtransfer.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Athatey Shyam
 *
 */
@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
	public Account findByAccNo(String accNo);
}
