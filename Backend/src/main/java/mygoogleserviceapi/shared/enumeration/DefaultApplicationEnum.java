package mygoogleserviceapi.shared.enumeration;


public enum DefaultApplicationEnum {

    GOOGLE_CONTACTS,
    GOOGLE_PHOTOS,
    NONE;

    public static DefaultApplicationEnum getEnumFromString(String enumString) {
        switch (enumString.toUpperCase()) {
            case "GOOGLE-CONTACTS":
                return GOOGLE_CONTACTS;
            case "GOOGLE-PHOTOS":
                return GOOGLE_PHOTOS;
            default:
                return NONE;

        }
    }

}
