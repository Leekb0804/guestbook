package com.sprint.mission.guestbooks.controller;

import com.sprint.mission.guestbooks.dto.PageResponse;
import com.sprint.mission.guestbooks.entity.GuestBook;
import com.sprint.mission.guestbooks.service.GuestBookService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/guestbooks")
@RequiredArgsConstructor
public class Controller {
  private final GuestBookService guestBookService;

  @PostMapping
  public ResponseEntity<GuestBook> addGuestBook(
      @RequestParam("name") String name,
      @RequestParam("title") String title,
      @RequestParam("content") String content,
      @RequestParam(name = "image", required = false) MultipartFile image
  ) {
    return ResponseEntity
        .status(HttpStatus.OK)
        .body(guestBookService.addGuestBook(name, title, content, image));
  }

  @GetMapping
  public ResponseEntity<PageResponse<GuestBook>> getAllGuestBooks(
    @PageableDefault(
      page = 0,
      size = 5
    ) Pageable pageable
  ) {
    return ResponseEntity
        .status(HttpStatus.OK)
        .body(guestBookService.getAllGuestBooks(pageable));
  }

  @GetMapping("/{id}")
  public ResponseEntity<GuestBook> getGuestBookById(@PathVariable UUID id) {
    return ResponseEntity
        .status(HttpStatus.OK)
        .body(guestBookService.getGuestBookById(id));
  }
}
