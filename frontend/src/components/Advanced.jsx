import React, {useState} from 'react';
import facade from '../apiFacade';

const Advanced = ({permission}) => {
	if (!permission) {
		return (
			<>
				<p>You are not logged in</p>
			</>
		);
	} else {
		return (
			<>
				<AdvancedFunction />
			</>
		);
	}
};

const AdvancedFunction = () => {
	return <p>Not implemented</p>;
};

export default Advanced;
