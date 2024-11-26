package Menu.MenuBackend.presentationlayer;

import Menu.MenuBackend.servicelayer.dto.MenuDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static Menu.MenuBackend.config.FirebaseAuthMockConfig.VALID_TOKEN;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


class MenuRestControllerIntegrationTest extends BasicIntegrationTest {

    @Test
    @DisplayName("Should fetch valid menu for given period")
    void testFetchingMenuForGivenPeriod() throws Exception {

        String startDate = LocalDate.of(2024,11,10).format(DateTimeFormatter.ISO_DATE);
        String endDate = LocalDate.of(2024,11,15).format(DateTimeFormatter.ISO_DATE);
        mockMvc.perform(get("/api/getMenuForPeriod")
                        .header("Authorization", BEARER_PREFIX + VALID_TOKEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("startDate", startDate)
                        .param("endDate", endDate))
                .andExpect(status().isOk())
                .andDo(result -> {
                    List<MenuDTO> menu = objectMapper.readValue(result.getResponse().getContentAsString(), ArrayList.class);
                    Assertions.assertTrue(menu.isEmpty());
                });
    }


}