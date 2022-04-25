package com.epam.data.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.epam.data.dao.UserDaoTest;
import com.epam.domain.controllers.Operations;
import com.epam.utils.TestUtils;

@ExtendWith(MockitoExtension.class)
public class OperationsTest {
	static Connection connection;
	static Logger logger = Logger.getLogger(UserDaoTest.class);
	Operations operations = Operations.getInstance(connection);
    HttpServletRequest request = mock(HttpServletRequest.class);       
    HttpServletResponse response = mock(HttpServletResponse.class);  
    HttpSession session = mock(HttpSession.class);
	RequestDispatcher dispatcher = mock(RequestDispatcher.class);
    
    @BeforeAll
	public static void setup() {
		BasicConfigurator.configure();
		connection = TestUtils.getTestConnection();
	}
	
	@Test
	public void shouldSendRedirect() throws ServletException, IOException {
		when(request.getSession()).thenReturn(session);
		when(session.getAttribute("user_id")).thenReturn(1);
		operations.get(request, response, "en", false);
		verify(response, atLeastOnce()).sendRedirect(anyString());
	}
	
	@Test
	public void shouldDoGetAdmin() throws ServletException, IOException {
		when(request.getSession()).thenReturn(session);
		when(session.getAttribute("user_id")).thenReturn(1);
		when(request.getRequestDispatcher(anyString())).thenReturn(dispatcher);
		operations.get(request, response, "en", true);
		verify(request, atLeastOnce()).setAttribute(anyString(), any());
	}
	
	@Test
	public void shouldDoGetUser() throws ServletException, IOException {
		when(request.getSession()).thenReturn(session);
		when(session.getAttribute("user_id")).thenReturn(3);
		when(request.getRequestDispatcher(anyString())).thenReturn(dispatcher);
		when(request.getParameter(anyString())).thenReturn("1");
		operations.get(request, response, "en", true);
		verify(request, atLeastOnce()).setAttribute(anyString(), any());
		verify(request, atLeastOnce()).getParameter(anyString());
	}
	
	@Test
	public void shouldOrderOperationsDateDescending() throws ServletException, IOException {
		when(request.getSession()).thenReturn(session);
		when(session.getAttribute("user_id")).thenReturn(3);
		when(request.getRequestDispatcher(anyString())).thenReturn(dispatcher);
		when(request.getParameter("orderBy")).thenReturn("date");
		when(request.getParameter("ascending")).thenReturn("false");
		operations.get(request, response, "en", true);
		verify(request, atLeastOnce()).setAttribute(eq("operations"), any());
	}
	@Test
	public void shouldOrderOperationsDateAscending() throws ServletException, IOException {
		when(request.getSession()).thenReturn(session);
		when(session.getAttribute("user_id")).thenReturn(3);
		when(request.getRequestDispatcher(anyString())).thenReturn(dispatcher);
		when(request.getParameter("orderBy")).thenReturn("date");
		when(request.getParameter("ascending")).thenReturn("true");
		operations.get(request, response, "en", true);
		verify(request, atLeastOnce()).setAttribute(eq("operations"), any());
	}
	
	@Test
	public void shouldOrderOperationsPaymentStatusAscending() throws ServletException, IOException {
		when(request.getSession()).thenReturn(session);
		when(session.getAttribute("user_id")).thenReturn(3);
		when(request.getRequestDispatcher(anyString())).thenReturn(dispatcher);
		when(request.getParameter("orderBy")).thenReturn("paymentStatus");
		when(request.getParameter("ascending")).thenReturn("true");
		operations.get(request, response, "en", true);
		verify(request, atLeastOnce()).setAttribute(eq("operations"), any());
	}
	
	@Test
	public void shouldOrderOperationsPaymentStatusDescending() throws ServletException, IOException {
		when(request.getSession()).thenReturn(session);
		when(session.getAttribute("user_id")).thenReturn(3);
		when(request.getRequestDispatcher(anyString())).thenReturn(dispatcher);
		when(request.getParameter("orderBy")).thenReturn("paymentStatus");
		when(request.getParameter("ascending")).thenReturn("false");
		operations.get(request, response, "en", true);
		verify(request, atLeastOnce()).setAttribute(eq("operations"), any());
	}
	
	
}
