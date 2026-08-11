package com.example.khetibadi.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.khetibadi.model.Booking;
import com.example.khetibadi.model.BookingStatus;
import com.example.khetibadi.model.Machine;
import com.example.khetibadi.model.User;
import com.example.khetibadi.repository.BookingRepository;
import com.example.khetibadi.repository.MachineRepository;
import com.example.khetibadi.repository.UserRepository;
import com.example.khetibadi.util.DistanceCalculator;


@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final MachineRepository machineRepository;
    private final UserRepository userRepository;
    private final DispatchService dispatchService;


    public BookingService(
            BookingRepository bookingRepository, 
            MachineRepository machineRepository, 
            UserRepository userRepository,
            DispatchService dispatchService) {
        this.bookingRepository = bookingRepository;
        this.machineRepository = machineRepository;
        this.userRepository = userRepository;
        this.dispatchService = dispatchService;
    }

    //Create Booking when machine already selected
    public Booking createBooking(Long userId, Long machineId){

        User user = userRepository.findById(userId).orElseThrow();
        Machine machine = machineRepository.findById(machineId).orElseThrow();

        double distance = DistanceCalculator.calculate(
            user.getLatitude(),
            user.getLongitude(),
            machine.getLatitude(),
            machine.getLongitude()
        );


        List<Booking> queue = bookingRepository.findByMachine(machine);

        Booking booking = new Booking();
        booking.setUser(user);
        booking.setMachine(machine);
        booking.setBookingTime(LocalDateTime.now());
        booking.setDistance(distance);

        if (queue.isEmpty()) {
            booking.setStatus(BookingStatus.CONFIRMED);
            booking.setQueuePosition(0);
        }else{
            if (distance < 3) {
                booking.setPriority(true);
            }

            booking.setStatus(BookingStatus.QUEUED);

            int position = calculateQueuePosition(queue, booking);
            booking.setQueuePosition(position)  ;
        }

        return bookingRepository.save(booking);
    }


    //2. method to create booking based on machine type and nearest machine
    public Booking createBooking(Long userId, String machineType){

    
        User user = userRepository.findById(userId).orElseThrow();

        Machine machine = dispatchService.findNearestMachine(user, machineType);

        double distance = DistanceCalculator.calculate(user.getLatitude(),
            user.getLongitude(),
            machine.getLatitude(),
            machine.getLongitude()
        );

        List<Booking> queue = bookingRepository.findByMachine(machine);
        Booking booking = new Booking();
        booking.setUser(user);
        booking.setMachine(machine);
        booking.setDistance(distance);
        booking.setBookingTime(LocalDateTime.now());

        if (queue.isEmpty()) {
            booking.setStatus(BookingStatus.CONFIRMED);
            booking.setQueuePosition(0);
        }else{
            booking.setStatus(BookingStatus.QUEUED);
            booking.setQueuePosition(queue.size());
        }
        
        return bookingRepository.save(booking);
    }

    private int calculateQueuePosition(List<Booking> queue, Booking newBooking) {
        int position = 1;

        for (Booking b : queue) {
            if (Boolean.TRUE.equals(newBooking.getPriority()) && !Boolean.TRUE.equals(b.getPriority())) {
                break;
            }

            if (newBooking.getDistance()<b.getDistance()) {
                break;
            }
            position++;
        }

        return position;
    }

    //Complete Booking
    public Booking completeBooking(Long bookingId){
        
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(()-> new RuntimeException("Booking not found"));
        
        booking.setStatus(BookingStatus.COMPLETED);
        bookingRepository.save(booking);

        Machine machine = booking.getMachine();

        List<Booking> queued = bookingRepository.findByMachineAndStatus(machine, BookingStatus.QUEUED);

        if (!queued.isEmpty()) {
            Booking nextBooking = queued.get(0);
            nextBooking.setStatus(BookingStatus.CONFIRMED);
            nextBooking.setQueuePosition(0);

            bookingRepository.save(nextBooking);
        }

        return booking;
    }

    //Cancel Booking
    public void cancelBooking(Long bookingId){
        Booking booking = bookingRepository.findById(bookingId)
            .orElseThrow(()-> new RuntimeException("Booking not found"));

        Machine machine = booking.getMachine();

        booking.setStatus(BookingStatus.CANCELLED);
        bookingRepository.save(booking);

        List<Booking> pendingBookings = bookingRepository.findByMachineAndStatus(machine, BookingStatus.PENDING);

        if (!pendingBookings.isEmpty()) {
            Booking nextBooking = pendingBookings.get(0);

            nextBooking.setStatus(BookingStatus.CONFIRMED);
            nextBooking.setQueuePosition(0);

            bookingRepository.save(nextBooking);
        }
    }

    

    public List<Booking> getBookings(){
        return bookingRepository.findAll();
    }

    public List<Booking> getUserBookings(Long userId){
        User user = userRepository.findById(userId).orElseThrow();
        return bookingRepository.findByUser(user);
    }

}
