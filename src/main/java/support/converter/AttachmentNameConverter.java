package support.converter;

import java.util.UUID;

public class AttachmentNameConverter {
    public static String convertName(String fileName) {
        return UUID.randomUUID().toString() + fileName;
    }
}
