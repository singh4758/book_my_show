package com.stack_innovation.book_my_show.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class ShowSeatType extends BaseModel {

    @ManyToOne
    private Show show;
    @ManyToOne
    private SeatType seatType;
    private int price;
}

