package miaad.sgrh.user.mapper;

import miaad.sgrh.user.dto.AccountDto;
import miaad.sgrh.user.dto.UserDto;
import miaad.sgrh.user.entity.User;

public class UserMapper {
    public static UserDto mapToUserDto(User user){
        return new UserDto(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getGender(),
                user.getPhone(),
                null
        );
    }

    public static User mapToUser(UserDto userDto){
        return new User(
                userDto.getId(),
                userDto.getFirstName(),
                userDto.getLastName(),
                userDto.getEmail(),
                userDto.getGender(),
                userDto.getPhone()
        );
    }

    public static AccountDto mapToAccountDto(UserDto userDto){
        return new AccountDto(
                userDto.getId(),
                userDto.getEmail(),
                userDto.getPassword(),
                "stagiaire",
                false
        );
    }
}
