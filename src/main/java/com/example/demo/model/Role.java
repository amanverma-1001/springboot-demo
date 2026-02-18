package com.example.demo.model;

//import java.security.Permissions;
import com.example.demo.model.Permissions;
import java.util.Set;

public enum Role{
    ADMIN(Set.of(Permissions.USER_READ,Permissions.USER_WRITE,Permissions.USER_DELETE)),
    USER(Set.of(Permissions.USER_READ));

    private final Set<Permissions> permissions;
    Role(Set<Permissions> permissions) {
        this.permissions = permissions;
    }

    public Set<Permissions> getPermissions(){
        return permissions;
    }
}

