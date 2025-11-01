package com.springboot.jpa.services;

import com.springboot.jpa.entities.*;
import com.springboot.jpa.repositories.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;


@Service
public class OrderServiceImp implements OrderService {

    private final OrderRepository orderRepository;
    private final ClientRepository clientRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final InventoryRepository inventoryRepository;

    private static final String CPRNOTFOUND = "No se encontró inventario para el producto con CPR: ";

    public OrderServiceImp(OrderRepository orderRepository,ClientRepository clientRepository,UserRepository userRepository,ProductRepository productRepository,InventoryRepository inventoryRepository ) {
        this.orderRepository = orderRepository;
        this.clientRepository = clientRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.inventoryRepository = inventoryRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Order> findById(Long id) {
        return orderRepository.findById(id);
    }


    @Override
    @Transactional
    public Order save(Order order) {
        User user = userRepository.findByUsername(order.getUser().getUsername())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Client client = clientRepository.findByCcr(order.getClient().getCcr())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        Order newOrder = new Order();
        newOrder.setUser(user);
        newOrder.setClient(client);
        newOrder.setState("Activa");

        List<OrderDetails> details = order.getItems().stream().map(itemDto -> {

            if (itemDto.getQuantity() <= 0) {
                throw new IllegalArgumentException("La cantidad solicitada para el producto '"
                        + itemDto.getProduct().getCpr() + "' debe ser mayor que cero.");
            }

            Product product = productRepository.findByCpr(itemDto.getProduct().getCpr())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

            Inventory inventory = inventoryRepository.findByProductId(product.getId())
                    .orElseThrow(() -> new IllegalStateException(CPRNOTFOUND + product.getCpr()));

            if (inventory.getStockActual() <= 0) {
                throw new IllegalStateException("El producto '" + product.getName() + "' no se encuentra disponible en el almacén.");
            }

            if (inventory.getStockActual() < itemDto.getQuantity()) {
                throw new IllegalStateException("Stock insuficiente para el producto '" + product.getName() + "'. Disponible: " + inventory.getStockActual() + ", solicitado: " + itemDto.getQuantity());
            }

            inventory.setStockActual(inventory.getStockActual() - itemDto.getQuantity());
            inventoryRepository.save(inventory);

            OrderDetails orderDetails = new OrderDetails();
            orderDetails.setOrder(newOrder);
            orderDetails.setProduct(product);
            orderDetails.setQuantity(itemDto.getQuantity());
            orderDetails.setUnitPrice(BigDecimal.valueOf(product.getPrice()));
            return orderDetails;
        }).toList();

        newOrder.setItems(details);
        newOrder.setTotal(newOrder.calcularTotal());

        return orderRepository.save(newOrder);
    }


    @Override
    @Transactional
    public Optional<Order> update(Long id, Order order) {
        Optional<Order> orderOptional = orderRepository.findById(id);
        if (orderOptional.isPresent()) {
            Order existingOrder = orderOptional.orElseThrow();

            User user = userRepository.findByUsername(order.getUser().getUsername())
                    .orElseThrow(() -> new IllegalStateException("Usuario no encontrado"));

            Client client = clientRepository.findByCcr(order.getClient().getCcr())
                    .orElseThrow(() -> new IllegalStateException("Cliente no encontrado"));

            existingOrder.setUser(user);
            existingOrder.setClient(client);

            // Se revierte el stock de los detalles anteriores para evitar restas incoherentes
            for (OrderDetails oldDetail : existingOrder.getItems()) {
                Product product = oldDetail.getProduct();
                Inventory inventory = inventoryRepository.findByProductId(product.getId())
                        .orElseThrow(() -> new IllegalStateException(CPRNOTFOUND + product.getCpr()));
                inventory.setStockActual(inventory.getStockActual() + oldDetail.getQuantity());
                inventoryRepository.save(inventory);
            }

            // Se limpia la lista de productos que se tenía anteriormente
            existingOrder.getItems().clear();

            // Se genera la nueva lista con los productos de la actualización y se realiza nuevamente la resta de productos
            List<OrderDetails> newDetails = order.getItems().stream().map(itemDto -> {
                Product product = productRepository.findByCpr(itemDto.getProduct().getCpr())
                        .orElseThrow(() -> new IllegalStateException("Producto no encontrado"));

                Inventory inventory = inventoryRepository.findByProductId(product.getId())
                        .orElseThrow(() -> new IllegalStateException(CPRNOTFOUND + product.getCpr()));

                int cantidadSolicitada = itemDto.getQuantity();
                if (inventory.getStockActual() < cantidadSolicitada) {
                    throw new IllegalStateException("Stock insuficiente para el producto '" + product.getName() + "'. Disponible: " + inventory.getStockActual() + ", solicitado: " + cantidadSolicitada);
                }

                inventory.setStockActual(inventory.getStockActual() - cantidadSolicitada);
                inventoryRepository.save(inventory);

                OrderDetails detail = new OrderDetails();
                detail.setOrder(existingOrder);
                detail.setProduct(product);
                detail.setQuantity(cantidadSolicitada);
                detail.setUnitPrice(BigDecimal.valueOf(product.getPrice()));
                return detail;
            }).toList();

            existingOrder.getItems().addAll(newDetails);
            existingOrder.setTotal(existingOrder.calcularTotal());

            return Optional.of(orderRepository.save(existingOrder));
        }
        return orderOptional;
    }


    @Override
    @Transactional
    public Optional<Order> delete(Long id) {
        Optional<Order> orderOptional = orderRepository.findById(id);
        if (orderOptional.isPresent()) {
            Order order = orderOptional.get();

            if ("Cancelada".equalsIgnoreCase(order.getState())) {
                throw new IllegalStateException("La orden ya está cancelada.");
            }

            // Revertir el stock de cada producto
            for (OrderDetails detail : order.getItems()) {
                Product product = detail.getProduct();
                Inventory inventory = inventoryRepository.findByProductId(product.getId())
                        .orElseThrow(() -> new IllegalStateException(CPRNOTFOUND + product.getCpr()));

                inventory.setStockActual(inventory.getStockActual() + detail.getQuantity());
                inventoryRepository.save(inventory);
            }


            order.setState("Cancelada");
            orderRepository.save(order);

            return Optional.of(order);
        }
        return Optional.empty();
    }

}
