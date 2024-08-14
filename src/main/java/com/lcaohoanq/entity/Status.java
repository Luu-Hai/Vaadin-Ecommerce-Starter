package com.lcaohoanq.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lcaohoanq.enums.UserStatusEnum;
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
@ToString(exclude = {"users"})
@Entity
@Table(name = "user_status")
public class Status {

    @Id
    @Column(name = "id")
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(name = "name")
    private UserStatusEnum statusName;

    @OneToMany(mappedBy = "status", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<User> users = new HashSet<>();

    public Status (int id, UserStatusEnum statusName) {
        this.id = id;
        this.statusName = statusName;
    }

    public Status (UserStatusEnum statusName) {
        this.statusName = statusName;
    }

}