package cn.com.taiji.learn.sshelloworld.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.util.Calendar;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private Long id;

    @NotEmpty(message = "Username is required.")
    private String username;

    @NotEmpty(message = "Email is required.")
    private String email;

    private Calendar created = Calendar.getInstance();
}
