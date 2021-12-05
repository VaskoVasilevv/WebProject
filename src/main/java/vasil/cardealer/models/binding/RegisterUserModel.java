package vasil.cardealer.models.binding;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RegisterUserModel {
    private String username;
    private String email;
    private String password;
    private String confirmPassword;
}
