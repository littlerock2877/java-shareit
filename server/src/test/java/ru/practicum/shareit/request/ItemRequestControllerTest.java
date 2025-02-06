package ru.practicum.shareit.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.service.ItemRequestService;

import java.time.LocalDateTime;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ItemRequestController.class)
@AutoConfigureMockMvc
public class ItemRequestControllerTest {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ItemRequestService itemRequestService;

    @SneakyThrows
    @Test
    void getById() {
        ItemRequestDto itemRequestDtoLong = new ItemRequestDto(1L, "description", LocalDateTime.now(), List.of());
        Mockito.when(itemRequestService.getItemRequestById(Mockito.anyLong(), Mockito.anyLong())).thenReturn(itemRequestDtoLong);

        String result = mockMvc.perform(get("/requests/{requestId}", 1L)
                        .header("X-Sharer-User-Id", 1))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertEquals(objectMapper.writeValueAsString(itemRequestDtoLong), result);
    }

    @SneakyThrows
    @Test
    void getAll() {
        ItemRequestDto itemRequestDto = new ItemRequestDto(1L, "description", LocalDateTime.now(), List.of());
        Mockito.when(itemRequestService.getAllItemRequestsForUser(Mockito.anyLong())).thenReturn(List.of(itemRequestDto));

        String result = mockMvc.perform(get("/requests/all")
                        .header("X-Sharer-User-Id", 1L))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertEquals(objectMapper.writeValueAsString(List.of(itemRequestDto)), result);
    }

    @SneakyThrows
    @Test
    void getAllByRequester() {
        ItemRequestDto itemRequestDto = new ItemRequestDto(1L, "description", LocalDateTime.now(), List.of());
        Mockito.when(itemRequestService.getAllItemRequests(Mockito.anyLong())).thenReturn(List.of(itemRequestDto));

        String result = mockMvc.perform(get("/requests")
                        .header("X-Sharer-User-Id", 1L))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertEquals(objectMapper.writeValueAsString(List.of(itemRequestDto)), result);
    }
}
