package Bootstrapers;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class AuditBootstraper {
    private static FileWriter auditCSVToOutput;

    private static AuditBootstraper auditInstance;

    private final static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    private AuditBootstraper(String completeFilePath) {
        try {
            this.auditCSVToOutput = new FileWriter(completeFilePath);
        } catch (IOException exception) {
            System.out.println(exception);
        }
    }

    public static AuditBootstraper getInstance(String completeFilePath) {
        if (auditInstance == null) {
            auditInstance = new AuditBootstraper(completeFilePath);
        }

        return auditInstance;
    }

    public void addNewAuditLog(String action) throws IOException {
        String appendableLog = action + "," + dateTimeFormatter.format(LocalDateTime.now()) + "\n";

        auditCSVToOutput.append(appendableLog);
        auditCSVToOutput.flush(); // flush and write to the output
    }
}
