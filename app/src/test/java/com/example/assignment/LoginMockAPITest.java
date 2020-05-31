package com.example.assignment;

import com.example.assignment.models.LoginRequest;
import com.example.assignment.models.LoginResponse;
import com.example.assignment.network.RetrofitServices;

import org.mockito.Mock;

public class LoginMockAPITest {

    @Mock
    RetrofitServices retrofitServices;

    void testSuccessResponse()
    {
        // arrange
        LoginRequest loginRequest = new LoginRequest("super_admin","12345678");
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setStatusCode(200);
        loginResponse.setToken("VwvYXBpXC9");


    }

}
