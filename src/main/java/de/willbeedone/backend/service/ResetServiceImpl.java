package de.willbeedone.backend.service;

import de.willbeedone.backend.domain.entity.ResetCode;
import de.willbeedone.backend.domain.entity.User;
import de.willbeedone.backend.repository.ResetCodeRepository;
import de.willbeedone.backend.service.interfaces.ResetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class ResetServiceImpl implements ResetService {

    @Autowired
    private final ResetCodeRepository codeRepository;

    public ResetServiceImpl(ResetCodeRepository codeRepository) {
        this.codeRepository = codeRepository;
    }

    @Override
    public String generateResetPasswordCode(User user) {
        String code = UUID.randomUUID().toString();
        LocalDateTime expired = LocalDateTime.now().plusMinutes(5);
        ResetCode codeEntity = new ResetCode(code, expired, user);
        codeRepository.save(codeEntity);
        return code;
    }
}
