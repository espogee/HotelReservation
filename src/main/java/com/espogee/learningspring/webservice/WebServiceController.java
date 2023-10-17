package com.espogee.learningspring.webservice;


import com.espogee.learningspring.business.ReservationService;
import com.espogee.learningspring.business.RoomReservation;
import com.espogee.learningspring.data.Guest;
import com.espogee.learningspring.data.Room;
import com.espogee.learningspring.util.DateUtils;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api")
public class WebServiceController {
    private final DateUtils dateUtils;
    private final ReservationService reservationService;

    public WebServiceController(DateUtils dateUtils, ReservationService reservationService) {
        this.dateUtils = dateUtils;
        this.reservationService = reservationService;
    }

    @RequestMapping(path = "/reservations", method = RequestMethod.GET )
    public List<RoomReservation> getReservations(@RequestParam(value = "date", required = false) String dateString){
        Date date = this.dateUtils.createDateFromDateString(dateString);
        return this.reservationService.getRoomReservationsForDate(date);
    }

    @RequestMapping(path = "/guests", method = RequestMethod.GET)
    public List<Guest> getGuests(){
        return this.reservationService.getHotelGuests();
    }

    @RequestMapping(path = "/rooms", method = RequestMethod.GET)
    public List<Room> getRooms(){
        return this.reservationService.getHotelRooms();
    }

    @RequestMapping(path = "/guests", method = RequestMethod.POST)
    public String addGuest(@Param(value = "firstName") String firstName, @Param(value = "lastName") String lastName,
                           @Param(value = "email") String email, @Param(value = "address") String address,
                           @Param(value = "country") String country, @Param(value = "state") String state,
                           @Param(value = "phoneNumber") String phoneNumber){
        Guest guest = new Guest();
        guest.setFirstName(firstName);
        guest.setLastName(lastName);
        guest.setEmailAddress(email);
        guest.setAddress(address);
        guest.setCountry(country);
        guest.setState(state);
        guest.setPhoneNumber(phoneNumber);
        this.reservationService.addHotelGuest(guest);
        return "Guest added";
    }

    @DeleteMapping(path = "/guests/{id}")
    public String deleteGuest(@PathVariable(value = "id") long id){
        Guest guest = this.reservationService.getHotelGuest(id);
        this.reservationService.deleteHotelGuest(guest);
        return "Guest deleted";
    }

    @PatchMapping(path = "/guests/{id}")
    public String updateGuest(@PathVariable(value = "id") long id, @Param(value = "firstName") String firstName,
                              @Param(value = "lastName") String lastName, @Param(value = "email") String email,
                              @Param(value = "address") String address, @Param(value = "country") String country,
                              @Param(value = "state") String state, @Param(value = "phoneNumber") String phoneNumber){
        Guest guest = this.reservationService.getHotelGuest(id);
        guest.setFirstName(firstName);
        guest.setLastName(lastName);
        guest.setEmailAddress(email);
        guest.setAddress(address);
        guest.setCountry(country);
        guest.setState(state);
        guest.setPhoneNumber(phoneNumber);
        this.reservationService.updateHotelGuest(guest);
        return "Guest updated";
    }
}
