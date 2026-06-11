package com.example.khetibadi.service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.khetibadi.model.Booking;
import com.example.khetibadi.model.BookingStatus;
import com.example.khetibadi.model.Machine;
import com.example.khetibadi.model.User;
import com.example.khetibadi.repository.BookingRepository;
import com.example.khetibadi.repository.MachineRepository;
import com.example.khetibadi.repository.UserRepository;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final MachineRepository machineRepository;
    private final UserRepository userRepository;

    public BookingService(BookingRepository bookingRepository, MachineRepository machineRepository,
            UserRepository userRepository) {
        this.bookingRepository = bookingRepository;
        this.machineRepository = machineRepository;
        this.userRepository = userRepository;
    }

    // Create Booking
    public Booking createBooking(Long userId, Long machineId) {

        User user = userRepository.findById(userId).orElseThrow();
        Machine machine = machineRepository.findById(machineId).orElseThrow();

        List<Booking> activeBookings = bookingRepository.findByMachineAndStatusIn(
                machine,
                List.of(
                        BookingStatus.CONFIRMED,
                        BookingStatus.PENDING));
        Booking booking = new Booking();

        booking.setUser(user);
        booking.setMachine(machine);
        booking.setBookingTime(LocalDateTime.now());

        if (activeBookings.isEmpty()) {
            booking.setStatus(BookingStatus.CONFIRMED);
            booking.setQueuePosition(0);
        } else {
            long pendingCount = activeBookings.stream()
                    .filter(b -> b.getStatus() == BookingStatus.PENDING)
                    .count();

            booking.setStatus(BookingStatus.PENDING);
            booking.setQueuePosition((int) pendingCount + 1);
        }

        return bookingRepository.save(booking);
    }

    // Complete Booking
    public Booking completeBooking(Long bookingId) {

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        Machine machine = booking.getMachine();

        booking.setStatus(BookingStatus.COMPLETED);
        bookingRepository.save(booking);

        List<Booking> pendingBookings = bookingRepository.findByMachineAndStatus(
                machine,
                BookingStatus.PENDING);

        if (!pendingBookings.isEmpty()) {

            pendingBookings.sort(
                    Comparator.comparing(Booking::getQueuePosition));

            Booking nextBooking = pendingBookings.get(0);

            nextBooking.setStatus(BookingStatus.CONFIRMED);
            nextBooking.setQueuePosition(0);

            bookingRepository.save(nextBooking);
        }

        updateQueuePositions(machine);

        return booking;
    }

    // Cancel Booking
    public void cancelBooking(Long bookingId) {

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        BookingStatus oldStatus = booking.getStatus();

        Machine machine = booking.getMachine();

        booking.setStatus(BookingStatus.CANCELLED);
        bookingRepository.save(booking);

        List<Booking> pendingBookings = bookingRepository.findByMachineAndStatus(
                machine,
                BookingStatus.PENDING);

        if (oldStatus == BookingStatus.CONFIRMED
                && !pendingBookings.isEmpty()) {

            pendingBookings.sort(
                    Comparator.comparing(Booking::getQueuePosition));

            Booking nextBooking = pendingBookings.get(0);

            nextBooking.setStatus(BookingStatus.CONFIRMED);
            nextBooking.setQueuePosition(0);

            bookingRepository.save(nextBooking);
        }

        updateQueuePositions(machine);
    }

    private void updateQueuePositions(Machine machine) {

        List<Booking> pendingBookings = bookingRepository.findByMachineAndStatus(
                machine,
                BookingStatus.PENDING);

        pendingBookings.sort(
                Comparator.comparing(Booking::getQueuePosition));

        int position = 1;

        for (Booking booking : pendingBookings) {

            booking.setQueuePosition(position++);

            bookingRepository.save(booking);
        }
    }

    public List<Booking> getBookings() {
        return bookingRepository.findAll();
    }

    public List<Booking> getUserBookings(Long userId) {
        User user = userRepository.findById(userId).orElseThrow();
        return bookingRepository.findByUser(user);
    }

}
