package org.example.dashboards.admin.pages.managePizzaSizes;

import org.example.dashboards.Page;
import org.example.dashboards.admin.pages.managePizzaSizes.pages.AdminAddNewPizzaSizePage;
import org.example.dashboards.admin.pages.managePizzaSizes.pages.AdminDeletePizzaSizePage;
import org.example.dashboards.general.ViewAllPizzaSizesPage;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static org.example.constants.ValueConstants.getValidNumber;

public class AdminManagePizzaSizePage implements Page {

    private final List<Page> pages = new ArrayList<>();

    public AdminManagePizzaSizePage() throws FileNotFoundException {
        pages.add(new ViewAllPizzaSizesPage());
        pages.add(new AdminAddNewPizzaSizePage());
        pages.add(new AdminDeletePizzaSizePage());
    }

    @Override
    public String getName() {
        return "Manage types of pizza size";
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
