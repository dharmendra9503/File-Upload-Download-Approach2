package com.example.fileuploaddownloadapproach2.repository;

import com.example.fileuploaddownloadapproach2.model.FileData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FileDataRepository extends JpaRepository<FileData, Long> {

    Optional<FileData> findByName(String fileName);

}
