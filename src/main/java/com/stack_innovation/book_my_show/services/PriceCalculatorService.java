package com.stack_innovation.book_my_show.services;

import com.stack_innovation.book_my_show.models.Show;
import com.stack_innovation.book_my_show.models.ShowSeat;
import com.stack_innovation.book_my_show.models.ShowSeatType;
import com.stack_innovation.book_my_show.repositories.ShowSeatTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class PriceCalculatorService {

    ShowSeatTypeRepository showSeatTypeRepository;

    @Autowired
    public PriceCalculatorService(
            ShowSeatTypeRepository showSeatTypeRepository
    ) {
        this.showSeatTypeRepository = showSeatTypeRepository;
    }

    public int calculatePrice(List<ShowSeat> showSeats, Show show){
        List<ShowSeatType> showSeatTypes = showSeatTypeRepository.findAllByShow(show);
        int amount = 0;
        for(ShowSeat showSeat: showSeats){
            for(ShowSeatType showSeatType: showSeatTypes) {
                if (showSeat.getSeat().equals(showSeatType.getSeatType())) {
                    amount += showSeatType.getPrice();
                    break;
                }
            }
        }
        return  amount;
    }
}
