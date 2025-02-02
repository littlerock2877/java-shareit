package ru.practicum.shareit.booking.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.booking.BookingStatus;
import java.time.LocalDateTime;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> getAllByBookerIdOrderByStartDesc(Long userId);

    List<Booking> getByBookerIdAndStatus(Long bookerId, BookingStatus status);

    List<Booking> getAllByItemOwnerIdAndStatus(Long ownerId, BookingStatus status);

    @Query("SELECT b FROM Booking b " +
            "WHERE b.booker.id = :id AND b.end < :currentTime AND upper(b.status) = UPPER('APPROVED')" +
            "ORDER BY b.start DESC")
    List<Booking> getByBookerIdStatePast(@Param("id") Long id, @Param("currentTime") LocalDateTime currentTime);

    @Query("SELECT b FROM Booking b WHERE b.booker.id = :userId AND b.end >= :currentTime AND :currentTime >= b.start " +
            "ORDER BY b.start DESC")
    List<Booking> getByBookerIdStateCurrent(@Param("userId") Long userId, @Param("currentTime") LocalDateTime currentTime);

    @Query("SELECT b FROM Booking b WHERE b.booker.id = :userId AND b.start > :currentTime ORDER BY b.start DESC")
    List<Booking> getFuture(@Param("userId") Long userId, @Param("currentTime") LocalDateTime currentTime);

    @Query("SELECT b FROM Booking b JOIN b.item i ON b.item = i WHERE i.owner.id = :ownerId ORDER BY b.start DESC")
    List<Booking> getOwnerAll(Long ownerId);

    @Query("SELECT b FROM Booking b JOIN b.item i ON b.item = i WHERE  i.owner.id = :userId AND b.start > :currentTime " +
            "ORDER BY b.start DESC")
    List<Booking> getOwnerFuture(@Param("userId") Long userId, @Param("currentTime") LocalDateTime currentTime);

    @Query("SELECT b FROM Booking b JOIN b.item i ON b.item = i WHERE i.owner.id = :userId " +
            "AND b.start <= :currentTime AND b.end >= :currentTime ORDER BY b.start DESC ")
    List<Booking> getOwnerCurrent(@Param("userId") Long userId, @Param("currentTime") LocalDateTime currentTime);

    @Query("SELECT b FROM Booking b JOIN b.item i ON b.item = i WHERE i.owner.id = :userId AND b.end < :currentTime")
    List<Booking> getOwnerPast(@Param("userId") Long userId, @Param("currentTime") LocalDateTime currentTime);



    @Query("SELECT b FROM Booking b JOIN Item i ON b.item.id = i.id " +
            "WHERE b.booker.id = :bookerId AND i.id = :itemId")
    List<Booking> getAllUserBookings(Long bookerId, Long itemId);
}