package org.ems.demo.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface CloudinaryService {
    String uploadImage(MultipartFile path) throws IOException;

    String uploadProfile(MultipartFile file, Long userId);

    String uploadLogo(MultipartFile file, Long companyId);
}
