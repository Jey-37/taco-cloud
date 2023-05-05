package sia.tacos.controller;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import sia.tacos.model.Ingredient;
import sia.tacos.model.Taco;
import sia.tacos.model.TacoOrder;
import sia.tacos.repository.IngredientRepository;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;


@Controller
@RequestMapping("/design")
@SessionAttributes("tacoOrder")
public class DesignTacoController
{
    private final Logger logger = Logger.getLogger(DesignTacoController.class.getName());

    private final IngredientRepository ingredientRepo;

    public DesignTacoController(IngredientRepository ingredientRepo) {
        this.ingredientRepo = ingredientRepo;
    }

    @ModelAttribute("tacoOrder")
    public TacoOrder order() {
        return new TacoOrder();
    }

    @GetMapping
    public String showDesignForm(Model model) {
        model.addAttribute(new Taco());
        addIngredientsToModel(model);

        return "design";
    }

    @PostMapping
    public String processTaco(@Valid Taco taco, Errors errors,
                              @ModelAttribute TacoOrder tacoOrder) {
        if (errors.hasErrors()) {
            return "design";
        }

        taco.setCreatedAt(new Date());
        tacoOrder.addTaco(taco);
        logger.info("Added taco: "+taco);
        return "redirect:/orders/current";
    }

    public void addIngredientsToModel(Model model) {
        logger.info("Adding Ingredients to model");
        List<Ingredient> ingredients = (List<Ingredient>)ingredientRepo.findAll();
        Ingredient.Type[] types = Ingredient.Type.values();
        for (Ingredient.Type type : types) {
            model.addAttribute(type.toString().toLowerCase(),
                    filterByType(ingredients, type));
        }
    }

    private Iterable<Ingredient> filterByType(
            List<Ingredient> ingredients, Ingredient.Type type) {
        return ingredients
                .stream()
                .filter(x -> x.getType().equals(type))
                .collect(Collectors.toList());
    }
}
