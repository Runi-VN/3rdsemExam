import React, {useState, useEffect} from 'react';
import {Link} from 'react-router-dom';
import facade from '../apiFacade';
import 'bootstrap/dist/css/bootstrap.min.css';
import Collapse from 'react-bootstrap/Collapse';
import Button from 'react-bootstrap/Button';
import '../Welcome.css';

const Welcome = () => {
	const [recipes, setRecipes] = useState();
	const [searchRecipe, setSearchRecipe] = useState();
	const [recipeDetails, setRecipeDetails] = useState();
	const [errorMessage, setErrorMessage] = useState();
	const [showMenu, setShowMenu] = useState(false);
	const [menu, setMenu] = useState([
		{
			weekDay: 'THU',
			recipe: {
				description:
					'Chicken wrapped in greaseproof paper to trap all the wonderful juices - keeps beautifully moist! ',
				id: 0,
				ingredients: [
					{
						description: '1 whole (1.8kg) chicken',
						id: 0
					},
					{
						description: 'salt and freshly ground black pepper',
						id: 0
					},
					{
						description: '1 large lemon, sliced',
						id: 0
					},
					{
						description: '6 cloves garlic, sliced',
						id: 0
					},
					{
						description: '6 sprigs fresh thyme',
						id: 0
					}
				],
				name: 'Moist garlic roasted chicken ',
				prep_time: 'Prep: 10 min ',
				preparation_steps: [
					'Preheat oven to 160 degrees C / gas mark 3.',
					'Place a large sheet of greaseproof or baking parchment into the middle of a roasting tray. It should be large enough to wrap around the chicken plus plenty of extra for folding over.',
					'Season the chicken with salt and pepper, stuff with half of the lemon slices and place breast side up in the middle of the paper. Sprinkle garlic slices and thyme sprigs evenly over the chicken. Lay the remaining lemon slices over the breast. Fold the paper over the chicken forming a loose parcel.',
					'Bake in the preheated oven until the chicken has cooked, about 1 1/2 to 2 hours or until the juices are no longer pink.',
					'Perfect Prawn Pitta Wrap ',
					'hese perfect prawn pittas are delicious with the perfect tangy sauce (very similar to marie rose), watercress and avocado! I love prawn sandwiches but this is a great twist on the ordinary sandwich. ',
					'Ingredients ',
					'Serves: 1 ',
					'1 pitta bread',
					'2 tablespoons mayonnaise',
					'1 teaspoon ketchup',
					'drop of horseradish sauce',
					'sprinkle of cayenne pepper',
					'splash of lemon juice',
					'1 small avacado',
					'60g (2 oz) cooked, peeled prawns',
					'small handful of watercress',
					'Place pitta bread in toaster and leave until it puffs out. Cut off end of pitta bread and stick a knife inside to open up the pocket.',
					'In a bowl place the mayonnaise, ketchup, horseradish, cayenne pepper and a splash of lemon juice. Combine.',
					'Chop up avacado and add to sauce along with prawns.',
					'Rip up some watercress and add to the bowl.',
					'Combine and spoon the mixture into the the pitta bread!'
				]
			}
		}
	]);
	console.log(showMenu);
	console.log(searchRecipe);
	console.log(recipeDetails);
	//For loading all recipes
	useEffect(() => {
		WeeklyDropdown();
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
			<h4 className="errorMsg">{errorMessage}</h4>
			<div className="row">
				<div className="col-sm-6">
					<AllRecipes recipes={recipes} setRecipeDetailed={setSearchRecipe} />
					<h4>Click a recipe for further details</h4>
					<DisplayRecipeDetails
						recipe={recipeDetails}
						menu={menu}
						setMenu={setMenu}
					/>
				</div>
				<div className="col-sm-6">
					<h4>Your menu plan</h4>
					<MenuPlanner
						showMenu={showMenu}
						setShowMenu={setShowMenu}
						menu={menu}
						setMenu={setMenu}
					/>
				</div>
			</div>
		</div>
	);
};

function DisplayRecipeDetails({recipe, menu, setMenu}) {
	const [open, setOpen] = useState(false); //controlling colapse

	function handleClick() {
		let dayPlan = {
			recipe,
			// recipe: {
			// 	name: recipe.name,
			// 	description: recipe.description,
			// 	prep_time: recipe.prep_time,
			// 	preparation_steps
			// },
			weekDay: document.getElementById('weeklyDropDown').value
		};
		setMenu(menu => [...menu, dayPlan]);
		console.log('menu: ', menu);
	}

	if (recipe)
		return (
			recipe && (
				<div className="border border-secondary customBorder">
					<h5>{recipe.name}</h5>
					<p className="recipeDescription">{recipe.description}</p>
					<p className="recipePrep">{recipe.prep_time}</p>
					<Button
						className="btn btn-info"
						onClick={() => setOpen(!open)}
						aria-controls="example-collapse-text"
						aria-expanded={open}>
						Expand Details
					</Button>
					<Collapse in={open}>
						<div id="example-collapse-text">
							<h6>Ingredients</h6>
							<ul>
								{recipe.ingredients.map((element, index) => (
									<li key={index}>{element.description}</li>
								))}
							</ul>
							<h6>Step-by-step</h6>
							<ol className="italicCustomStyle">
								{recipe.preparation_steps.map((element, index) => (
									<li key={index}>{element}</li>
								))}
							</ol>
						</div>
					</Collapse>
					<br />
					<br />
					<>
						<WeeklyDropdown />
						<button
							className="btn btn-primary weeklyBtn"
							type="button"
							onClick={evt => handleClick()}>
							Add to weekly plan
						</button>
					</>
				</div>
			)
		);
	return null;
}

function WeeklyDropdown() {
	return (
		<div className="weeklyDropdwn">
			<select id="weeklyDropDown">
				<option value="MON">Monday</option>
				<option value="TUE">Tuesday</option>
				<option value="WED">Wednesday</option>
				<option value="THU">Thursday</option>
				<option value="FRI">Friday</option>
				<option value="SAT">Saturday</option>
				<option value="SUN">Sunday</option>
			</select>
		</div>
	);
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
	return null;
}

function MenuPlanner({menu, setMenu}) {
	console.log('menu in planner: ', menu);
	if (menu.length != 0) {
		console.log('menu0Rec', menu[0].weekDay);
	}

	if (!menu) {
		//TODO ADD: || menu.length == 0
		return (
			<p className="italicCustomStyle">
				(Add recipes to your menu by accessing the recipes)
			</p>
		);
	} else {
		return (
			<table className="table table-sm">
				<thead>
					<tr>
						<th>Data</th>
						<th>Monday</th>
						<th>Tuesday</th>
						<th>Wednesday</th>
						<th>Thursday</th>
						<th>Friday</th>
						<th>Saturday</th>
						<th>Sunday</th>
					</tr>
				</thead>
				<tbody>
					<GenerateRows menu={menu} />
				</tbody>
			</table>
		);
	}
}

function GenerateRows(menu) {
	//bad performance

	return null;
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

export default Welcome;
