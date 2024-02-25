package org.example.dashboards.manager;

import org.example.dashboards.Application;
import org.example.dashboards.Page;
import org.example.dashboards.general.*;

import java.util.ArrayList;
import java.util.List;

import static org.example.constants.ValueConstants.getValidNumber;

public class ManagerApplication implements Application {

    private final List<Page> pages = new ArrayList<>();

    public ManagerApplication() {
        pages.add(new ManagerAddNewOrderPage());
        pages.add(new ManagerViewAllOrdersPage());
        pages.add(new ManagerViewOrderDetailPage());
        pages.add(new ViewAllPizzasPage());
        pages.add(new SearchByNamePizzaPage());
        pages.add(new DetailViewPizzaByIdPage());
        pages.add(new ViewAllIngredientsPage());
        pages.add(new ViewAllPizzaDoughsPage());
    }

    @Override
    public void start() {
        while (true) {
            showMenu();
            int numberOfMenu = getValidNumber(pages.size() + 1);
            if (numberOfMenu - 1 == pages.size()) {
                return;
            }
            pages.get(numberOfMenu - 1).start();
        }
    }

    private void showMenu() {
        int pageNumber = 1;
        for (var page : pages) {
            System.out.printf("%d) %s%n", pageNumber++, page.getName());
        }
        System.out.println(pageNumber + ") Exit");
    }

    @Override
    public void end() {

    }
}
