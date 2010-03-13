package com.mvp4g.example.server;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class NonRpcServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3451823764199326003L;

	private int number = 0;

	@Override
	public void doPost( HttpServletRequest request, HttpServletResponse response ) {
		try {
			response.getOutputStream().print( "Pong " + number );
			number++;
		} catch ( IOException e ) {
			e.printStackTrace();
		}
	}

}
