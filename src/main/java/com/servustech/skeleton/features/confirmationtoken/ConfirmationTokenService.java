package com.servustech.skeleton.features.confirmationtoken;


import com.servustech.skeleton.exceptions.InvalidConfirmTokenException;
import com.servustech.skeleton.security.constants.AuthConstants;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@AllArgsConstructor
@Service
public class ConfirmationTokenService {

    private final ConfirmationTokenRepository confirmationTokenRepository;

    public void saveToken(ConfirmationToken confirmationToken) {
        confirmationTokenRepository.save(confirmationToken);
    }

    public ConfirmationToken findConfirmationTokenByEmail(String email) {
        return confirmationTokenRepository.findByUserEmail(email)
                .orElseThrow(() -> new InvalidConfirmTokenException(AuthConstants.INVALID_CONFIRMATION_TOKEN));
    }

    @Transactional
    public void deleteTokenAfterConfirmation(String confirmationToken) {
        confirmationTokenRepository.deleteConfirmationTokenByValue(confirmationToken);
    }

    public ConfirmationToken validateToken(String email, String confirmationTokenRequest) {

        ConfirmationToken confirmationToken = findConfirmationTokenByEmail(email);

        if (!confirmationToken.getValue().equals(confirmationTokenRequest)) {
            throw new InvalidConfirmTokenException(AuthConstants.INVALID_CONFIRMATION_TOKEN);
        }

        return confirmationToken;
    }
}
