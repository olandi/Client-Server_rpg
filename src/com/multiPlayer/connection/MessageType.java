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

    LEAVE_BATTLE_REQUEST,
    LEAVE_BATTLE_RESPONSE,

    PLAYER_MOVEMENT_MESSAGE,
    PLAYER_MOVEMENT_MESSAGE_ACCEPTED,
    PLAYER_BATTLE_MESSAGE,
    PLAYER_BATTLE_MESSAGE_ACCEPTED,

    ANIMATION,
    UPDATE_PLAYER_DATA_EVENT,
    TIMER,
    FINISH_BATTLE,
    UPDATE_BATTLEFIELD

}
