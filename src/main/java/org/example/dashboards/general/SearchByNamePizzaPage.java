package org.example.dashboards.general;

import org.example.dashboards.Page;
import org.example.services.PizzaBaseService;
import org.example.services.impl.PizzaBaseServiceImpl;
import org.example.utils.InputUtils;

public class SearchByNamePizzaPage implements Page {
    private final PizzaBaseService pizzaBaseService;

    public SearchByNamePizzaPage() {
        this.pizzaBaseService = PizzaBaseServiceImpl.getInstance();
    }

    @Override
    public String getName() {
        return "Search by name pizza";
    }

    @Override
    public void start() {
        System.out.print("Enter a name of pizza base: ");
        var line = InputUtils.getScanner().nextLine().trim();
        if (line.isEmpty() || line.isBlank()) {
            System.out.println("Enter a correct value!");
            return;
        }
        new ViewAllPizzasPage().printAsTable(pizzaBaseService.findByName(line));
    }
}
