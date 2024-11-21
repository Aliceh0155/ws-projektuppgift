package com.alice.wsprojektuppgift.entity;

import com.alice.wsprojektuppgift.authorities.UserRole;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "users")
public class CustomUser {


    private String username;

    private String password;

    private UserRole userRole;
    private boolean isAccountNonExpired;
    private boolean isAccountNonLocked;
    private boolean isCredentialsNonExpired;
    private boolean isEnabled;

    // Lista av favoritkaraktärer (referenser till FavouriteCharacterEntity)
    @DBRef
    private List<FavouriteCharacterEntity> favouriteCharacters = new ArrayList<>();

    public CustomUser() {
    }

    public CustomUser(String username, String password, UserRole userRole, boolean isAccountNonExpired, boolean isAccountNonLocked, boolean isCredentialsNonExpired, boolean isEnabled) {
        this.username = username;
        this.password = password;
        this.userRole = userRole;
        this.isAccountNonExpired = isAccountNonExpired;
        this.isAccountNonLocked = isAccountNonLocked;
        this.isCredentialsNonExpired = isCredentialsNonExpired;
        this.isEnabled = isEnabled;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    //["ROLE_ADMIN","GET","POST"]
    @JsonIgnore
    public List<SimpleGrantedAuthority>  getAuthorities(){
     return userRole.getAuthorities();
    }
    //["GET","POST"]
    @JsonIgnore
    public List<String> getListOfPermissions(){
        return userRole.getPermission();
    }


    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public boolean isAccountNonExpired() {
        return isAccountNonExpired;
    }

    public void setAccountNonExpired(boolean accountNonExpired) {
        isAccountNonExpired = accountNonExpired;
    }

    public boolean isAccountNonLocked() {
        return isAccountNonLocked;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        isAccountNonLocked = accountNonLocked;
    }

    public boolean isCredentialsNonExpired() {
        return isCredentialsNonExpired;
    }

    public void setCredentialsNonExpired(boolean credentialsNonExpired) {isCredentialsNonExpired = credentialsNonExpired;}

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    public List<FavouriteCharacterEntity> getFavouriteCharacters() {
        return favouriteCharacters;
    }

    public void setFavouriteCharacters(List<FavouriteCharacterEntity> favouriteCharacters) {
        this.favouriteCharacters = favouriteCharacters;
    }
}
