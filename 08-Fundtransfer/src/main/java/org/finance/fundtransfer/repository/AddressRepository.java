/**
 * 
 */
package org.finance.fundtransfer.repository;

import org.finance.fundtransfer.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

}
