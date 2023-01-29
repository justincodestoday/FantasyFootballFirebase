package com.fantasy.fantasyfootball.constant

class Enums {
    enum class LoginStatus {
        LOGIN_SUCCESSFUL, LOGIN_UNSUCCESSFUL
    }

    enum class FormSuccess {
        REGISTER_SUCCESSFUL
    }

    enum class FormErrors {
        MISSING_NAME,
        MISSING_TEAM_NAME,
        INVALID_USERNAME,
        INVALID_PASSWORD,
        PASSWORDS_NOT_MATCHING,
    }

    enum class ShirtColor {
        DARKBLUE, DARKRED, LIGHTBLUE, LIGHTRED, ORANGE, TRANSLUCENT, TURQOISE, WHITE, YELLOW, PURPLE
    }

    enum class Position {
        GK, LB, LCB, RCB, RB, LM, LCM, RCM, RM, LS, RS
    }

    enum class Area {
        Goalkeeper, Defender, Midfielder, Striker
    }
}