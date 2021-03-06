package net.gegy1000.slyther.network.message.server;

import net.gegy1000.slyther.client.SlytherClient;
import net.gegy1000.slyther.game.entity.Food;
import net.gegy1000.slyther.network.MessageByteBuffer;
import net.gegy1000.slyther.network.message.SlytherServerMessageBase;
import net.gegy1000.slyther.server.ConnectedClient;
import net.gegy1000.slyther.server.SlytherServer;

public class MessageRemoveFood extends SlytherServerMessageBase {
    private Food food;

    public MessageRemoveFood() {
    }

    public MessageRemoveFood(Food food) {
        this.food = food;
    }

    @Override
    public void write(MessageByteBuffer buffer, SlytherServer server, ConnectedClient client) {
        int gameRadius = server.configuration.gameRadius;
        buffer.writeUInt16((int) food.posX + gameRadius);
        buffer.writeUInt16((int) food.posY + gameRadius);
        if (food.eater != null) {
            buffer.writeUInt16(food.eater.id);
        }
    }

    @Override
    public void read(MessageByteBuffer buffer, SlytherClient client) {
        int x = buffer.readUInt16();
        int y = buffer.readUInt16();
        int id = y * client.GAME_RADIUS * 3 + x;
        Food food = client.getFood(id);
        if (food != null) {
            food.eaten = true;
            if (buffer.hasRemaining(2)) {
                food.eater = client.getSnake(buffer.readUInt16());
                food.eatenFr = 0;
            } else {
                client.removeEntity(food);
            }
        }
    }

    @Override
    public int[] getMessageIds() {
        return new int[] { 'c' };
    }
}
