package residentevil.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import residentevil.entities.UserRole;
import residentevil.repositories.UserRoleRepository;

import javax.annotation.PostConstruct;

@Component
public class DataBaseSeeder {

    private final UserRoleRepository userRoleRepository;

    @Autowired
    public DataBaseSeeder(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }

    @PostConstruct
    public void seedRole() {
        if (this.userRoleRepository.count() == 0) {
            UserRole adminRole = new UserRole();
            adminRole.setAuthority("ADMIN");

            UserRole moderatorRole = new UserRole();
            moderatorRole.setAuthority("MODERATOR");

            UserRole userRole = new UserRole();
            userRole.setAuthority("USER");

            try {
                this.userRoleRepository.saveAndFlush(adminRole);
                this.userRoleRepository.saveAndFlush(moderatorRole);
                this.userRoleRepository.saveAndFlush(userRole);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
