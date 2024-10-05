package com.lnct.miniblog.security;

import com.lnct.miniblog.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

/**
 * Custom UserDetails class that wraps the User entity to be used by Spring Security.
 */
public class CustomUserDetails implements UserDetails {
    private final User user;

    public CustomUserDetails(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // This method returns the authorities granted to the user.
        // If your application has roles, you can map them to GrantedAuthority objects here.
        return Collections.emptyList();  // Return an empty list if your app doesn't use roles.
    }

    @Override
    public String getPassword() {
        // Returns the password used for authenticating the user.
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        // Returns the username used in user authentication.
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        // Indicates whether the user's account has expired. An expired account cannot be authenticated.
        return true;  // Assuming the account is never expired for simplicity.
    }

    @Override
    public boolean isAccountNonLocked() {
        // Indicates whether the user is locked or unlocked. A locked user cannot be authenticated.
        return true;  // Assuming the account is never locked for simplicity.
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // Indicates whether the user's credentials (password) have expired.
        return true;  // Assuming the credentials never expire for simplicity.
    }

    @Override
    public boolean isEnabled() {
        // Indicates whether the user is enabled or disabled. A disabled user cannot be authenticated.
        return true;  // Assuming the user is always enabled for simplicity.
    }

    public User getUser() {
        // Provides access to the full User entity if needed for business logic.
        return user;
    }
}
