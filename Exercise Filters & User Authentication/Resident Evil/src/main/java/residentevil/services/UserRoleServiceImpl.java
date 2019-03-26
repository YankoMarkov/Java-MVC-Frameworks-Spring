package residentevil.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import residentevil.entities.UserRole;
import residentevil.models.services.UserRoleServiceModel;
import residentevil.repositories.UserRoleRepository;

@Service
public class UserRoleServiceImpl implements UserRoleService {

    private final UserRoleRepository userRoleRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public UserRoleServiceImpl(UserRoleRepository userRoleRepository, ModelMapper modelMapper) {
        this.userRoleRepository = userRoleRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public UserRoleServiceModel getRoleByName(String name) {
        UserRole role = this.userRoleRepository.findByAuthority(name).orElse(null);
        if (role == null) {
            return null;
        }
        return this.modelMapper.map(role, UserRoleServiceModel.class);
    }
}
