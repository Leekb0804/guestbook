package com.sprint.mission.guestbooks.dto;

import java.util.List;

public record PageResponse<T> (
  List<T> content,
  int totalPages,
  int totalElements,
  int size,
  int number
){

}
