package com.servustech.skeleton.features.account.role;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class RoleService {
    private final RoleRepository roleRepository;

    public Role getUserRole() {
        return roleRepository.findByName(RoleName.ROLE_USER);
    }

    public Role getLecturerRole() {
        return roleRepository.findByName(RoleName.ROLE_LECTURER);
    }

    public Role getAdminRole() {
        return roleRepository.findByName(RoleName.ROLE_ADMIN);
    }

    public Role getPremiumRole() {
        return roleRepository.findByName(RoleName.ROLE_PREMIUM);
    }
}
