package org.example.dashboards.admin.pages.managePizza;

import org.example.dashboards.general.DetailViewPizzaByIdPage;
import org.example.dashboards.general.SearchByNamePizzaPage;
import org.example.dashboards.general.ViewAllPizzasPage;
import org.example.dashboards.Page;
import org.example.dashboards.admin.pages.managePizza.pages.*;

import java.util.ArrayList;
import java.util.List;

import static org.example.constants.ValueConstants.getValidNumber;

public class AdminManagePizzasPage implements Page {

    private final List<Page> pages = new ArrayList<>();

    public AdminManagePizzasPage() {
        pages.add(new ViewAllPizzasPage());
        pages.add(new AdminAddNewPizzaPage());
        pages.add(new AdminUpdatePizzaPage());
        pages.add(new AdminDeletePizzaPage());
        pages.add(new DetailViewPizzaByIdPage());
        pages.add(new SearchByNamePizzaPage());
    }

    @Override
    public String getName() {
        return "Manage pizzas";
    }

    @Override
    public void start() {
        while (true) {
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
