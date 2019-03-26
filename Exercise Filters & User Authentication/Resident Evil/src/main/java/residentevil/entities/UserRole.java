package residentevil.entities;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity(name = "roles")
public class UserRole extends BaseEntity implements GrantedAuthority {

    private String authority;

    @Override
    @Column(name = "authority", nullable = false)
    public String getAuthority() {
        return this.authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
}
