import entities.Location;
import entities.User;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.Random;

public class MqttPublishSample {
    public static String randomUser() {
        Random random = new Random();
        int id = random.nextInt(1000 + 1) + 1;

        String[] listType = {"client", "taxi"};
        String[] listStatus = {"available", "unavailable"};
        String[] listDestionation = {"Anfa", "France-Ville 2", "La Gironde", "Les Hôpitaux", "Racine", "Gauthier", "Maârif", "Bourgogne", "Aïn Diab", "Bouchentouf", "Sidi Bernoussi", "Lahraouyine", "Sidi Moumen", "Roches Noires", "Belvédère", "Aïn Sebaâ", "Hay Mohammadi", "Mabrouka", "Derb Sultan", "Mers Sultan", "Médina", "Hay El Hassani", "Californie", "Derb Ghallef", "Polo", "Oasis", "Missimi", "El Hana", "Ennassim", "Hay Swaret", "El Oulfa", "Beauséjour", "El Hank", "Al Krouia", "Ghandi", "Souart", "Palmier", "Moulay Rachid", "Aïn Chock", "2-Mars", "Bournazel", "Lissasfa", "El Rahma", "Sidi Othmane", "Sbata", "Ben Msik", "Salmia 1", "Salmia 2", "Sidi Maârouf", "Bouskoura", "Médiouna", "Sbit", "Aïn Harrouda", "La Marina63"};
        String type = listType[random.nextInt(listType.length)];
        String status = listStatus[random.nextInt(listStatus.length)];
        String destination = listDestionation[random.nextInt(listDestionation.length)];

        User user = new User(Long.valueOf(id), Location.randomCoordinates(33.45000, 33.588788, -7.500000, -7.680000), status, type, destination);

        return user.toString();
    }

    public static void main(String[] args) {
        String broker = "tcp://broker.hivemq.com:1883";
        String clientId = "emqx_test";
        String pubTopic = "bdcc/itaxi";

        try {
            MqttClient client = new MqttClient(broker, clientId);

            // MQTT connection option
            MqttConnectOptions connOpts = new MqttConnectOptions();

            // establish a connection
            client.connect(connOpts);

            for (int i = 0; i < 500; i++) {
                String content = randomUser();
                // Required parameters for message publishing
                MqttMessage message = new MqttMessage(content.getBytes());
                client.publish(pubTopic, message);
                System.out.println(content);
            }

            client.disconnect();
            client.close();
        } catch (MqttException e) {
        }
    }


}