package de.willbeedone.backend.service.interfaces;

import org.springframework.stereotype.Service;

@Service
public interface AdminService {

    boolean blockUserByEmail(String email);

}
