package ftn.project.web.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class ChangePasswordDto {

    @NotBlank(message = "You must enter current password!")
    @NotNull
    private String currentPassword;
    @NotBlank(message = "You must enter New Password!")
    private String newPassword;
    @NotBlank(message = "You must enter Repeated New Password!")
    private String repeatedNewPassword;

}
