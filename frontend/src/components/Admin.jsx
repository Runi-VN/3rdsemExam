import React, {useState} from 'react';
import facade from '../apiFacade.jsx';

const AdminPanel = ({loggedIn, roles}) => {
	if (!loggedIn || !roles.includes('admin')) {
		return (
			<>
				<p>You are not logged in as admin</p>
			</>
		);
	} else {
		return <p>Logged in as admin. Not implemented.</p>;
	}
};

export default AdminPanel;
