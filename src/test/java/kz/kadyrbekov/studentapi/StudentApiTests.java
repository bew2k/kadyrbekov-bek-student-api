package kz.kadyrbekov.studentapi;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class StudentApiTests {

    @Autowired
    private MockMvc mvc;

    private static final String BEK =
            "{\"name\":\"Bek\",\"surname\":\"Kadyrbekov\",\"email\":\"bek@example.com\",\"studentGroup\":\"IT2-2306\"}";

    @Test
    void infoShowsStudentName() throws Exception {
        mvc.perform(get("/api/info"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.author").value("Bek Kadyrbekov"));
    }

    @Test
    void createReadUpdateDelete() throws Exception {
        String response = mvc.perform(post("/api/students")
                        .contentType(MediaType.APPLICATION_JSON).content(BEK))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.surname").value("Kadyrbekov"))
                .andReturn().getResponse().getContentAsString();

        long id = Long.parseLong(response.replaceAll(".*\"id\":(\\d+).*", "$1"));

        mvc.perform(get("/api/students/" + id)).andExpect(status().isOk());
        mvc.perform(get("/api/students")).andExpect(status().isOk());

        mvc.perform(put("/api/students/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(BEK.replace("IT2-2306", "IT2-2307")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.studentGroup").value("IT2-2307"));

        mvc.perform(delete("/api/students/" + id)).andExpect(status().isNoContent());
        mvc.perform(get("/api/students/" + id)).andExpect(status().isNotFound());
    }

    @Test
    void nonexistentStudentReturns404() throws Exception {
        mvc.perform(get("/api/students/999999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404));
    }
}
