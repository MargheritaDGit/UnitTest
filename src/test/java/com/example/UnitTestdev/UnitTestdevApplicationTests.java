package com.example.UnitTestdev;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UnitTestdevApplicationTests {

    @Autowired
    UserRepository userRepository;
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void testCreateUser() throws Exception {
        Users users = new Users(1L, "Alberto", "Rossi", "ciao@gmail.com"); //definisco nuovo utente
        mockMvc.perform(post("/api/create") //richiamo mock per formare una chiamata di rete con chiamate del controller
                        .contentType(MediaType.APPLICATION_JSON) //spiego qual è contenuto che deve inserire
                        .content(objectMapper.writeValueAsString(users))) //deve scrivere USER come fosse una stringa, lo converso con object mapper
                .andExpect(status().isOk()) //sto dicendo che se è cosi è ok
                .andExpect(jsonPath("$.id").exists()) //prendo solo id dello user e verifico se esiste, devo dare valori di controllo
                .andExpect(jsonPath("$.name").value("Alberto"))
                .andExpect(jsonPath("$.surname").value("Rossi"))
                .andExpect(jsonPath("$.mail").value("ciao@gmail.com"));
    }

    @Test
    public void testGetUser() throws Exception {
        Users users = new Users(1L, "Alberto", "Rossi", "ciao@gmail.com");
        userRepository.save(users);
        mockMvc.perform(get("/api/get/{id}", users.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(users.getId()))
                .andExpect(jsonPath("$.name").value("Alberto"))
                .andExpect(jsonPath("$.surname").value("Rossi"))
                .andExpect(jsonPath("$.mail").value("ciao@gmail.com"));
    }

    @Test
    public void testPutUser() throws Exception {
        Users users = new Users(1L, "Alberto", "Rossi", "ciao@gmail.com");
        userRepository.save(users);
        Users updateUsers = new Users(1L, "Marco", "Bianchi", "hello@gmail.com");
        mockMvc.perform(put("/api/put/{id}", users.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateUsers)))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.id").value(users.getId()))
                        .andExpect(jsonPath("$.name").value("Alberto"))
                        .andExpect(jsonPath("$.surname").value("Rossi"))
                        .andExpect(jsonPath("$.mail").value("ciao@gmail.com"));
    }

    @Test
    public void deleteUserById() throws Exception{
        Users users = new Users (1L, "Alberto", "Rossi", "ciao@gmail.com");
       userRepository.save(users);
       mockMvc.perform(delete("/api/delete/{id}",users.getId()))
               .andExpect(status().isOk());
    }
}




