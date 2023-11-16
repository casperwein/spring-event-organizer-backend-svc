package com.eventorganizer.app.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@Service
public class FileService {
    private final Path path;
    private String pathDir = "public\\event\\";

    public FileService() {
        this.path = Path.of(pathDir).toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.path);
        } catch (IOException e) {
            throw new RuntimeException("Gagal membuat folder untuk menyimpan file event!");
        }
    }

    public String saveFile(MultipartFile file){
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            Path location = this.path.resolve(fileName);
            Files.copy(file.getInputStream(), location, StandardCopyOption.REPLACE_EXISTING);

            String pathName = "/public/event/" + fileName;
            return pathName;
        } catch (IOException e) {
            throw new RuntimeException("Tidak bisa menyimpan file " + fileName + "Tolong dicoba lagi! ", e );
        }
    }
}
