package com.example.bookaro.uploads.domain;

import lombok.Getter;
import lombok.Value;

import java.io.StringReader;
import java.time.LocalDateTime;
@Value
public class Upload {
    String id;
    byte[] file;
    String contentType;
    String filename;
    LocalDateTime createdAt;

    public StringReader getContentTypeStringReader() {
        StringReader stringReader = new StringReader(contentType);
        return stringReader;
    }

}
