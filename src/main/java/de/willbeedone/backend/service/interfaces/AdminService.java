package de.willbeedone.backend.service.interfaces;

import org.springframework.stereotype.Service;

@Service
public interface AdminService {

    void blockUserByEmail(String email);
}
