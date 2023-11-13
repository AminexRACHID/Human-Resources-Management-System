package miaad.sgrh.user.service;

import jakarta.transaction.Transactional;
import miaad.sgrh.user.dto.StagiaireDto;
import miaad.sgrh.user.dto.UserDto;

public interface UserService {
    @Transactional
    public StagiaireDto createUser(UserDto userDto);

}
