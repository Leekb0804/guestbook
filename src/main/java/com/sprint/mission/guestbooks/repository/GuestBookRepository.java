package com.sprint.mission.guestbooks.repository;

import com.sprint.mission.guestbooks.entity.GuestBook;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface GuestBookRepository extends JpaRepository<GuestBook, UUID> {
  Page<GuestBook> findAllByOrderByCreatedAtDesc(Pageable pageable);
}
