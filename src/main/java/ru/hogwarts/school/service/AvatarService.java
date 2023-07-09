package ru.hogwarts.school.service;

import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;

import java.io.IOException;
import java.util.List;

public interface AvatarService {
    Avatar findAvatar(Long studentId);

    Avatar findOrCreateAvatar(Long studentId);

    void upLoadAvatar(Long studentId, MultipartFile avatarFile) throws IOException;

    List<Avatar> getPage(Integer pageNumber, Integer pageSize);

}
