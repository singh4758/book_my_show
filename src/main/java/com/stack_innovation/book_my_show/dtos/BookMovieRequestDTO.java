package com.stack_innovation.book_my_show.dtos;

import java.util.List;

public class BookMovieRequestDTO {
    private Long userId;
    private Long showId;
    private List<Long> seatIds;

    public Long getUserId() {
        return userId;
    }

    public Long getShowId() {
        return showId;
    }

    public List<Long> getSeatIds() {
        return seatIds;
    }
}
