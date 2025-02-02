package ru.practicum.shareit.booking.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.booking.BookingMapper;
import ru.practicum.shareit.booking.BookingStatus;
import ru.practicum.shareit.booking.State;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingOutputDto;
import ru.practicum.shareit.booking.storage.BookingRepository;
import ru.practicum.shareit.exception.NotAvailableException;
import ru.practicum.shareit.exception.NotEnoughRightsException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.storage.ItemRepository;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.storage.UserRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final BookingMapper bookingMapper;

    @Override
    public BookingOutputDto createBooking(BookingDto bookingDto, Long userId) {
        User booker = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(String.format("User with id %d is not found", userId)));
        Item item = itemRepository.findById(bookingDto.getItemId())
                .orElseThrow(() -> new NotFoundException(String.format("Item with id %d is not found", bookingDto.getItemId())));
        if (booker.getId().equals(item.getOwner().getId())) {
            throw new NotAvailableException("Owner cant booked this item");
        }
        if (!item.getAvailable()) {
            throw new NotAvailableException(String.format("Item with id %d is not available", bookingDto.getItemId()));
        }
        bookingDto.setStatus(BookingStatus.WAITING);
        bookingDto.setId(0L);
        Booking booking = bookingRepository.save(bookingMapper.toModel(bookingDto, booker, item));
        return bookingMapper.toOutputDto(booking, item, booker);
    }

    @Override
    public BookingOutputDto approveBooking(Long userId, Long bookingId, Boolean approved) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new NotFoundException(String.format("Booking with id %d doesn't exist", bookingId)));
        if (!booking.getItem().getOwner().getId().equals(userId)) {
            throw new NotEnoughRightsException(String.format("User with id %d can't update item with id %d", userId, booking.getItem().getId()));
        }
        if (approved) {
            booking.setStatus(BookingStatus.APPROVED);
        } else {
            booking.setStatus(BookingStatus.REJECTED);
        }
        return bookingMapper.toOutputDto(bookingRepository.save(booking), booking.getItem(), booking.getBooker());
    }

    @Override
    public BookingOutputDto getBooking(Long userId, Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new NotFoundException(String.format("Booking with id %d doesn't exist", bookingId)));
        if (!booking.getBooker().getId().equals(userId) && booking.getItem().getOwner().getId().equals(userId)) {
            throw new NotEnoughRightsException(String.format("User with id %d can't update booking with id %d", userId, bookingId));
        }
        return bookingMapper.toOutputDto(booking, booking.getItem(), booking.getBooker());
    }

    @Override
    public List<BookingOutputDto> findAllByBooker(Long userId, State state) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(String.format("User with id %d is not found", userId)));
        List<Booking> bookings = List.of();
        LocalDateTime now = LocalDateTime.now();
        switch (state) {
            case ALL:
                bookings = bookingRepository.getAllByBookerIdOrderByStartDesc(userId);;
                break;
            case PAST:
                bookings = bookingRepository.getByBookerIdStatePast(userId, now);
                break;
            case WAITING:
                bookings = bookingRepository.getByBookerIdAndStatus(userId, BookingStatus.WAITING);
                break;
            case CURRENT:
                bookings = bookingRepository.getByBookerIdStateCurrent(userId, now);
                break;
            case REJECTED:
                bookings = bookingRepository.getByBookerIdAndStatus(userId, BookingStatus.REJECTED);
                break;
            case FUTURE:
                bookings = bookingRepository.getFuture(userId, now);
                break;
        }
        if (bookings.isEmpty()) {
            throw new NotFoundException(String.format("User with id %d has no bookings booked", userId));
        }
        List<Item> items = itemRepository.findAll();
        Map<Long, Item> itemMap = items.stream()
                .collect(Collectors.toMap(Item::getId, item -> item));

        return bookings.stream().map(book -> bookingMapper.toOutputDto(book, itemMap.get(book.getItem().getId()), user)).toList();
    }

    @Override
    public List<BookingOutputDto> findAllByOwner(Long userId, State state) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(String.format("User with id %d is not found", userId)));
        List<Booking> bookings = List.of();
        LocalDateTime now = LocalDateTime.now();
        switch (state) {
            case ALL:
                bookings = bookingRepository.getOwnerAll(userId);
                break;
            case FUTURE:
                bookings = bookingRepository.getOwnerFuture(userId, now);
                break;
            case CURRENT:
                bookings = bookingRepository.getOwnerCurrent(userId, now);
                break;
            case WAITING:
                bookings = bookingRepository.getAllByItemOwnerIdAndStatus(userId, BookingStatus.WAITING);
                break;
            case PAST:
                bookings = bookingRepository.getOwnerPast(userId, now);
                break;
            case REJECTED:
                bookings = bookingRepository.getAllByItemOwnerIdAndStatus(userId, BookingStatus.REJECTED);
                break;
        }
        List<Item> items = itemRepository.findAll();
        Map<Long, Item> itemMap = items.stream()
                .collect(Collectors.toMap(Item::getId, item -> item));

        return bookings.stream().map(book -> bookingMapper.toOutputDto(book, itemMap.get(book.getItem().getId()), user)).toList();
    }
}