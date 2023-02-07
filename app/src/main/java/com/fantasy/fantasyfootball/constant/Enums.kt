package com.fantasy.fantasyfootball.constant

class Enums {
    enum class FormSuccess {
        LOGIN_SUCCESSFUL,
        REGISTER_SUCCESSFUL,
        LOGOUT_SUCCESSFUL
    }

    enum class FormError {
        EMPTY_FIELD,
        WRONG_CREDENTIALS,
        MISSING_NAME,
        MISSING_TEAM_NAME,
        INVALID_USERNAME,
        INVALID_PASSWORD,
        PASSWORDS_NOT_MATCHING,
        USER_EXISTS,
        LOGIN_UNSUCCESSFUL
    }

    enum class Result {
        REFRESH,
        POSITION_BUTTON,
        PLAYER_ID,
        PLAYER_PRICE,
        EDIT_PROFILE_RESULT,
        EDIT_IMAGE_RESULT,
        ADD_PLAYER_RESULT,
        REMOVE_PLAYER_RESULT
    }

    enum class SortOrder {
        Ascending,
        Descending
    }

    enum class SortBy {
        Name,
        Price
    }

    enum class Team {
        ParisSG, ManCity, RealMadrid, BorussiaDort, Barcelona, BayernMunich, Arsenal, Leipzig, Chelsea, Newcastle,
        LeicesterCity, ManUnited, Liverpool, InterMilan, ACMilan, Juventus
    }

    enum class ShirtColor {
        DARKBLUE, DARKRED, LIGHTBLUE, LIGHTRED, ORANGE, TURQUOISE, WHITE, YELLOW, PURPLE, BLACK
    }

    enum class Position {
        GK, LB, LCB, RCB, RB, LM, LCM, RCM, RM, LS, RS
    }

    enum class Area {
        Goalkeeper, Defender, Midfielder, Striker
    }
}