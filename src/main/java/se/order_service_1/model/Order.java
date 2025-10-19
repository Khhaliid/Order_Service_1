package se.order_service_1.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "orderTable")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Enumerated(EnumType.STRING) //Sparar enum som string i databasen istället för index
    @Column(nullable = false)
    private OrderStatus orderStatus;

    @Column
    private LocalDateTime orderDate;

    // Nytt fält för leveransadress
    @Embedded
    private DeliveryAddress deliveryAddress;

    public enum OrderStatus {
        COMPLETED,
        ONGOING;
    }
}