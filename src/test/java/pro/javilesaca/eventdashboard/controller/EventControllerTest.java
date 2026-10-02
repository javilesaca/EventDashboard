package pro.javilesaca.eventdashboard.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import pro.javilesaca.eventdashboard.model.Event;
import pro.javilesaca.eventdashboard.service.EventNotFoundException;
import pro.javilesaca.eventdashboard.service.EventService;
import pro.javilesaca.eventdashboard.service.EventStreamService;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EventController.class)
class EventControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private EventService service;

    @MockitoBean
    private EventStreamService streamService;

    @Test
    void postValidEventReturns201WithLocation() throws Exception {
        Event saved = new Event("INFO", "arranque", "system", LocalDateTime.now());
        saved.setId("abc123");
        when(service.saveEvent(any())).thenReturn(saved);

        mockMvc.perform(post("/api/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"type":"INFO","message":"arranque","source":"system"}"""))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", org.hamcrest.Matchers.endsWith("/api/events/abc123")))
                .andExpect(jsonPath("$.id").value("abc123"));
    }

    @Test
    void postInvalidEventReturns400WithFieldErrors() throws Exception {
        mockMvc.perform(post("/api/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"type":"","message":"x"}"""))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors.type").exists())
                .andExpect(jsonPath("$.errors.source").exists());
    }

    @Test
    void getMissingEventReturns404() throws Exception {
        when(service.getById("nope")).thenThrow(new EventNotFoundException("nope"));

        mockMvc.perform(get("/api/events/nope"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Evento no encontrado: nope"));
    }

    @Test
    void listEventsAppliesFilters() throws Exception {
        Event event = new Event("ERROR", "fallo", "backend", LocalDateTime.now());
        when(service.search(any(), any(), any(), any(), any()))
                .thenReturn(new PageImpl<>(List.of(event), PageRequest.of(0, 20), 1));

        mockMvc.perform(get("/api/events").param("type", "ERROR"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].type").value("ERROR"))
                .andExpect(jsonPath("$.totalElements").value(1));
    }
}
