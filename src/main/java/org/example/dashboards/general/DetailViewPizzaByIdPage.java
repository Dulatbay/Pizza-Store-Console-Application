package org.example.dashboards.general;

import org.example.dashboards.Page;
import org.example.exceptions.EntityNotFoundException;
import org.example.services.PizzaBaseService;
import org.example.services.impl.PizzaBaseServiceImpl;
import org.example.utils.InputUtils;

import java.util.List;

public class DetailViewPizzaByIdPage implements Page {
    private final PizzaBaseService pizzaBaseService;

    public DetailViewPizzaByIdPage() {
        this.pizzaBaseService = PizzaBaseServiceImpl.getInstance();
    }

    @Override
    public String getName() {
        return "Detail view pizza";
    }

    @Override
    public void start() {
        System.out.println("Write \"exit\" to back");
        try {
            System.out.print("Enter id of pizza base: ");
            var line = InputUtils.getScanner().nextLine();
            if (line.equals("exit")) return;
            var id = Long.parseLong(line);
            var entity = pizzaBaseService.getById(id);
            new ViewAllPizzasPage().printAsTable(List.of(entity));
            System.out.println("Base ingredients:");
            new ViewAllIngredientsPage().printAsTable(entity.getBaseIngredients());
            System.out.println("Extra ingredients:");
            new ViewAllIngredientsPage().printAsTable(entity.getExtraIngredients());
            System.out.println("Sizes:");
            new ViewAllPizzaSizesPage().printAsTable(entity.getPizzaSizes());
            System.out.println("Doughs:");
            new ViewAllPizzaDoughsPage().printAsTable(entity.getPizzaDoughs());
        } catch (EntityNotFoundException e) {
            System.out.println("Pizza base doesn't exist!");
        } catch (NumberFormatException e) {
            System.out.println("Enter a correct value!");
        }
    }
}
