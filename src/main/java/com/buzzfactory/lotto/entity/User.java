package com.buzzfactory.lotto.entity;

import java.util.*;
import javax.persistence.*;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@javax.persistence.Entity
public class User implements Entity, UserDetails {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true, length = 16, nullable = false)
    private String name;

    @Column(length = 64, nullable = false)
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> roles = new HashSet<String>();

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name="Configuration",
            joinColumns=@JoinColumn(name="OWNER_ID")
    )
    private Set<Configuration> configuration = new HashSet<Configuration>();

    protected User() {

        /* Reflection instantiation */
    }

    public User(String name, String passwordHash) {

        this.name = name;
        this.password = passwordHash;
    }

    public Long getId() {

        return this.id;
    }

    public void setId(Long id) {

        this.id = id;
    }

    public String getName() {

        return this.name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public Set<String> getRoles() {

        return this.roles;
    }

    public void setRoles(Set<String> roles) {

        this.roles = roles;
    }

    public void addRole(String role) {

        this.roles.add(role);
    }

    @Override
    public String getPassword() {

        return this.password;
    }

    public void setPassword(String password) {

        this.password = password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Set<String> roles = this.getRoles();

        if (roles == null) {
            return Collections.emptyList();
        }

        Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
        for (String role : roles) {
            authorities.add(new SimpleGrantedAuthority(role));
        }

        return authorities;
    }

    @Override
    public String getUsername() {

        return this.name;
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



    public void addConfiguration(Configuration configuration) {
        this.getConfiguration().add(configuration);
    }

    public Set<Configuration> getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Set<Configuration> configuration) {
        this.configuration = configuration;
    }
}
