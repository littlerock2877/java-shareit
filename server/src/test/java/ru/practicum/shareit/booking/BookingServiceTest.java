package ru.practicum.shareit.booking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import ru.practicum.shareit.booking.dto.BookingOutputDto;
import ru.practicum.shareit.booking.service.BookingServiceImpl;
import ru.practicum.shareit.booking.storage.BookingRepository;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.storage.ItemRepository;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.storage.UserRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@SpringBootTest
public class BookingServiceTest {
    @InjectMocks
    private BookingServiceImpl bookingService;

    @Mock
    private BookingRepository bookingRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private BookingMapper bookingMapper;

    private User user1;
    private Item item;
    private Booking booking;
    private final LocalDateTime start = LocalDateTime.now();
    private final LocalDateTime end = LocalDateTime.now().plusDays(1);

    @BeforeEach
    void beforeEach() {
        user1 = new User(1L, "Alex", "alex.b@yandex.ru");
        item = new Item(1L, "bag", "description", true, user1,
                null);
        booking = new Booking(1L, start, end, item, user1, BookingStatus.WAITING);
    }

    @Test
    void getById_shouldReturnBookingDto() {
        Long bookingId = 1L;
        Long userId = 1L;
        when(bookingRepository.findById(anyLong())).thenReturn(Optional.of(booking));
        when(bookingMapper.toOutputDto(any(), any(), any())).thenAnswer(invocation -> {
            Booking booking = invocation.getArgument(0);
            Item item = invocation.getArgument(1);
            User user = invocation.getArgument(2);
            return new BookingOutputDto(booking.getId(), booking.getStart(), booking.getEnd(), item, user, booking.getStatus());
        });

        BookingOutputDto result = bookingService.getBooking(userId, bookingId);

        assertEquals(1L, result.getId());
        assertEquals(start, result.getStart());
        assertEquals(end, result.getEnd());
        assertEquals(BookingStatus.WAITING, result.getStatus());
        assertEquals(1L, result.getItem().getId());
        assertEquals("bag", result.getItem().getName());
        assertEquals(1L, result.getBooker().getId());
        assertEquals("Alex", result.getBooker().getName());
    }

    @Test
    void getById_shouldReturnUserNotFoundException() {
        when(bookingRepository.findById(anyLong())).thenThrow(NotFoundException.class);

        assertThrows(NotFoundException.class, () -> bookingService.getBooking(1L, 1L));
    }

    @Test
    void getAllByOwnerId_whenStateIsAll() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user1));
        when(bookingRepository.getOwnerAll(anyLong())).thenReturn(List.of(booking));

        List<BookingOutputDto> result = bookingService.findAllByOwner(1L, State.ALL);

        assertEquals(1, result.size());
    }

    @Test
    void getAllByOwnerId_whenStateIsWAITING() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user1));
        when(bookingRepository.getAllByItemOwnerIdAndStatus(anyLong(), any())).thenReturn(List.of(booking));

        List<BookingOutputDto> result = bookingService.findAllByOwner(1L, State.WAITING);

        assertEquals(1, result.size());
    }

    @Test
    void getAllByOwnerId_whenStateIsREJECTED() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user1));
        when(bookingRepository.getAllByItemOwnerIdAndStatus(anyLong(), any())).thenReturn(List.of(booking));

        List<BookingOutputDto> result = bookingService.findAllByOwner(1L, State.WAITING);

        assertEquals(1, result.size());
    }

    @Test
    void getAllByOwnerId_shouldReturnUserNotFoundException() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> bookingService.findAllByOwner(999L, State.ALL));
    }
}