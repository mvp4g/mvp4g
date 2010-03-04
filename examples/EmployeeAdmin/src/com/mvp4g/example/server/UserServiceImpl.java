package com.mvp4g.example.server;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.mvp4g.example.client.Constants;
import com.mvp4g.example.client.UserService;
import com.mvp4g.example.client.bean.UserBean;

public class UserServiceImpl extends RemoteServiceServlet implements UserService, Constants {

	/**
	 * 
	 */
	private static final long serialVersionUID = 263606265567100312L;

	static private final String[] FIRST_NAMES = { "Karim", "Cristiano", "Iker", "Sergio" };
	static private final String[] LAST_NAMES = { "Benzema", "Ronaldo", "Casillas", "Ramos" };

	static private int NB_USERS = 4;

	public List<UserBean> getUsers() {
		List<UserBean> users = new ArrayList<UserBean>();
		UserBean user = null;
		String firstName = null;
		String lastName = null;
		String username = null;
		List<String> roles = null;

		Random random = new Random();

		for ( int i = 0; i < NB_USERS; i++ ) {
			user = new UserBean();

			firstName = FIRST_NAMES[i % FIRST_NAMES.length];
			user.setFirstName( firstName );

			lastName = LAST_NAMES[i % LAST_NAMES.length];
			user.setLastName( lastName );

			username = ( firstName.substring( 0, 1 ) + lastName ).toLowerCase();
			user.setUsername( username );

			user.setEmail( username + "@real.com" );

			user.setDepartment( DEPARTMENTS[i % DEPARTMENTS.length] );

			user.setPassword( "1234" );

			roles = new ArrayList<String>();
			int nbRoles = random.nextInt( ROLES.length );
			String role = null;
			for ( int j = 0; j < nbRoles; j++ ) {
				role = ROLES[random.nextInt( ROLES.length )];
				if ( !roles.contains( role ) ) {
					roles.add( role );
				}
			}
			user.setRoles( roles );

			users.add( user );
		}

		return users;
	}

	public void deleteUser( UserBean user ) {
		// deletion always succeeds		
	}

	public void createUser( UserBean user ) {
		// creation always succeeds		
	}

	public void updateUser( UserBean user ) {
		// update always succeeds		
	}

}
