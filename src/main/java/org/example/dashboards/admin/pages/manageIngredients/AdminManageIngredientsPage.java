package org.example.dashboards.admin.pages.manageIngredients;

import org.example.dashboards.Page;
import org.example.dashboards.admin.pages.manageIngredients.pages.AdminAddNewIngredientPage;
import org.example.dashboards.admin.pages.manageIngredients.pages.AdminDeleteIngredientPage;
import org.example.dashboards.general.ViewAllIngredientsPage;

import java.util.ArrayList;
import java.util.List;

import static org.example.constants.ValueConstants.getValidNumber;

public class AdminManageIngredientsPage implements Page {

    private final List<Page> pages = new ArrayList<>();

    public AdminManageIngredientsPage(){
        pages.add(new ViewAllIngredientsPage());
        pages.add(new AdminAddNewIngredientPage());
        pages.add(new AdminDeleteIngredientPage());
    }

    @Override
    public String getName() {
        return "Manage ingredients";
    }

    @Override
    public void start() {
        while (true){
            showMenu();
            var number = getValidNumber(pages.size() + 1);
            if (number == pages.size() + 1)
                return;
            pages.get(number - 1).start();
        }
    }
    private void showMenu() {
        var index = 0;
        for (var i : pages) {
            System.out.printf("%d) %s%n", ++index, i.getName());
        }
        System.out.println(++index + ") Exit");
    }
}
