package pojo;

import lombok.Data;
import java.util.List;

@Data
public class LoginResponse {
    private String token;
    private String type;
    private String username;
    private String email;
    private List<String> roles;
    private int id;
}
