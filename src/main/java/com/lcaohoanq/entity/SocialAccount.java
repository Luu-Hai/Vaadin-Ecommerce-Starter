package com.lcaohoanq.entity;

import com.lcaohoanq.enums.SocialAccountProviderEnum;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
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
@ToString(exclude = {"user"})
@Table(name = "social_accounts")
public class SocialAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "provider")
    private SocialAccountProviderEnum provider;

    @NotNull
    @Column(name = "provider_id")
    private int providerId;

    @NotNull
    @Column(name = "email")
    private String email;

    @NotNull
    @Column(name = "name")
    private String displayName;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    public SocialAccount(SocialAccountProviderEnum provider, int providerId, String email, String displayName, User user) {
        this.provider = provider;
        this.providerId = providerId;
        this.email = email;
        this.displayName = displayName;
        if (user != null) {
            this.id = user.getId();
            this.setUser(user);
        }
    }
}
