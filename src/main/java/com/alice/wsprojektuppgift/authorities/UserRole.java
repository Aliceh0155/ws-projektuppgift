package com.alice.wsprojektuppgift.authorities;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;

import static com.alice.wsprojektuppgift.authorities.UserPermission.*;

public enum UserRole {

    GUEST(List.of(GET.getPermission())),
    USER(List.of(GET.getPermission(),POST.getPermission())),
    ADMIN(List.of(
            GET.getPermission(),
            POST.getPermission(),
            DELETE.getPermission()));

    private final List<String> permission;

    UserRole(List<String> permission){
        this.permission = permission;
    }

    public List<String> getPermission() {
        return permission;
    }

    public List<SimpleGrantedAuthority> getAuthorities(){

            List<SimpleGrantedAuthority> authoritiesList = new ArrayList<>();

            authoritiesList.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
            authoritiesList.addAll(getPermission().stream().map(SimpleGrantedAuthority::new).toList());

            return authoritiesList;

    }
    }
