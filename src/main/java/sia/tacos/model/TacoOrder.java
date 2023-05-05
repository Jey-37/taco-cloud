package sia.tacos.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.CreditCardNumber;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@RestResource(path = "orders", rel = "orders")
public class TacoOrder
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date placedAt;

    @NotBlank(message="Delivery name is required")
    @Column(nullable = false)
    private String deliveryName;
    @NotBlank(message="Street is required")
    @Column(nullable = false)
    private String deliveryStreet;
    @NotBlank(message="City is required")
    @Column(nullable = false)
    private String deliveryCity;
    @NotBlank(message="State is required")
    @Column(nullable = false)
    private String deliveryState;
    @NotBlank(message="Zip code is required")
    @Column(nullable = false)
    private String deliveryZip;

    @CreditCardNumber(message="Not a valid credit card number")
    @Column(nullable = false, length = 16)
    private String ccNumber;
    @Pattern(regexp="^(0[1-9]|1[0-2])([\\/])([2-9][0-9])$",
             message="Must be formatted MM/YY")
    @Column(nullable = false, length = 5)
    private String ccExpiration;
    @Digits(integer=3, fraction=0, message="Invalid CVV")
    @Column(nullable = false, length = 3)
    private String ccCvv;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order", fetch = FetchType.LAZY)
    private List<Taco> tacos = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    public void addTaco(Taco taco) {
        taco.setOrder(this);
        tacos.add(taco);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getPlacedAt() {
        return placedAt;
    }

    public void setPlacedAt(Date placedAt) {
        this.placedAt = placedAt;
    }

    public String getDeliveryName() {
        return deliveryName;
    }

    public void setDeliveryName(String deliveryName) {
        this.deliveryName = deliveryName;
    }

    public String getDeliveryStreet() {
        return deliveryStreet;
    }

    public void setDeliveryStreet(String deliveryStreet) {
        this.deliveryStreet = deliveryStreet;
    }

    public String getDeliveryCity() {
        return deliveryCity;
    }

    public void setDeliveryCity(String deliveryCity) {
        this.deliveryCity = deliveryCity;
    }

    public String getDeliveryState() {
        return deliveryState;
    }

    public void setDeliveryState(String deliveryState) {
        this.deliveryState = deliveryState;
    }

    public String getDeliveryZip() {
        return deliveryZip;
    }

    public void setDeliveryZip(String deliveryZip) {
        this.deliveryZip = deliveryZip;
    }

    public String getCcNumber() {
        return ccNumber;
    }

    public void setCcNumber(String ccNumber) {
        this.ccNumber = ccNumber;
    }

    public String getCcExpiration() {
        return ccExpiration;
    }

    public void setCcExpiration(String ccExpiration) {
        this.ccExpiration = ccExpiration;
    }

    public String getCcCvv() {
        return ccCvv;
    }

    public void setCcCvv(String ccCVV) {
        this.ccCvv = ccCVV;
    }

    public List<Taco> getTacos() {
        return tacos;
    }

    public void setTacos(List<Taco> tacos) {
        this.tacos = tacos;
        this.tacos.forEach(t -> t.setOrder(this));
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "TacoOrder{" +
                "id=" + id +
                ", placedAt=" + placedAt +
                ", deliveryName='" + deliveryName + '\'' +
                ", deliveryStreet='" + deliveryStreet + '\'' +
                ", deliveryCity='" + deliveryCity + '\'' +
                ", deliveryState='" + deliveryState + '\'' +
                ", deliveryZip='" + deliveryZip + '\'' +
                ", ccNumber='" + ccNumber + '\'' +
                ", ccExpiration='" + ccExpiration + '\'' +
                ", ccCVV='" + ccCvv + '\'' +
                ", tacos=" + tacos +
                ", user=" + user +
                '}';
    }
}
