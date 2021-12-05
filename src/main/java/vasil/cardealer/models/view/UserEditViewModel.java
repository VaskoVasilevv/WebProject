package vasil.cardealer.models.view;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserEditViewModel {

    private String username;
    private String oldPassword;
    private String password;
    private String confirmPassword;
    private String email;
}
