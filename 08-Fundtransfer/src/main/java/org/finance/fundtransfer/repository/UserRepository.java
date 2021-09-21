/**
 * 
 */
package org.finance.fundtransfer.repository;

import org.finance.fundtransfer.entity.Account;
import org.finance.fundtransfer.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
	public UserEntity findByUsername(String username);
	public UserEntity findByAccount(Account account);
	public UserEntity findByEmail(String email);
}
