package platform;
import org.springframework.stereotype.Component;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class LoadDate{
    private static final String DATE_FORMATTER= "yyyy/MM/dd HH:mm:ss";

    public static String getLoadDate() {
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMATTER);
        String formatDateTime = localDateTime.format(formatter);
        return formatDateTime;
    }
}