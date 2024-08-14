package com.lcaohoanq.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.lcaohoanq.constraint.PasswordConstraint;
import com.lcaohoanq.enums.SocialAccountProviderEnum;
import com.lcaohoanq.enums.UserGenderEnum;
import com.lcaohoanq.enums.UserRoleEnum;
import com.lcaohoanq.enums.UserStatusEnum;
import com.lcaohoanq.utils.ValidatorUtil;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString(callSuper = true, exclude = {"score", "role", "status"})
@JsonPropertyOrder({"id", "firstName", "lastName", "email", "phone", "password", "birthday",
    "address", "gender", "role", "status", "created_at", "updated_at", "avatar_url",
    "subscription", "facebook_id", "google_id"})
@JsonIgnoreProperties(ignoreUnknown = true)
@Table(name = "users")
public class User {

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    @JsonProperty("id")
    protected Long id;

    @NotNull //JSR-380 annotation
    @Column(name = "first_name", nullable = false, length = 45)
    @JsonProperty("firstName")
    protected String firstName;

    @NotNull
    @Column(name = "last_name", nullable = false, length = 45)
    @JsonProperty("lastName")
    protected String lastName;

    @Email(message = "Email should be valid")
    @Column(name = "email", unique = true)
    @JsonProperty("email")
    protected String email;

    @Pattern(regexp = "(84|0[3|5|7|8|9])[0-9]{8}", message = "Phone number should be 10 digits, Viet Nam format")
    @Column(name = "phone", unique = true)
    @JsonProperty("phone")
    protected String phone;

    @NotNull
    @Column(name = "password", nullable = false)
    @JsonProperty("password")
    @PasswordConstraint
    protected String password;

    //@NotNull, the birthday will be null if the user does not provide it
    //we can update later
    @Column(name = "birthday", nullable = false)
    @JsonProperty("birthday")
    protected String birthday;

    //@NotNull, the address will be null if the user does not provide it
    //we can update later
    @Column(name = "address", nullable = false)
    @JsonProperty("address")
    protected String address;

    @NotNull
    @Column(name = "gender", nullable = false)
    @JsonProperty("gender")
    protected UserGenderEnum gender;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id", nullable = false)
    @JsonProperty("role")
    protected Role role;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "status_id", nullable = false)
    @JsonProperty("status")
    protected Status status;

    @NotNull
    @Column(name = "created_at", nullable = false)
    @JsonProperty("created_at")
    protected String created_at;

    @NotNull
    @Column(name = "updated_at", nullable = false)
    @JsonProperty("updated_at")
    protected String updated_at;

    @Lob
    @Column(name = "avatar_url", length = 1000000)
    @JsonProperty("avatar_url")
    protected byte[] avatar_url;

    @NotNull
    @Column(name = "subscription", nullable = false)
    @JsonProperty("subscription")
    protected int subscription;

    @Column(name="facebook_id")
    @JsonProperty("facebook_id")
    protected int facebook_account_id;

    @Column(name="google_id")
    @JsonProperty("google_id")
    protected int google_account_id;

    @OneToOne(mappedBy = "user") // Refers to the user field in Score
    @JsonIgnore
    protected SocialAccount socialAccount;

    @OneToOne(mappedBy = "user") // Refers to the user field in Score
    @JsonIgnore
    //what is JsonSetter and JsonGetter
    //https://www.baeldung.com/jackson-jsonmappingexception
    protected Score score;

    @Transient
    @JsonIgnore
    protected String confirmPassword;

    public User(String email_phone, String password) {
        this.email = email_phone;
        this.password = password;
    }

    // register by frontend
    public User(String email_phone, String firstName, String lastName, String password,
        String birthday, String address, UserGenderEnum gender, Role role,
        Status status, String created_at, String updated_at, byte[] avatar_url) {
        this.email = email_phone;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.birthday = birthday;
        this.address = address;
        this.gender = gender;
        this.role = role;
        this.status = status;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.avatar_url = avatar_url;
    }

    public User(Long id, String firstName, String lastName, String email, String phone,
        String password, String birthday, String address, UserGenderEnum gender, Role role,
        Status status, String created_at, String updated_at, byte[] avatar_url,
        int subscription) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.birthday = birthday;
        this.address = address;
        this.gender = gender;
        this.role = role;
        this.status = status;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.avatar_url = avatar_url;
        this.subscription = subscription;
    }

    public User(String firstName, String lastName, String email, String phone, String password,
        String birthday, String address, UserGenderEnum gender, Role role,
        Status status, String created_at, String updated_at, byte[] avatar_url,
        int subscription) {
        this.id = -1L;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.birthday = birthday;
        this.address = address;
        this.gender = gender;
        this.role = role;
        this.status = status;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.avatar_url = avatar_url;
        this.subscription = subscription;
    }

    public User(Long id, String firstName, String lastName, String email, String phone,
        String password, String birthday, String address, UserGenderEnum gender, Role role,
        Status status, String created_at, String updated_at, byte[] avatar_url,
        int subscription, Score score) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.birthday = birthday;
        this.address = address;
        this.gender = gender;
        this.role = role;
        this.status = status;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.avatar_url = avatar_url;
        this.subscription = subscription;
        this.score = score;
    }

    public User(Long id, String firstName, String lastName, String email, String phone, String password,
        String birthday, String address, UserGenderEnum gender, Role role,
        Status status, String created_at, String updated_at, byte[] avatar_url,
        int subscription, int facebook_account_id, int google_account_id) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.birthday = birthday;
        this.address = address;
        this.gender = gender;
        this.role = role;
        this.status = status;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.avatar_url = avatar_url;
        this.subscription = subscription;
        this.facebook_account_id = facebook_account_id;
        this.google_account_id = google_account_id;
    }



    public static void main(String[] args) {
        Role role = new Role(1, UserRoleEnum.USER);
        Status status = new Status(1, UserStatusEnum.VERIFIED);

        User user = new User(1L, "hoang", "luu", "hoangdz1604@gmail.com",
            null, "", null,
            "Ho Chi Minh", UserGenderEnum.MALE, role, status,
            LocalDateTime.now().toString(), LocalDateTime.now().toString(), null, 0, 0 ,0);

        SocialAccount socialAccount = new SocialAccount(1L, SocialAccountProviderEnum.GOOGLE, SocialAccountProviderEnum.GOOGLE.getProvider(), user.getEmail(), user.getFirstName(), user);

        user.setSocialAccount(socialAccount);


        try {
            ValidatorUtil.validate(user);
            System.out.println("User is valid");
            System.out.println(user);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }
}
