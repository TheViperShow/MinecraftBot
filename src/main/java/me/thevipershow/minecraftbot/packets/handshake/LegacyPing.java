package me.thevipershow.minecraftbot.packets.handshake;

import java.io.DataOutputStream;
import java.io.IOException;
import me.thevipershow.minecraftbot.packets.AbstractPacket;
import me.thevipershow.minecraftbot.packets.PacketType;

public final class LegacyPing extends AbstractPacket {
    public LegacyPing() {
        super(0xFE, PacketType.TO_SERVER);
    }

    @Override
    public void writeData() {
        try {
            dataOutputStream.writeByte(HandshakePacket.HandshakeNextState.STATUS.getStatus());
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }
}
