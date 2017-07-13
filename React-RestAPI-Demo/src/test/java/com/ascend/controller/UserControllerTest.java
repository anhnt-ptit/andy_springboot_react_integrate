package com.ascend.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.ascend.domain.User;
import com.ascend.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Tests for UserController RESTful endpoints.
 * 
 * @author anh.ngo
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class UserControllerTest {

	private MockMvc mockMvc;
	
    @Mock
    private UserService userService;
    
    @InjectMocks
    private UserController userController;    

    @Before
    public void setup() {
    	MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(userController)
                .build();
    }
    
    @Test
    public void test_get_all_success() throws Exception {
        List<User> Users = Arrays.asList(
        		new User(1, "Andy", 33, "Ha Noi"),
        		new User(2, "Richar", 25, "Ha Noi"));
        
        when(userService.list()).thenReturn(Users);
        
        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].userId", is(1)))
                .andExpect(jsonPath("$[0].name", is("Andy")))
                .andExpect(jsonPath("$[1].userId", is(2)))
                .andExpect(jsonPath("$[1].name", is("Richar")));
        
        verify(userService, times(1)).list();
        verifyNoMoreInteractions(userService);
    }
    
    @Test
    public void test_get_by_id_success() throws Exception {
    	User User = new User(1, "Andy", 33, "Ha Noi");
        when(userService.findById(1)).thenReturn(User);
        
        mockMvc.perform(get("/users/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.userId", is(1)))
                .andExpect(jsonPath("$.name", is("Andy")));
        
        verify(userService, times(1)).findById(1);
        verifyNoMoreInteractions(userService);
    }
    
    @Test
    public void test_get_by_id_fail_404_not_found() throws Exception {
    	
        when(userService.findById(1)).thenReturn(null);
        
        mockMvc.perform(get("/users/{id}", 1))
                .andExpect(status().isNotFound());
        
        verify(userService, times(1)).findById(1);
        verifyNoMoreInteractions(userService);
        
    }
    
    @Test
    public void test_create_success() throws Exception {
    	User User = new User(1, "Andy", 33, "Ha Noi");
        when(userService.saveUser(User)).thenReturn(Boolean.TRUE);
        
        mockMvc.perform(post("/users")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(asJsonString(User)))
            		.andExpect(status().isCreated());
        
        verify(userService, times(1)).saveUser(User);
        verifyNoMoreInteractions(userService);
    }
    
    @Test
    public void test_update_success() throws Exception {
    	User User = new User(1, "Andy", 33, "Ha Noi");
        when(userService.updateUser(User)).thenReturn(Boolean.TRUE);
        
        mockMvc.perform(
                put("/users/{id}", User.getUserId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(asJsonString(User)))
                	.andExpect(status().isOk());
        
        verify(userService, times(1)).updateUser(User);
        verifyNoMoreInteractions(userService);
    }
    
    @Test
    public void test_delete_success() throws Exception {
    	User User = new User(1, "Andy", 33, "Ha Noi");
        when(userService.deleteById(User.getUserId())).thenReturn(Boolean.TRUE);
        
        mockMvc.perform(
                delete("/users/{id}", User.getUserId()))
                .andExpect(status().isNoContent());
        
        verify(userService, times(1)).deleteById(User.getUserId());
        verifyNoMoreInteractions(userService);
    }    
    
    public static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }    
}
