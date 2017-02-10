package models;

import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import controllers.HibernateUtil;
import controllers.MainWindowController;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

	@SuppressWarnings({ "deprecation", "rawtypes", "unchecked" })
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		try {
			String empFirstName = "Rinat";
			Criteria criteria = session.createCriteria(Employee.class);
			Criterion eqEmpFirstName = Restrictions.eq("empFirstname", empFirstName);
			System.out.println(criteria.toString());
			List<Employee> employees = criteria.list();
			for (Employee employee : employees) {
				System.out.println(employee.toString());
			}
			for (Employee employee : employees) {
				System.out.print(employee.getId() + " | ");
			}
			System.out.println();

			ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
			Validator validator = validatorFactory.getValidator();
			for (Employee employee : employees) {
				Set<ConstraintViolation<Employee>> constrs = validator.validate(employee);
				for (ConstraintViolation<Employee> constr : constrs) {
					System.out.print(constr.getRootBean().getEmpSurname() + " " + constr.getRootBean().getEmpFirstname() + " - ");
					StringBuilder stringBuilder = new StringBuilder("property: ");
					stringBuilder.append(constr.getPropertyPath() + " ");
					stringBuilder.append("value: ");
					stringBuilder.append(constr.getInvalidValue() + " ");
					stringBuilder.append("message: ");
					stringBuilder.append(constr.getMessage());
					System.out.println(stringBuilder.toString());
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {

		}
		// session.close();
		// sessionFactory.close();
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		MainWindowController mainWindowController = new MainWindowController();
		mainWindowController.start(primaryStage);
	}

	// @Override
	// public void start(Stage primaryStage) throws Exception {

	// stageBuilder("../views/MainWindow.fxml", 1000, 640, "АС
	// административного сопровождения - ТЭЦ-3 - Гатин Ильдар Рашитович",
	// 1000, 640, 20, 20);
	// System.out.println(idImagesZayavki.getImage());
	// stageBuilder("../views/EmployeesWindow.fxml", 400, 500, "АС
	// административного сопровождения - База данных - Сотрудники", 400,
	// 500, 435, 20);
	// stageBuilder("../views/EmployeeAddingWindow.fxml", 400, 500, "АС
	// административного сопровождения - База данных - Сотрудники -
	// Добавление нового сотрудника", 400, 500, 435, 20);

	// public void stageBuilder(String fxmlResource, int width, int height,
	// String title, int minWidth, int minHeight, int x, int y) {
	// try {
	// Stage stage = new Stage();
	// Parent panel;
	// panel = FXMLLoader.load(getClass().getResource(fxmlResource));
	// Scene scene = new Scene(panel, width, height);
	// stage.setTitle(title);
	// stage.setMinWidth(minWidth);
	// stage.setMinHeight(minHeight);
	// stage.setScene(scene);
	// stage.setX(x);
	// stage.setY(y);
	// stage.show();
	// } catch (IOException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	//
	// }

}
