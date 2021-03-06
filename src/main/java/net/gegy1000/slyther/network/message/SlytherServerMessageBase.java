package net.gegy1000.slyther.network.message;

import net.gegy1000.slyther.client.SlytherClient;
import net.gegy1000.slyther.network.MessageByteBuffer;
import net.gegy1000.slyther.server.ConnectedClient;
import net.gegy1000.slyther.server.SlytherServer;

/**
 * Message being sent to the client
 */
public abstract class SlytherServerMessageBase implements IMessage {
    public byte messageId;
    public int serverTimeDelta;

    public abstract void write(MessageByteBuffer buffer, SlytherServer server, ConnectedClient client);

    public abstract void read(MessageByteBuffer buffer, SlytherClient client);

    public abstract int[] getMessageIds();

    public int getSendMessageId() {
        return getMessageIds()[0];
    }
}
