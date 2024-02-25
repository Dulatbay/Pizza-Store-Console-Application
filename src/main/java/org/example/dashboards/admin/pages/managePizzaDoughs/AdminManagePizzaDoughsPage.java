package org.example.dashboards.admin.pages.managePizzaDoughs;

import org.example.dashboards.Page;
import org.example.dashboards.admin.pages.managePizzaDoughs.pages.AdminAddNewPizzaDoughPage;
import org.example.dashboards.admin.pages.managePizzaDoughs.pages.AdminDeletePizzaDoughPage;
import org.example.dashboards.general.ViewAllPizzaDoughsPage;

import java.util.ArrayList;
import java.util.List;

import static org.example.constants.ValueConstants.getValidNumber;

public class AdminManagePizzaDoughsPage implements Page {
    private final List<Page> pages = new ArrayList<>();

    public AdminManagePizzaDoughsPage(){
        pages.add(new ViewAllPizzaDoughsPage());
        pages.add(new AdminAddNewPizzaDoughPage());
        pages.add(new AdminDeletePizzaDoughPage());
    }

    @Override
    public String getName() {
        return "Manage types of pizza dough";
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
