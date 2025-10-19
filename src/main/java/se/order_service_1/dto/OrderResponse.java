package se.order_service_1.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import se.order_service_1.model.Order;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponse {
    long OrderId;
    Order.OrderStatus orderStatus;
    LocalDateTime completedAt;
    List<OrderItemRespons> items;
    // Nytt fält för väderinformation
    String weatherInfo;
}