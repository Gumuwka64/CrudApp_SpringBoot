package gumuwka.crud_on_boot.model;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;


import java.util.Set;

@Entity
@Table(name = "roles")
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @Override
    public String getAuthority() {
        return name;
    }

    public Role(){}

    public Role(String name){
        this.name = name.startsWith("ROLE_") ? name : "ROLE_" + name.toUpperCase();
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name.startsWith("ROLE_") ? name : "ROLE_" + name.toUpperCase();
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}

