package com.sprint.mission.guestbooks.service;

import com.sprint.mission.guestbooks.dto.PageResponse;
import com.sprint.mission.guestbooks.entity.GuestBook;
import com.sprint.mission.guestbooks.repository.GuestBookRepository;
import java.io.IOException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Service
@RequiredArgsConstructor
public class GuestBookService {
  private final S3Client s3Client;
  private final GuestBookRepository guestBookRepository;

  @Value("${aws.s3.bucket}")
  private String bucketName;

  @Value("${aws.s3.base-url}")
  private String baseUrl;

  public GuestBook addGuestBook(
      String name,
      String title,
      String content,
      MultipartFile image
  ) {
    String s3Url = null;

    try {
      String fileName = image.getOriginalFilename();
      String contentType = image.getContentType();
      long size = image.getSize();

      // S3에 저장할 고유한 키 생성
      String s3Key = UUID.randomUUID().toString() + "-" + fileName;

      // S3에 파일 업로드
      PutObjectRequest putObjectRequest = PutObjectRequest.builder()
          .bucket(bucketName)
          .key(s3Key)
          .contentType(contentType)
          .build();

      s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(image.getInputStream(), size));

      // S3 URL 생성
      s3Url = baseUrl + "/" + s3Key;
    } catch (IOException e) {
      throw new RuntimeException("Failed to upload file", e);
    }

    GuestBook newGuestBook = new GuestBook(name, title, content, s3Url);
    return guestBookRepository.save(newGuestBook);
  }

  public PageResponse<GuestBook> getAllGuestBooks (Pageable pageable) {
    Page<GuestBook> page = guestBookRepository.findAllByOrderByCreatedAtDesc(pageable);

    return new PageResponse<>(
        page.getContent(),
        page.getTotalPages(),
        (int) page.getTotalElements(),
        page.getSize(),
        page.getNumber()
    );
  }

  public GuestBook getGuestBookById(UUID id) {
    return guestBookRepository.findById(id).orElseThrow(() -> new RuntimeException("GuestBook not found"));
  }
}
