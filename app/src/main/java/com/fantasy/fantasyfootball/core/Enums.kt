package com.fantasy.fantasyfootball.core

class Enums {
    enum class FormSuccess {
        LOGIN_SUCCESSFUL, REGISTER_SUCCESSFUL, LOGOUT_SUCCESSFUL, UPDATE_SUCCESSFUL
    }

    enum class FormError {
        EMPTY_FIELD, WRONG_CREDENTIALS,
        MISSING_NAME, MISSING_TEAM_NAME, MISSING_EMAIL, MISSING_PASSWORD,
        INVALID_EMAIL, INVALID_USERNAME, INVALID_PASSWORD, PASSWORDS_NOT_MATCHING,
        USER_EXISTS, TEAM_NAME_EXISTS,
        IMAGE_UPLOAD_FAILED, FAILURE
    }

    enum class Result {
        REFRESH, EDIT_PROFILE_RESULT, ADD_PLAYER_RESULT, REMOVE_PLAYER_RESULT, COLLECTED_POINTS
    }

    enum class Fragment {
        Team, Leaderboard, Profile, Match
    }

    enum class SortOrder {
        Ascending, Descending
    }

    enum class SortBy {
        Name, Price
    }

    enum class Team {
        ParisSG, ManCity, RealMadrid, BorussiaDort, Barcelona, BayernMunich, Arsenal, Leipzig, Chelsea, Newcastle,
        LeicesterCity, ManUnited, Liverpool, InterMilan, ACMilan, Juventus
    }

    enum class ShirtColor {
        DARKBLUE, DARKRED, LIGHTBLUE, LIGHTRED, ORANGE, GREEN, TURQUOISE, WHITE, YELLOW, PURPLE, BLACK
    }

    enum class Position {
        GK, LB, LCB, RCB, RB, LM, LCM, RCM, RM, LS, RS
    }

    enum class Area {
        Goalkeeper, Defender, Midfielder, Striker
    }
}