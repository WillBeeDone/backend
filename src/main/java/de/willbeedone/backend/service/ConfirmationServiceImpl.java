package de.willbeedone.backend.service;

import de.willbeedone.backend.domain.entity.ConfirmationCode;
import de.willbeedone.backend.domain.entity.User;
import de.willbeedone.backend.repository.ConfirmationCodeRepository;
import de.willbeedone.backend.service.interfaces.ConfirmationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class ConfirmationServiceImpl implements ConfirmationService {

    @Autowired
    private final ConfirmationCodeRepository codeRepository;

    public ConfirmationServiceImpl(ConfirmationCodeRepository codeRepository) {
        this.codeRepository = codeRepository;
    }

    @Override
    public String generateConfirmationCode(User user) {
        String code = UUID.randomUUID().toString();
        LocalDateTime expired = LocalDateTime.now().plusMinutes(5);
        ConfirmationCode codeEntity = new ConfirmationCode(code, expired, user);
        codeRepository.save(codeEntity);
        return code;
    }
}
