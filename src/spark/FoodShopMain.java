package spark;

import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.post;
import static spark.Spark.staticFiles;

import java.io.File;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import beans.Administrator;
import beans.Customer;
import beans.Deliverer;
import beans.Manager;
import beans.Restaurant;
import beans.User;
import controllers.*;
import enumerations.UserRoles;
import repositories.*;
import services.*;

public class FoodShopMain {

	private static Gson g = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
	private static UserController userController = new UserController();
	private static CustomerController customerController = new CustomerController(new CustomerService(new CustomerRepository()));
	private static DelivererController delivererController = new DelivererController(new DelivererService(new DelivererRepository()));
	private static ManagerController managerController = new ManagerController(new ManagerService(new ManagerRepository()));
	private static AdministratorController administratorController = new AdministratorController(new AdministratorService(new AdministratorRepository()));
	private static RestaurantController restaurantContoller = new RestaurantController(new RestaurantService(new RestaurantRepository()));
	
	public static void main(String[] args) throws Exception {
		port(8080);
		
		staticFiles.externalLocation(new File("./static").getCanonicalPath());
		
		get("/", (req, res) -> {
			return "SUCCESS";
		});
		
		post("/login", (req, res) -> {
			res.type("application/json");
			User user = g.fromJson(req.body(), User.class);
			User loggedUser = userController.userExists(user.getUsername(), user.getPassword());
			if (loggedUser == null) {
				return "ERROR";
			}
			Session session = req.session();
			if (loggedUser.getUserRole() == UserRoles.CUSTOMER) {
				Customer customer = customerController.read(loggedUser.getUsername());
				session.attribute("user", customer);
				return "SUCCESS/customer";
			} else if (loggedUser.getUserRole() == UserRoles.DELIVERER) {
				Deliverer deliverer = delivererController.read(loggedUser.getUsername());
				session.attribute("user", deliverer);
				return "SUCCESS/deliverer";
			} else if (loggedUser.getUserRole() == UserRoles.MANAGER) {
				Manager manager = managerController.read(loggedUser.getUsername());
				session.attribute("user", manager);
				return "SUCCESS/manager";
			} else {
				Administrator administrator = administratorController.read(loggedUser.getUsername());
				session.attribute("user", administrator);
				return "SUCCESS/administrator";
			}
		});
		
		post("/registration", (req, res) -> {
			res.type("application/json");
			User user = g.fromJson(req.body(), User.class);
			User registratedUser = userController.registerUser(user.getName(), user.getLastName(), user.getBirthDate(), user.getSex(), user.getUsername(), user.getPassword());
			if (registratedUser == null) {
				return "ERROR";
			}
			return "SUCCESS";
		});
		
		get("/loggedUser", (req, res) -> {
			res.type("application/json");
			
			Session session = req.session();
			User user = (User) session.attribute("user");
			if (user == null)
				return "ERROR";
			
			if (user.getUserRole() == UserRoles.CUSTOMER) {
				return g.toJson((Customer)user);
			} else if (user.getUserRole() == UserRoles.DELIVERER) {
				return g.toJson((Deliverer)user);
			} else if (user.getUserRole() == UserRoles.MANAGER) {
				return g.toJson((Manager)user);
			} else {
				return g.toJson((Administrator)user);
			}

		});
		
		post("/logout", (req, res) -> {
			req.session().invalidate();
			return "SUCCESS";
		});
		
		post("/updateUser", (req, res) -> {
			User user = g.fromJson(req.body(), User.class);
			
			if (user.getUserRole() == UserRoles.CUSTOMER) {
				customerController.updateUserData(user);
				return "SUCCESS";
			} else if (user.getUserRole() == UserRoles.DELIVERER) {
				delivererController.updateUserData(user);
				return "SUCCESS";
			} else if (user.getUserRole() == UserRoles.MANAGER) {
				managerController.updateUserData(user);
				return "SUCCESS";
			} else {
				administratorController.updateUserData(user);
				return "SUCCESS";
			}
		});
		
		get("/getRestaurants", (req, res) -> {
			res.type("application/json");
			String s = g.toJson(restaurantContoller.readAllEntities());
			return g.toJson(restaurantContoller.readAllEntities());
		});
				
	}
}
