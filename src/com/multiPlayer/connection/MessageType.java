package com.multiPlayer.connection;

//enum, который отвечает за тип сообщений пересылаемых между клиентом и сервером.
public enum MessageType {
    PLAYER_NAME,
    PLAYER_NAME_REQUEST,
    PLAYER_NAME_ACCEPTED,

    CREATE_BALLTE_REQUEST,
    JOIN_TO_BATTLE_REQUEST,
    JOIN_TO_BATTLE_RESPONSE,
    BATTLE_FIELD_INSTANCE,
    BATTLE_FIELD_INSTANCE_ACCEPTED,

    PLAYER_ACTION_EVENT,
    PLAYER_ACTION_EVENT_ACCEPTED,

    UPDATE_PLAYER_DATA_EVENT,

}
