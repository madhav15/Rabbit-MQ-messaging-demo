package io.madhav.rabbitmq.rabbitmqapp.madhav;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Publisher {

    public static void main(String... args) throws Exception {
        final ConnectionFactory factory = new ConnectionFactory();
        factory.setUri("amqp://guest:guest@localhost");
        factory.setConnectionTimeout(30000);

        final Connection connection = factory.newConnection();

        final Channel channel = connection.createChannel();

        channel.queueDeclare("my-queue", true, false, false, null);

        int count = 1;

        while (count < 50) {
            final String message = "This is Message Number " + count;
            channel.basicPublish("", "my-queue", null, message.getBytes());
            count++;
            System.out.println("Published Message " + message);
            Thread.sleep(5000);
        }
    }
}
