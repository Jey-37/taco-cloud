package sia.tacos;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import sia.tacos.model.Ingredient;
import sia.tacos.model.Taco;
import sia.tacos.model.TacoOrder;
import sia.tacos.model.User;
import sia.tacos.repository.IngredientRepository;
import sia.tacos.repository.OrderRepository;
import sia.tacos.repository.UserRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Random;

@SpringBootTest
@Sql({"/data.sql"})
public class TacoOrderSavingTest
{
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private IngredientRepository ingredientRepository;

    @BeforeEach
    public void addUserToDB() {
        User user = new User();
        user.setUsername("andriy");
        user.setPassword("password");
        user.setFullName("Andriy Honcharenko");
        userRepository.save(user);
    }

    @Test
    public void tacoOrderSavingTest() {
        TacoOrder order = new TacoOrder();
        order.setDeliveryName("Andriy");
        order.setDeliveryStreet("Bandery str., 73a");
        order.setDeliveryCity("Izum");
        order.setDeliveryState("UA");
        order.setDeliveryZip("64306");
        order.setCcNumber("5375414106329757");
        order.setCcExpiration("07/25");
        order.setCcCvv("583");

        order.setUser(userRepository.findByUsername("andriy").get());

        var allIngredients = (Collection<Ingredient>)ingredientRepository.findAll();
        Random rand = new Random();
        for (int i = 0; i < 3; i++) {
            Taco taco = new Taco();
            taco.setName("Taco "+(i+1));
            var ings = new ArrayList<>(allIngredients);
            int ingredsNumber = rand.nextInt(1, 6);
            for (int j = 0; j < ingredsNumber; j++) {
                int ind = rand.nextInt(ings.size());
                taco.addIngredient(ings.get(ind));
                ings.remove(ind);
            }
            order.addTaco(taco);
        }

        order.setPlacedAt(new Date());
        order = orderRepository.save(order);

        Assertions.assertNotNull(order);
        Assertions.assertNotEquals(0L, order.getId());
    }
}
