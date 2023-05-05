package sia.tacos.controller;

import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import sia.tacos.model.TacoOrder;
import sia.tacos.model.User;
import sia.tacos.repository.OrderRepository;

import java.util.Date;
import java.util.logging.Logger;

@Controller
@RequestMapping("/orders")
@SessionAttributes("tacoOrder")
public class OrderController
{
    private final Logger logger = Logger.getLogger(OrderController.class.getName());

    private final OrderRepository orderRepository;

    public OrderController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @GetMapping("/current")
    public String getOrderForm(@ModelAttribute TacoOrder tacoOrder) {
        return "orderForm";
    }

    @PostMapping
    public String acceptOrder(@Valid TacoOrder tacoOrder, Errors errors,
                              SessionStatus sessionStatus,
                              @AuthenticationPrincipal User user) {
        if (errors.hasErrors()) {
            return "orderForm";
        }

        tacoOrder.setUser(user);
        tacoOrder.setPlacedAt(new Date());
        orderRepository.save(tacoOrder);
        logger.info("Order accepted: "+tacoOrder);

        sessionStatus.setComplete();

        return "redirect:/";
    }
}
