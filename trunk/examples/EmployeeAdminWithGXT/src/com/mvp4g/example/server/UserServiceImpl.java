package com.mvp4g.example.server;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpSession;

import com.extjs.gxt.ui.client.data.BasePagingLoadResult;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
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

	public PagingLoadResult<UserBean> getUsers( PagingLoadConfig config ) {
		HttpSession session = getThreadLocalRequest().getSession();
		List<UserBean> users = (List)session.getAttribute( "users" );
		if ( users == null ) {
			users = loadUsers();
			session.setAttribute( "users", users );
		}

		ArrayList<UserBean> sublist = new ArrayList<UserBean>();
		int start = config.getOffset();
		int limit = users.size();
		if ( config.getLimit() > 0 ) {
			limit = Math.min( start + config.getLimit(), limit );
		}
		for ( int i = config.getOffset(); i < limit; i++ ) {
			sublist.add( users.get( i ) );
		}
		return new BasePagingLoadResult<UserBean>( sublist, config.getOffset(), users.size() );

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

	private List<UserBean> loadUsers() {
		List<UserBean> users = new ArrayList<UserBean>();

		UserBean user = null;
		String firstName = null;
		String lastName = null;
		String username = null;
		List<String> roles = null;

		Random random = new Random();

		for ( int k = 0; k < 10; k++ ) {
			for ( int i = 0; i < NB_USERS; i++ ) {
				user = new UserBean();

				firstName = FIRST_NAMES[i % FIRST_NAMES.length] + k;
				user.setFirstName( firstName );

				lastName = LAST_NAMES[i % LAST_NAMES.length] + k;
				user.setLastName( lastName );

				username = ( firstName.substring( 0, 1 ) + lastName ).toLowerCase() + k;
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
		}

		return users;
	}

}
