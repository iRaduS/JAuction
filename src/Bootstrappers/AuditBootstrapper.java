package Bootstrappers;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class AuditBootstrapper {
    private static FileWriter auditCSVToOutput;

    private static AuditBootstrapper auditInstance;

    private final static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    private AuditBootstrapper(String completeFilePath) {
        try {
            auditCSVToOutput = new FileWriter(completeFilePath, true);
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }
    }

    public static AuditBootstrapper getInstance(String completeFilePath) {
        if (auditInstance == null) {
            auditInstance = new AuditBootstrapper(completeFilePath);
        }

        return auditInstance;
    }

    public void addNewAuditLog(String action) throws IOException {
        String appendableLog = action + "," + dateTimeFormatter.format(LocalDateTime.now()) + "\n";

        auditCSVToOutput.append(appendableLog);
        auditCSVToOutput.flush(); // flush and write to the output
    }
}
