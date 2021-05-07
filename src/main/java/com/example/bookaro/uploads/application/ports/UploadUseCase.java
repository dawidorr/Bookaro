package com.example.bookaro.uploads.application.ports;

import com.example.bookaro.uploads.domain.Upload;
import lombok.AllArgsConstructor;
import lombok.Value;

import java.util.Optional;

public interface UploadUseCase {
    Upload save(SaveUploadCommand command);

    Optional<Upload> getById(String id);

    void removeById(String id);

    @Value
    @AllArgsConstructor
    class SaveUploadCommand {
        String filename;
        byte[] file;
        String contentType;
    }

}