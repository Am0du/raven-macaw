package User_Service.service.impl;

import User_Service.entity.Role;
import User_Service.exception.NotFoundException;
import User_Service.repository.RoleRepository;
import User_Service.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    @Override
    public Role getRoleByName(String roleName) {
        return roleRepository.findByRole(roleName)
                .orElseThrow(() -> new NotFoundException("Role not found"));
    }
}
