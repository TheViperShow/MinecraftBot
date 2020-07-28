package me.thevipershow.minecraftbot.packets.handshake;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import me.thevipershow.minecraftbot.DataUtils;
import me.thevipershow.minecraftbot.packets.AbstractPacket;
import me.thevipershow.minecraftbot.packets.PacketType;

public final class HandshakePacket extends AbstractPacket {

    public enum HandshakeNextState {
        STATUS(0x01), LOGIN(0x02);

        private final int status;

        HandshakeNextState(final int status) {
            this.status = status;
        }

        public int getStatus() {
            return status;
        }
    }

    private final int protocolVersion;
    private final String address;
    private final int port;
    private final HandshakeNextState nextState;
    private ByteArrayOutputStream b;

    public HandshakePacket(final int protocolVersion,
                           final String address,
                           final int port,
                           final HandshakeNextState nextState) {
        super(0x00, PacketType.TO_SERVER);
        this.protocolVersion = protocolVersion;
        this.address = address;
        this.port = port;
        this.nextState = nextState;
    }

    @Override
    public void writeData(final DataOutputStream dos) {
        try {
            final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            final DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
            dataOutputStream.writeByte(getId());
            DataUtils.writeVarInt(dataOutputStream, protocolVersion);
            DataUtils.writeVarInt(dataOutputStream, address.length());
            dataOutputStream.writeBytes(address);
            dataOutputStream.writeShort(port);
            DataUtils.writeVarInt(dataOutputStream, nextState.status);
            b = byteArrayOutputStream;
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendPacket(final DataOutputStream dos) throws IOException {
        DataUtils.writeVarInt(dos, b.size());
        dos.write(b.toByteArray());
    }
}
