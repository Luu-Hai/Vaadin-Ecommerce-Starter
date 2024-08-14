package com.lcaohoanq.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lcaohoanq.enums.UserRoleEnum;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString(exclude = {"users"})
@Table(name="roles")
public class Role {

    @Id
    @Column(name = "id")
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(name = "name")
    private UserRoleEnum roleName;

    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<User> users = new HashSet<>();

    public Role(int id, UserRoleEnum roleName) {
        this.id = id;
        this.roleName = roleName;
    }

    public Role(UserRoleEnum roleName){
        this.roleName = roleName;
    }
}
