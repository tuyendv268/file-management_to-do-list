package main;

public class Application {
	private static Login login;
	private static AdminApp adminApp;
	private static UserApp userApp;
	
	public Application() {
		login = new Login(this);
	}
	
	public static void main(String[] args) {
		System.out.println("hello world");
		new Application();
	}

	
//	Setter, getter
	
	public static Login getLogin() {
		return login;
	}

	public static void setLogin(Login login) {
		Application.login = login;
	}

	public static AdminApp getAdminApp() {
		return adminApp;
	}

	public void setAdminApp(AdminApp adminApp) {
		Application.adminApp = adminApp;
	}

	public static UserApp getUserApp() {
		return userApp;
	}

	public void setUserApp(UserApp userApp) {
		Application.userApp = userApp;
	}
}
