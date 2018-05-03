package org.dam.utils.util.serialize;

public interface Serialize {

    byte[] serializeHeader();

    byte[] serializeBody();

}