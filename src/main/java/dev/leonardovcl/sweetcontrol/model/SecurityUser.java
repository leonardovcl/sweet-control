package dev.leonardovcl.sweetcontrol.model;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class SecurityUser implements UserDetails {

    private static final long serialVersionUID = 1L;
	private final User user;
	
	@Autowired
    public SecurityUser(User user) {
        this.user = user;
    }
	
	@Override
	public String getUsername() {
		return user.getUsername();
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
