package com.servustech.skeleton.features.confirmationtoken;

import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, Integer> {

    @Query("SELECT t FROM ConfirmationToken t where t.user.email = :email")
    Optional<ConfirmationToken> findByUserEmail(@Param("email") String email);

    @Modifying
    @Query("DELETE FROM ConfirmationToken CT WHERE CT.value =:confirmationToken")
    void deleteConfirmationTokenByValue(@Param("confirmationToken") String confirmationToken);
}
