package mygoogleserviceapi.contacts.enumeration;

import static org.springframework.util.StringUtils.capitalize;

public enum ContactTypeEnum {

    HOME,
    WORK,
    OTHER;

    public String getString() {
        return capitalize(this.toString());
    }

    public static ContactTypeEnum getEnumFromString(String enumString) {
        switch (enumString.toUpperCase()) {
            case "HOME":
                return HOME;
            case "WORK":
                return WORK;
            default:
                return OTHER;

        }
    }
}
