package io.madhav.rabbitmq.rabbitmqapp.madhav;

import com.rabbitmq.client.*;

public class Consumer {

    public static void main(String... args) throws Exception {
        final ConnectionFactory factory = new ConnectionFactory();
        factory.setUri("amqp://guest:guest@localhost");
        factory.setConnectionTimeout(30000);

        final Connection connection = factory.newConnection();

        final Channel channel = connection.createChannel();

        channel.queueDeclare("my-queue", true, false, false, null);

        final DefaultConsumer defaultConsumer = new DefaultConsumer(channel);
        channel.basicConsume("my-queue", false, defaultConsumer);

        while (true) {
            final DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                final String message = new String(delivery.getBody(), "UTF-8");
                System.out.println("Received Message " + message + "");
            };
            channel.basicConsume("my-queue", true, deliverCallback, consumerTag -> { });
        }

    }
}
