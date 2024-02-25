package org.example.dashboards.admin;

import org.example.dashboards.Application;
import org.example.dashboards.Page;
import org.example.dashboards.admin.pages.manageIngredients.AdminManageIngredientsPage;
import org.example.dashboards.admin.pages.managePizza.AdminManagePizzasPage;
import org.example.dashboards.admin.pages.managePizzaDoughs.AdminManagePizzaDoughsPage;
import org.example.dashboards.admin.pages.managePizzaSizes.AdminManagePizzaSizePage;
import org.example.utils.InputUtils;

import java.util.ArrayList;
import java.util.List;

import static org.example.constants.ValueConstants.getValidNumber;

//

public class AdminApplication implements Application {
    private boolean isRunning;
    private final String password = "qit";

    private final List<Page> pages = new ArrayList<>();

    public AdminApplication() {
        try {
            isRunning = true;

            pages.add(new AdminManagePizzasPage());
            pages.add(new AdminManageIngredientsPage());
            pages.add(new AdminManagePizzaDoughsPage());
            pages.add(new AdminManagePizzaSizePage());
        } catch (Exception e) {
            System.out.println("Service init error: " + e.getMessage());
        }
    }

    @Override
    public void start() {
        try {
            login();
            while (isRunning) {
                showMenu();
                int numberOfMenu = getValidNumber(pages.size() + 1);
                if (numberOfMenu - 1 == pages.size()){
                    isRunning = false;
                    continue;
                }
                pages.get(numberOfMenu - 1).start();
            }
        } catch (Exception e) {
            System.out.println("Start error:" + e.getMessage());
        }
    }

    private void showMenu() {
        int pageNumber = 1;
        for (var page : pages) {
            System.out.printf("%d) %s%n", pageNumber++, page.getName());
        }
        System.out.println(pageNumber + ") Exit");
    }

    private void login() {
        do {
            System.out.print("Enter a password:(qit): ");
            var inputPassword = InputUtils.getScanner().nextLine();
            if (inputPassword.equals(password)) return;
            else System.out.println("Incorrect!!!");
        } while (true);
    }


}
