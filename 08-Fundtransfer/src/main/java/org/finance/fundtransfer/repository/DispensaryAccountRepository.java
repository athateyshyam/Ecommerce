/**
 * 
 */
package org.finance.fundtransfer.repository;

import org.finance.fundtransfer.entity.DispensaryAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Athatey Shyam
 *
 */
@Repository
public interface DispensaryAccountRepository extends JpaRepository<DispensaryAccount, Long> {

}
