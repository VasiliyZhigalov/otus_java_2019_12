package com.vasiliyzhigalov.domain;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class User implements Serializable {
    private final long serialVersionUID = 1L;
    private long id;
    private String name;
    private String login;
    private String password;
}
