package com.lcaohoanq.utils;
import java.io.File;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LogUtils {

    /**
     * Checks if a "logs" folder exists in the project's root directory. If it does not exist, the
     * method creates it.
     */
    public static void ensureLogsFolderExists() {
        // Assuming the project's root directory is the current working directory
        String projectRootPath = System.getProperty("user.dir");
        File logsFolder = new File(projectRootPath, "logs");

        if (!logsFolder.exists()) {
            boolean wasCreated = logsFolder.mkdir();
            if (wasCreated) {
                log.info("Folder logs was created successfully.");
            } else {
                log.error("Failed to create folder logs.");
            }
        }
    }

    public static void showLogValidationFailed(String atTimeRequest) {
        log.error("Validation failed for user at: {}", atTimeRequest);
    }

    public static void showLogExistedUser(String typeAccount, String accountIdentifier) {
        log.error("{} already existed: {}", typeAccount, accountIdentifier);
    }

    public static void showLogNewUserRegistered(String atTimeRequest){
        log.info("New user registered at: {}", atTimeRequest);
    }


    public static void showLogNotFoundUser(String typeAccount, String accountIdentifier){
        log.error("{} not found: {}", typeAccount, accountIdentifier);
    }

    public static void showLogLoginSuccess(String atTimeRequest){
        log.info("Login successfully at: {}", atTimeRequest);
    }

    public static void showLogErrorWhenRegisterNewUser(String atTimeRequest, String reason){
        log.error("An error occurred while creating a new user at: {}\nReason: {}", atTimeRequest, reason);
    }

    public static void showLogTimeOutException(String atTimeRequest){
        log.error("Time out exception at: {}", atTimeRequest);
    }

    public static void showLogInternalServerException(String atTimeRequest){
        log.error("Internal server exception at: {}", atTimeRequest);
    }
}