import React, {useState, useEffect} from 'react';
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
	const [shoppingList, setShoppingList] = useState([]);

	const [menu, setMenu] = useState([]);

	//For loading all recipes
	useEffect(() => {
		WeeklyDropdown();
		facade
			.fetchGetData('all')
			.then(res => {
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

	//For shopping list
	useEffect(() => {
		if (menu.length != 0) {
			let newShoppingList = [];
			{
				menu.map(element => {
					element.recipe.ingredients.map(element => {
						newShoppingList.push(element.description);
					});
				});
			}
			setShoppingList(shoppingList => [...shoppingList, ...newShoppingList]);
		}
	}, [menu]);

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
						menu={menu}
						setMenu={setMenu}
						shoppingList={shoppingList}
					/>
					{menu.length != 0 && <h4>Your shopping list</h4>}
					<ul>
						{shoppingList.map(element => {
							return <li>{element}</li>;
						})}
					</ul>
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
			weekDay: document.getElementById('weeklyDropDown').value
		};
		setMenu(menu => [...menu, dayPlan]);
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

function MenuPlanner({menu}) {
	if (!menu || menu.length == 0) {
		return (
			<p className="italicCustomStyle">
				(Add recipes to your menu by accessing the recipes)
			</p>
		);
	} else {
		return (
			<div>
				<table className="table table-sm">
					<thead>
						<tr>
							<th>Weekday</th>
							<th>Name</th>
						</tr>
					</thead>
					<tbody>
						<GenerateTableRows menu={menu} />
					</tbody>
				</table>
			</div>
		);
	}
}

function GenerateTableRows({menu}) {
	return (
		<>
			{menu.map((element, index) => {
				return (
					<tr>
						<td key={index + element.weekDay}>{element.weekDay}</td>
						<td key={index + element.recipe.name}>{element.recipe.name}</td>
					</tr>
				);
			})}
		</>
	);
}

export default Welcome;
