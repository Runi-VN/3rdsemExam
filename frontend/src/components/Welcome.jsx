import React, {useState, useEffect} from 'react';
import {Link} from 'react-router-dom';
import facade from '../apiFacade';
import 'bootstrap/dist/css/bootstrap.min.css';
import '../Welcome.css';

const Welcome = () => {
	const [recipes, setRecipes] = useState();
	const [searchRecipe, setSearchRecipe] = useState();
	const [recipeDetails, setRecipeDetails] = useState();
	const [errorMessage, setErrorMessage] = useState();
	const [showMenu, setShowMenu] = useState(false);
	console.log(showMenu);
	console.log(searchRecipe);
	console.log(recipeDetails);
	//For loading all recipes
	useEffect(() => {
		facade
			.fetchGetData('all')
			.then(res => {
				console.log(res);
				setRecipes(res);
				setErrorMessage('');
			})
			.catch(err => {
				if (err.status) {
					err.fullError.then(e => {
						setErrorMessage(e.message);
						console.log(e.code, e.message);
					});
				} else {
					console.log('Network error');
				}
			});
	}, []);
	//For loading specific recipes
	useEffect(() => {
		if (searchRecipe) {
			facade
				.fetchGetData('specific/' + searchRecipe)
				.then(res => {
					console.log(res);
					setRecipeDetails(res);
					setErrorMessage('');
				})
				.catch(err => {
					if (err.status) {
						err.fullError.then(e => {
							setErrorMessage(e.message);
							console.log(e.code, e.message);
						});
					} else {
						console.log('Network error');
					}
				});
		}
	}, [searchRecipe]);

	return (
		<div>
			<AllRecipes recipes={recipes} setRecipeDetailed={setSearchRecipe} />
			<h4>Click a recipe for further details</h4>
			<DisplayRecipeDetails recipe={recipeDetails} />
			<MenuPlanner showMenu={showMenu} setShowMenu={setShowMenu} />
			{/* <SearchRecipe
				recipes={recipes}
				setRecipes={setRecipes}
				setErrorMessage={setErrorMessage}
			/> */}
		</div>
	);
};

function DisplayRecipeDetails({recipe}) {
	if (recipe)
		return (
			recipe && (
				<div className="border border-secondary customBorder">
					<h5>{recipe.name}</h5>
					<p className="recipeDescription">{recipe.description}</p>
					<p className="recipePrep">{recipe.prep_time}</p>
					<h6>Ingredients</h6>
					<ul>
						{recipe.ingredients.map((element, index) => (
							<li key={index}>{element.description}</li>
						))}
					</ul>
					<h6>Step-by-step</h6>
					<ol className="recipeSteps">
						{recipe.preparation_steps.map((element, index) => (
							<li key={index}>{element}</li>
						))}
					</ol>
				</div>
			)
		);
	return null;
}

function AllRecipes({recipes, setRecipeDetailed}) {
	const handleClick = name => {
		setRecipeDetailed(name);
	};
	if (recipes)
		return (
			<div>
				<h4>All recipes</h4>
				<ul>
					{recipes.map((element, index) => (
						<li key={index}>
							<a href="#" onClick={evt => handleClick(evt.target.innerHTML)}>
								{element}
							</a>
						</li>
					))}
				</ul>
			</div>
		);
	else return null;
}

// function MemberTable({members}) {
// 	console.log('members: ', members);
// 	const tableItems = members.map((member, index) => (
// 		<Row
// 			key={member.id}
// 			fName={member.fName}
// 			id={member.id}
// 			hobbylist={member.hobbylist}
// 			lName={member.lName}
// 			mail={member.mail}
// 			residence={member.residence}
// 			telephone={member.telephone}
// 		/>
// 	));
// 	return (
// 		<table className="table">
// 			<thead>
// 				<tr>
// 					<th>First Name</th>
// 					<th>Hobbies</th>
// 					<th>ID</th>
// 					<th>Last Name</th>
// 					<th>Email</th>
// 					<th>Address</th>
// 					<th>City</th>
// 					<th>Phone</th>
// 				</tr>
// 			</thead>
// 			<tbody>{tableItems}</tbody>
// 		</table>
// 	);
// }

// function Row(props) {
// 	console.log(props.hobbylist);
// 	return (
// 		<tr>
// 			<td>{props.fName}</td>
// 			<td>
// 				{props.hobbylist.map((element, index) => {
// 					return <li key={index}>{element.hobbyName}</li>;
// 				})}
// 			</td>
// 			<td>{props.id}</td>
// 			<td>{props.lName}</td>
// 			<td>{props.mail}</td>
// 			<td>{props.residence.road}</td>
// 			<td>{props.residence.town}</td>
// 			<td>{props.telephone}</td>
// 		</tr>
// 	);
// }

function MenuPlanner({showMenu, setShowMenu}) {
	if (!showMenu) {
		return (
			<button type="button" onClick={() => setShowMenu(!showMenu)}>
				Plan a menu
			</button>
		);
	} else {
		return <p>test</p>;
	}
}

export default Welcome;
