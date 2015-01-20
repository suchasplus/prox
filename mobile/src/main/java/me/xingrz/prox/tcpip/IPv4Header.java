/*
 * Copyright (C) 2015 XiNGRZ <chenxingyu92@gmail.com>
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */

package me.xingrz.prox.tcpip;

import java.net.InetAddress;
import java.net.UnknownHostException;

import me.xingrz.prox.util.NumericUtils;

/**
 * https://en.wikipedia.org/wiki/IPv4#Header
 *
 * @author XiNGRZ
 */
public class IPv4Header extends IPHeader {

    public IPv4Header(byte[] packet) {
        super(packet);
    }

    /**
     * @return IP 包头部长度
     */
    public int ipHeaderLength() {
        return (packet[0] & 0xf) * 4;
    }

    /**
     * @return IP 包总长度
     */
    public int totalLength() {
        return NumericUtils.readShort(packet, 2);
    }

    /**
     * @return 协议
     */
    public byte protocol() {
        return packet[9];
    }

    public int getSourceIp() {
        return NumericUtils.readInt(packet, 12);
    }

    public InetAddress getSourceIpAddress() {
        try {
            return InetAddress.getByAddress(new byte[]{
                    packet[12], packet[13], packet[14], packet[15]
            });
        } catch (UnknownHostException e) {
            return null;
        }
    }

    public void setSourceIp() {

    }

    public int getDestinationIp() {
        return NumericUtils.readInt(packet, 16);
    }

    public InetAddress getDestinationIpAddress() {
        try {
            return InetAddress.getByAddress(new byte[]{
                    packet[16], packet[17], packet[18], packet[19]
            });
        } catch (UnknownHostException e) {
            return null;
        }
    }

    public short getIpHeaderChecksum() {
        return NumericUtils.readShort(packet, 10);
    }

    public void setIpHeaderChecksum(short checksum) {
        NumericUtils.writeShort(packet, 10, checksum);
    }

    /**
     * 重新计算并覆盖 Checksum
     */
    public void recomputeChecksum() {
        setIpHeaderChecksum((short) 0);

        
    }

    @Override
    public String toString() {
        return String.format("IPv4[headerLen:%s, totalLen:%s,  protocol:%s, srcIp:%s, dstIp:%s",
                ipHeaderLength(), totalLength(), protocol(),
                getSourceIpAddress().getHostAddress(),
                getDestinationIpAddress().getHostAddress());
    }

}
