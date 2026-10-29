INSERT INTO orders(total_price, order_status) VALUES
(100.50, 'PENDING'),
(250.00, 'CONFIRMED'),
(75.25, 'PENDING'),
(499.99, 'DELIVERED'),
(150.75, 'CANCELLED'),
(320.00, 'CONFIRMED'),
(89.50, 'PENDING'),
(650.25, 'DELIVERED'),
(200.00, 'CONFIRMED'),
(125.99, 'PENDING');

INSERT INTO order_item(order_id, product_id, quantity) VALUES
(1, 101, 2),
(1, 102, 1),
(2, 103, 3),
(2, 104, 1),
(3, 105, 2),
(3, 101, 1),
(4, 106, 4),
(5, 102, 2),
(6, 107, 1),
(7, 108, 3);