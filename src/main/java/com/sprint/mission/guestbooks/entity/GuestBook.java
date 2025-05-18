package com.sprint.mission.guestbooks.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "guestbooks")
@Getter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GuestBook {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column
  private String name;

  @Column
  private String title;

  @Column
  private String content;

  @Column
  private String imageUrl;

  @CreatedDate
  @Column(columnDefinition = "timestamp with time zone")
  private Instant createdAt;

  public GuestBook(String name, String title, String content, String imageUrl) {
    this.name = name;
    this.title = title;
    this.content = content;
    this.imageUrl = imageUrl;
  }
}
