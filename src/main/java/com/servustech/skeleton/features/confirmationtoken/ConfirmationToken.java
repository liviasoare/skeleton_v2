package com.servustech.skeleton.features.confirmationtoken;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.servustech.skeleton.features.account.User;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "confirmation_tokens")
public class ConfirmationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String value;

    @CreatedDate
    private LocalDateTime createdOn;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;

    public ConfirmationToken(String value, User account) {
        this.value = value;
        this.user = account;
    }
}
